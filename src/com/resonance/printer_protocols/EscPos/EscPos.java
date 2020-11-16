package com.resonance.printer_protocols.EscPos;

import com.resonance.printer_protocols.Barcode;
import com.resonance.printer_protocols.CharacterCodeTable;
import com.resonance.printer_protocols.PrinterProtocol;

public class EscPos implements PrinterProtocol, EscPosConst {

    private Barcode barcode;
    private CharacterCodeTableEscPos characterCodeTableEscPos;

    public EscPos () {
        characterCodeTableEscPos = new CharacterCodeTableEscPos();
    }

    public byte [] setCharacterCodeTable(CharacterCodeTable characterCodeTable) {
        characterCodeTableEscPos.setCurrentCharacterCodeTable(characterCodeTable);
        return new byte[]{ESC, 0x74, characterCodeTableEscPos.getCurrentCharacterCodeTableByte()};
    }

    public Barcode getBarcode() {
        return barcode == null ? barcode = new BarcodeEscPos() : barcode;
    }

    public String getCharsetName() {
        return characterCodeTableEscPos.getCurrentCharacterCodeTable().charsetName;
    }

    public enum CutMode {
        FULL(67),   //  GIANT-100, GIANT-150 and GIANT PRO does not support ‘Full-Cut’ function.
        PART(0);
        public int value;

        CutMode(int value) {
            this.value = value;
        }
    }

    public byte [] cut(CutMode mode) {
        return new byte[] {GS, 0x56, (byte)mode.value};
    }

    public byte [] cut () {
        return new byte[] {GS, 0x56, (byte)CutMode.PART.value};
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
