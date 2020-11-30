package com.resonance.printer_protocols;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface PrinterProtocol {
    byte [] setCharacterCodeTable(CharacterCodeTable characterCodeTable);
    byte [] feed (int linesCount);
    byte [] cut(CutMode cutMode);
    byte [] cut ();
    byte [] initConnection();
    String getCharsetName();
    Barcode getBarcode();
    QrCode getQrCode();
    byte[] printStoredImage();
    byte[] storeImage (PrintingImage printingImage) throws IOException;
    PrintingImage getPrintingImage(BufferedImage image) throws IllegalImageSizeException;

    enum CutMode {
        FULL(),   //  GIANT-100, GIANT-150 and GIANT PRO does not support ‘Full-Cut’ function.
        PART()
    }
}
