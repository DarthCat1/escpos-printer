package com.resonance.printer_protocols.EscPos;

import com.resonance.printer_protocols.PrintingStyle;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class PrintingStyleEscPos implements PrintingStyle, EscPosConst {
    private static final HashMap <Alignment, Byte> ALIGNMENT_BYTE_MAP;
    private static final HashMap <CharFonts, Byte> CHAR_FONTS_BYTE_MAP;
    private static final HashMap <UnderlineMode, Byte> UNDERLINE_MODE_BYTE_MAP;
    private static final HashMap <CharSize, Byte> WIDTH_CHAR_BYTE_MAP;
    private static final HashMap <CharSize, Byte> HEIGHT_CHAR_BYTE_MAP;
    static {
        ALIGNMENT_BYTE_MAP = new HashMap<>();
        ALIGNMENT_BYTE_MAP.put(Alignment.LEFT, (byte) 0);
        ALIGNMENT_BYTE_MAP.put(Alignment.CENTER, (byte) 1);
        ALIGNMENT_BYTE_MAP.put(Alignment.RIGHT, (byte) 2);

        CHAR_FONTS_BYTE_MAP = new HashMap<>();
        CHAR_FONTS_BYTE_MAP.put(CharFonts.FONT_A, (byte) 0);   //  Character font A (12 × 24) selected.
        CHAR_FONTS_BYTE_MAP.put(CharFonts.FONT_B, (byte) 1);   //  Character font B (9 × 17) selected.

        UNDERLINE_MODE_BYTE_MAP = new HashMap<>();
        UNDERLINE_MODE_BYTE_MAP.put(UnderlineMode.UNDERLINE_OFF, (byte) 0);
        UNDERLINE_MODE_BYTE_MAP.put(UnderlineMode.UNDERLINE_1_DOT, (byte) 1);
        UNDERLINE_MODE_BYTE_MAP.put(UnderlineMode.UNDERLINE_2_DOT, (byte) 2);

        WIDTH_CHAR_BYTE_MAP = new HashMap<>();
        WIDTH_CHAR_BYTE_MAP.put(CharSize.x1_NORMAL, (byte) 0x00);
        WIDTH_CHAR_BYTE_MAP.put(CharSize.x2_DOUBLE, (byte) 0x10);
        WIDTH_CHAR_BYTE_MAP.put(CharSize.x3, (byte) 0x20);
        WIDTH_CHAR_BYTE_MAP.put(CharSize.x4, (byte) 0x30);
        WIDTH_CHAR_BYTE_MAP.put(CharSize.x5, (byte) 0x40);
        WIDTH_CHAR_BYTE_MAP.put(CharSize.x6, (byte) 0x50);
        WIDTH_CHAR_BYTE_MAP.put(CharSize.x7, (byte) 0x60);
        WIDTH_CHAR_BYTE_MAP.put(CharSize.x8, (byte) 0x70);

        HEIGHT_CHAR_BYTE_MAP = new HashMap<>();
        HEIGHT_CHAR_BYTE_MAP.put(CharSize.x1_NORMAL, (byte) 0x00);
        HEIGHT_CHAR_BYTE_MAP.put(CharSize.x2_DOUBLE, (byte) 0x01);
        HEIGHT_CHAR_BYTE_MAP.put(CharSize.x3, (byte) 0x02);
        HEIGHT_CHAR_BYTE_MAP.put(CharSize.x4, (byte) 0x03);
        HEIGHT_CHAR_BYTE_MAP.put(CharSize.x5, (byte) 0x04);
        HEIGHT_CHAR_BYTE_MAP.put(CharSize.x6, (byte) 0x05);
        HEIGHT_CHAR_BYTE_MAP.put(CharSize.x7, (byte) 0x06);
        HEIGHT_CHAR_BYTE_MAP.put(CharSize.x8, (byte) 0x07);
    }

    private byte underline;
    private byte alignment;
    private byte charFont;
    private boolean bold;
    private byte lineSpacing;
    private boolean isDefaultLineSpacing;
    private boolean reverseColor;
    private byte charSize;

    public PrintingStyleEscPos() {
        reset();
    }

    @Override
    public byte [] setAlignment(Alignment alignment) {
        this.alignment = ALIGNMENT_BYTE_MAP.get(alignment);
        return new byte[] {ESC, 0x61, this.alignment};
    }

    @Override
    public byte [] setBold(boolean bold) {
        this.bold = bold;
        return new byte[] {ESC, 0x45, bold ? (byte) 1 : (byte) 0};
    }

    @Override
    public byte [] setCharFonts(CharFonts charFont) {
        this.charFont = CHAR_FONTS_BYTE_MAP.get(charFont);
        return new byte[] {ESC, 0x4D, this.charFont};
    }

    @Override
    public byte [] setUnderline(UnderlineMode underline) {
        this.underline = UNDERLINE_MODE_BYTE_MAP.get(underline);
        return new byte[] {ESC, 0x2D, this.underline};
    }

    @Override
    public byte[] setLineSpacing(byte lineSpacing) {
        this.lineSpacing = lineSpacing;
        isDefaultLineSpacing = false;
        return new byte[] {ESC, 0x33, lineSpacing};
    }

    @Override
    public byte[] setDefaultLineSpacing() {
        isDefaultLineSpacing = true;
        return new byte[] {ESC, 0x32};
    }

    @Override
    public byte[] setReverseColor(boolean reverseColor) {
        this.reverseColor = reverseColor;
        return new byte[] {GS, 0x42, reverseColor ? (byte) 1 : (byte) 0};
    }

    @Override
    public byte[] setCharSize(CharSize width, CharSize height) {
        charSize = (byte) (WIDTH_CHAR_BYTE_MAP.get(width) + HEIGHT_CHAR_BYTE_MAP.get(height));
        return new byte[] {GS, 0x21, charSize};
    }

    @Override
    public void reset() {
        alignment = ALIGNMENT_BYTE_MAP.get(Alignment.LEFT);
        bold = false;
        charFont = CHAR_FONTS_BYTE_MAP.get(CharFonts.FONT_A);
        underline = UNDERLINE_MODE_BYTE_MAP.get(UnderlineMode.UNDERLINE_OFF);
        isDefaultLineSpacing = true;
        reverseColor = false;
    }

    @Override
    public byte[] getConfigBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        if (!isDefaultLineSpacing) {
            bytes.write(ESC);
            bytes.write(0x33);
            bytes.write(lineSpacing);
        }
        else {
            bytes.write(ESC);
            bytes.write(0x32);
        }
        //
        bytes.write(GS);
        bytes.write(0x21);
        bytes.write(charSize);
        //
        bytes.write(GS);
        bytes.write(0x42);
        bytes.write(reverseColor ? 1 : 0);

        //
        bytes.write(ESC);
        bytes.write(0x4D);
        bytes.write(charFont);
        //
        bytes.write(ESC);
        bytes.write(0x45);
        bytes.write(bold ? 1 : 0);
        //
        bytes.write(ESC);
        bytes.write(0x61);
        bytes.write(alignment);
        //
        bytes.write(ESC);
        bytes.write(0x2D);
        bytes.write(underline);
        return bytes.toByteArray();
    }

}
