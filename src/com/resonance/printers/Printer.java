package com.resonance.printers;

import com.resonance.printer_protocols.PrinterProtocol;
import com.resonance.printer_protocols.PrintingStyle;
import com.resonance.printers.connector.Connector;
import com.resonance.printers.connector.ConnectorSerialPort;
import com.resonance.printers.connector.ConnectorTcp;
import com.resonance.printers.connector.ConnectorUsb;
import jssc.SerialPortException;

import java.io.*;
import java.net.Socket;

public class Printer {

    private Connector connector;
    private String port;
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

    public void setPort(String port) {
        this.port = port;
    }

    public Connector getConnector() {
        return connector;
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

    public void printFromBuffer() throws IOException, SerialPortException {
        if (connector instanceof ConnectorUsb) {
            OutputStream outputStream = new FileOutputStream(connector.getPathway());
            outputStream.write(printerBuffer.toByteArray());
            outputStream.close();
        }
        else if (connector instanceof ConnectorTcp) {
            String pathway = connector.getPathway();
            Socket socket = new Socket(pathway.substring(0, pathway.indexOf(':')),
                    Integer.parseInt(pathway.substring(pathway.indexOf(':') + 1)));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            oos.write(printerBuffer.toByteArray());
            oos.close();
        }

        else if (connector instanceof ConnectorSerialPort) {
            ((ConnectorSerialPort) connector).getSerialPort().openPort();
            ((ConnectorSerialPort) connector).getSerialPort().writeBytes(printerBuffer.toByteArray());
            ((ConnectorSerialPort) connector).getSerialPort().closePort();
        }

        resetBuffer();
    }

    public void resetBuffer () {
        printerBuffer.reset();
    }

}
