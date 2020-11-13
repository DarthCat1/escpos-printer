package com.resonance.printers;

import java.io.IOException;

public interface Printer {
    String getPort();
    void setPort(String port);
    void writeToBuffer (byte [] bytes) throws IOException;
    void resetBuffer ();
    void printFromBuffer() throws IOException;
}
