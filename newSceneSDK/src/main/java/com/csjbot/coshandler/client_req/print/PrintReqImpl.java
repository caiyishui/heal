package com.csjbot.coshandler.client_req.print;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.global.CmdConstants;
import com.csjbot.coshandler.global.REQConstants;

/**
 * Created by jingwc on 2017/9/26.
 */

public class PrintReqImpl extends BaseClientReq implements IPrintReq {

    @Override
    public void openPrinter() {
        sendReq(getJson(CmdConstants.PRINTER_OPEN_CMD));
    }

    @Override
    public void printerCut() {
        sendReq(getJson(CmdConstants.PRINTER_PAPER_CUT_CMD));
    }

    @Override
    public void printText(String text) {
        sendReq(getJson(CmdConstants.PRINTER_PRINT_TEXT_CMD,"text",text));
    }

    @Override
    public void printQRCode(String qrcode) {
        sendReq(getJson(CmdConstants.PRINTER_PRINT_QRCODE_CMD,"qrcode",qrcode));
    }

    @Override
    public void printImage(String image) {
        sendReq(getJson(CmdConstants.PRINTER_PRINT_QRCODE_CMD,"img",image));
    }
}
