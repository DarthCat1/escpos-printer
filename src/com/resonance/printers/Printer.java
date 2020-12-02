package com.resonance.printers;


import com.resonance.printer_protocols.PrinterProtocol;
import com.resonance.printer_protocols.PrintingStyle;
import com.resonance.printers.connector.*;
import jssc.SerialPortException;

import javax.print.*;
import java.io.*;

public class Printer {
    private Connector connector;
    private int charPerLine;
    private ByteArrayOutputStream printerBuffer;
    private PrinterProtocol protocol;

    public Printer(Connector connector, int charPerLine, PrinterProtocol protocol) {
        this.connector = connector;
        this.charPerLine = charPerLine;
        this.protocol = protocol;
        printerBuffer = new ByteArrayOutputStream();
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public Connector getConnector() {
        return connector;
    }

    public void writeToBuffer (byte[] bytes) throws IOException {
        printerBuffer.write(bytes);
    }

    public void writeToBuffer (String text) throws IOException {
        printerBuffer.write(text.getBytes(protocol.getCharsetName()));
    }

    public void writeToBuffer (PrintingStyle style,  String text) throws IOException {
        printerBuffer.write(style.getConfigBytes());
        printerBuffer.write(text.getBytes(protocol.getCharsetName()));
    }

    public void writeToBuffer (byte b) {
        printerBuffer.write(b);
    }

    public void printBuffer() throws PrintException, SerialPortException, IOException {
        connector.printBuffer(printerBuffer.toByteArray());
        printerBuffer.reset();
    }

    public void resetBuffer () {
        printerBuffer.reset();
    }

}
