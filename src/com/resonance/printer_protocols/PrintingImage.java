package com.resonance.printer_protocols;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface PrintingImage {
    void setImage(BufferedImage image) throws IOException;
    byte[] getImageBytesChain();
    BufferedImage getBufferedImage();
}
