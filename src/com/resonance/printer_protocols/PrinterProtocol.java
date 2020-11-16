package com.resonance.printer_protocols;

import com.resonance.printer_protocols.EscPos.EscPos;

public interface PrinterProtocol {
    byte [] setCharacterCodeTable(CharacterCodeTable characterCodeTable);
    byte [] feed (int linesCount);
    byte [] cut(EscPos.CutMode mode);
    byte [] cut ();
    byte [] initConnection();
    String getCharsetName();
    Barcode getBarcode();

    enum CutMode {
        FULL(),   //  GIANT-100, GIANT-150 and GIANT PRO does not support ‘Full-Cut’ function.
        PART()
    }
}
