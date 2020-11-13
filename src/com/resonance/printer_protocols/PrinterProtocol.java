package com.resonance.printer_protocols;

public interface PrinterProtocol {
    byte [] setCharacterCodeTable(CharacterCodeTable characterCodeTable);
    byte [] feed (int linesCount);
    byte [] cut(EscPos.CutMode mode);
    byte [] cut ();
    byte [] initConnection();
}
