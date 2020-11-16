package com.resonance.printers;

import com.resonance.printer_protocols.PrintingStyle;

import java.io.IOException;

public interface Printer {
    String getPort();
    void setPort(String port);
    void writeToBuffer (String text) throws IOException;
    void writeToBuffer (byte [] bytes) throws IOException;
    void writeToBuffer (PrintingStyle style, String text) throws IOException;
    void resetBuffer ();
    void printFromBuffer() throws IOException;
}
