package com.resonance.printer_protocols.EscPos;

import com.resonance.printer_protocols.Barcode;
import com.resonance.printer_protocols.CharacterCodeTable;
import com.resonance.printer_protocols.PrinterProtocol;
import com.resonance.printer_protocols.QrCode;

import java.util.HashMap;

public class EscPos implements PrinterProtocol, EscPosConst {

    private static final HashMap<PrinterProtocol.CutMode, Byte> CUT_MODE_HASH_MAP;

    static {
        CUT_MODE_HASH_MAP = new HashMap<>();
        CUT_MODE_HASH_MAP.put(PrinterProtocol.CutMode.FULL, (byte) 0x67);
        CUT_MODE_HASH_MAP.put(PrinterProtocol.CutMode.PART, (byte) 0);
    }

    private CharacterCodeTableEscPos characterCodeTableEscPos;
    private CutMode cutMode;

    public EscPos () {
        characterCodeTableEscPos = new CharacterCodeTableEscPos();
        cutMode = CutMode.PART;
    }

    public byte [] setCharacterCodeTable(CharacterCodeTable characterCodeTable) {
        characterCodeTableEscPos.setCurrentCharacterCodeTable(characterCodeTable);
        return new byte[]{ESC, 0x74, characterCodeTableEscPos.getCurrentCharacterCodeTableByte()};
    }

    public Barcode getBarcode() {
        return new BarcodeEscPos();
    }

    public QrCode getQrCode() {
        return new QrCodeEscPos();
    }

    public String getCharsetName() {
        return characterCodeTableEscPos.getCurrentCharacterCodeTable().charsetName;
    }

    public byte [] cut(CutMode mode) {
        return new byte[] {GS, 0x56, CUT_MODE_HASH_MAP.get(mode)};
    }

    public byte [] cut () {
        return new byte[] {GS, 0x56, CUT_MODE_HASH_MAP.get(cutMode)};
    }

    public byte [] feed (int linesCount) {
        if (linesCount < 1 || linesCount > 255) {
            throw new IllegalArgumentException("lineCounts must be between 1 and 255");
        }
        if(linesCount == 1){
            return new byte[] {LF};
        }
        else {
            return new byte[] {ESC, 0x64, (byte)linesCount};
        }
    }

    public byte [] initConnection() {
        return new byte[] {ESC, 0x40};
    }

}
