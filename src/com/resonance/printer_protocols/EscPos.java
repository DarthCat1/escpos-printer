package com.resonance.printer_protocols;

import java.io.*;

public class EscPos implements PrinterProtocol,EscPosConst{

    public EscPos () {

    }

    public byte [] setCharacterCodeTable(CharacterCodeTable characterCodeTable) {
        return  new byte[]{ESC, 0x74, (byte) characterCodeTable.value};
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
