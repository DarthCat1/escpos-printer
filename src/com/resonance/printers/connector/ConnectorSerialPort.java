package com.resonance.printers.connector;


import jssc.SerialPort;


public class ConnectorSerialPort implements Connector{

    private SerialPort serialPort;
    private String pathway;

    public ConnectorSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
        pathway = serialPort.getPortName();
    }

    @Override
    public String getPathway() {
        return pathway;
    }

    @Override
    public void setPathway(String pathway) {
        this.pathway = pathway;
        serialPort = new SerialPort(pathway);
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }
}

