package com.resonance.printer_protocols;

public interface PrinterProtocol {
    byte [] setCharacterCodeTable(CharacterCodeTable characterCodeTable);
    byte [] feed (int linesCount);
    byte [] cut(CutMode cutMode);
    byte [] cut ();
    byte [] initConnection();
    String getCharsetName();
    Barcode getBarcode();
    QrCode getQrCode();

    enum CutMode {
        FULL(),   //  GIANT-100, GIANT-150 and GIANT PRO does not support ‘Full-Cut’ function.
        PART()
    }
}
