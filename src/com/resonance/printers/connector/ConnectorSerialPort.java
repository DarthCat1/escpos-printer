package com.resonance.printers.connector;


import jssc.SerialPort;
import jssc.SerialPortException;


public class ConnectorSerialPort implements Connector{

    private SerialPort serialPort;
    private String pathway;

    public ConnectorSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
        pathway = serialPort.getPortName();
    }

    @Override
    public void setPathway(String pathway) {
        this.pathway = pathway;
        serialPort = new SerialPort(pathway);
    }

    @Override
    public String getPathway() {
        return pathway;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    @Override
    public void printBuffer(byte[] buffer) throws SerialPortException {
        serialPort.openPort();
        serialPort.writeBytes(buffer);
        serialPort.closePort();
    }


}

