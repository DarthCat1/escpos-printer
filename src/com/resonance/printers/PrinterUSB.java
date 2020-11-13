package com.resonance.printers;

import java.io.*;

public class PrinterUSB implements Printer {

    private String port;
    private int charPerLine;
    ByteArrayOutputStream printerBuffer;




    public PrinterUSB(String port, int charPerLine) {
        this.port = port;
        this.charPerLine = charPerLine;
        printerBuffer = new ByteArrayOutputStream();
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void writeToBuffer (byte [] bytes) throws IOException {
        printerBuffer.write(bytes);
    }

    public void printFromBuffer() throws IOException {
        OutputStream outputStream = new FileOutputStream(port);
        outputStream.write(printerBuffer.toByteArray());
        outputStream.close();
        resetBuffer();
    }

    public void resetBuffer () {
        printerBuffer.reset();
    }


}
