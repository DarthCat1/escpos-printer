package com.resonance.printer_protocols;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PrintingStyleEscPOS implements PrintingStyle, EscPosConst{
    //  default style include left alignment, not bold, CharFont A
    private byte selectedAlignment;
    private byte selectedCharFonts;
    private boolean bold;
    Map <Alignment, Byte> alignmentMap;
    Map <CharFonts, Byte> charFontsMap;

    public PrintingStyleEscPOS() {
        alignmentMap = new HashMap<>();
        alignmentMap.put(Alignment.LEFT, (byte) 0);
        alignmentMap.put(Alignment.CENTER, (byte) 1);
        alignmentMap.put(Alignment.RIGHT, (byte) 2);
        charFontsMap = new HashMap<>();
        charFontsMap.put(CharFonts.FONT_A, (byte) 0);   //  Character font A (12 × 24) selected.
        charFontsMap.put(CharFonts.FONT_B, (byte) 1);   //  Character font B (9 × 17) selected.
        reset();
    }

    public byte [] setAlignment(Alignment alignment) {
        selectedAlignment = alignmentMap.get(alignment);
        return new byte[] {ESC, 0x61, selectedAlignment};
    }

    public byte [] setBold(boolean bold) {
        this.bold = bold;
        return new byte[] {ESC, 0x45, bold ? (byte) 1 : (byte) 0};
    }

    public byte [] setCharFonts(CharFonts charFonts) {
        selectedCharFonts = charFontsMap.get(charFonts);
        return new byte[] {ESC, 0x4D, selectedCharFonts};
    }


    public void reset() {
        this.selectedAlignment = alignmentMap.get(Alignment.LEFT);
        this.bold = false;
        selectedCharFonts = charFontsMap.get(CharFonts.FONT_A);
    }

    public byte[] getConfigBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bytes.write(ESC);
        bytes.write(0x4D);
        bytes.write(selectedCharFonts);
        //
        bytes.write(ESC);
        bytes.write(0x45);
        bytes.write(bold ? 1 : 0);
        //
        bytes.write(ESC);
        bytes.write(0x61);
        bytes.write(selectedAlignment);
        //
        return bytes.toByteArray();
    }

}
