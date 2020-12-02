package com.resonance.printers.connector;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ConnectorUsb implements Connector {

    private String pathway;

    public ConnectorUsb (String pathway) {
        this.pathway = pathway;
    }

    @Override
    public String getPathway() {
        return pathway;
    }

    @Override
    public void setPathway(String pathway) {
        this.pathway = pathway;
    }

    @Override
    public void printBuffer(byte[] buffer) throws IOException {
        OutputStream outputStream = new FileOutputStream(pathway);
        outputStream.write(buffer);
        outputStream.close();
    }
}
