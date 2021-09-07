package com.resonance.printers.connector;


import jssc.SerialPortException;

import javax.print.PrintException;
import java.io.IOException;

public interface Connector {
    String getPathway();
    void setPathway(String pathway) throws IllegalPrintServiceNameException;
    void printBuffer(byte[] buffer) throws PrintException, IOException, SerialPortException;
}
