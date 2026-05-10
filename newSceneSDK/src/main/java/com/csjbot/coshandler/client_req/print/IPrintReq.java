package com.csjbot.coshandler.client_req.print;

/**
 * Created by jingwc on 2017/9/26.
 */

public interface IPrintReq {

    /**
     * 打开打印机
     */
    void openPrinter();

    /**
     * 打印机切刀
     */
    void printerCut();

    /**
     * 打印文本
     */
    void printText(String text);

    /**
     * 打印二维码
     */
    void printQRCode(String qrcode);

    /**
     * 打印图片
     */
    void printImage(String image);
}
