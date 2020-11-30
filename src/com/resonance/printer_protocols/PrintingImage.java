package com.resonance.printer_protocols;

import java.awt.image.BufferedImage;

public interface PrintingImage {
    void setImage(BufferedImage image) throws IllegalImageSizeException;
    byte[] getImageBytesChain();
    BufferedImage getBufferedImage();
}
