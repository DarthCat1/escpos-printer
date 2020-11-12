package com.resonance.printers;

public class PrinterUSB implements Printer {

    private String port;

    public PrinterUSB(String port) {
        this.port = port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }


}
