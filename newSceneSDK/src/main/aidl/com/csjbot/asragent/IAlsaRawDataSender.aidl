// IAlsaRawDataSender.aidl
package com.csjbot.asragent;

// Declare any non-default types here with import statements

interface IAlsaRawDataSender {
    void sendAlsaData(in byte[] data);
}