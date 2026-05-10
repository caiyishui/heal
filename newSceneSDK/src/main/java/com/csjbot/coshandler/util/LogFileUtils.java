package com.csjbot.coshandler.util;

import android.os.Environment;


import com.csjbot.coshandler.log.Csjlogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LogFileUtils {
    private static final String logFileDir = Environment.getExternalStorageDirectory() + "/new_retail/logs/";

    public static void findAndZipLogFiles() {
        new Thread(LogFileUtils::checkAndZipLog).start();
    }

    private static void checkAndZipLog() {
        File dir = new File(logFileDir);
        if (!dir.exists()) {
            return;
        }

        if (!dir.isDirectory()) {
            return;
        }

        File[] files = dir.listFiles();

        if (files == null || files.length == 0) {
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();

        for (File file : files) {
            String today = sdf.format(now);
            Csjlogger.info("will zip log file = {}, length = {}", file.getAbsolutePath(), file.length());
            if (dateBiggerThan30(file, sdf)) {
                file.delete();
            }
            if (file.getName().endsWith("log") && !file.getName().contains(today)) {
                zipFile(file.getAbsolutePath());
                file.delete();
            }
        }
    }

    static boolean dateBiggerThan30(File file, SimpleDateFormat sdf) {
        String dateString = file.getName(); // 要转换的日期字符串
        String names[] = dateString.split("\\.");

        if (names.length > 2) {
            long nd = 1000 * 24 * 60 * 60;

            try {
                Date now = new Date();
                Date fileDate = sdf.parse(names[1]);
                long diff = now.getTime() - fileDate.getTime();
                long day = diff / nd;

                if (day >= 30) {
                    return true;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        return false;

    }

    /**
     * 这个是抄的代码，可以跑，不想改了
     *
     * @param srcFileString
     */
    private static void zipFile(String srcFileString) {
        //创建ZIP
        try {
            ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(new File(srcFileString + ".zip")));
            //创建文件
            File file = new File(srcFileString);
            //压缩
            zipFileReal(file.getParent() + File.separator, file.getName(), outZip);

            //完成和关闭
            outZip.finish();
            outZip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void zipFileReal(String folderString, String fileString, ZipOutputStream zipOutputSteam) {
        try {
            if (zipOutputSteam == null)
                return;
            File file = new File(folderString + fileString);

            ZipEntry zipEntry = new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
