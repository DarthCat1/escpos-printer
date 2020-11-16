package com.resonance.printers;

import com.resonance.printer_protocols.PrinterProtocol;
import com.resonance.printer_protocols.PrintingStyle;

import java.io.*;

public class PrinterUSB implements Printer {

    private String port;
    private int charPerLine;
    private ByteArrayOutputStream printerBuffer;
    private PrinterProtocol protocol;

    public PrinterUSB(String port, int charPerLine, PrinterProtocol protocol) {
        this.port = port;
        this.charPerLine = charPerLine;
        this.protocol = protocol;
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

    public void writeToBuffer (String text) throws IOException {
        printerBuffer.write(text.getBytes(protocol.getCharsetName()));
    }

    public void writeToBuffer (PrintingStyle style,  String text) throws IOException {
        printerBuffer.write(style.getConfigBytes());
        printerBuffer.write(text.getBytes(protocol.getCharsetName()));
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
