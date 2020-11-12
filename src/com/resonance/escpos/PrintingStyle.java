package com.resonance.escpos;

import java.io.ByteArrayOutputStream;

public class PrintingStyle implements EscPosConst{
    //  default style include left alignment, not bold
    private Alignment alignment;
    private boolean bold;
    private CharFonts charFonts;

    public PrintingStyle() {
        reset();
    }


    public enum CharFonts {
        FONT_A(0),  //  Character font A (12 × 24) selected.
        FONT_B(1);  //  Character font B (9 × 17) selected.
        private int value;

        CharFonts (int value) {
            this.value = value;
        }
    }


    public enum Alignment {
        LEFT(0),
        CENTER(1),
        RIGHT(2);
        private int value;

        Alignment(int value) {
            this.value = value;
        }
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setCharFonts(CharFonts charFonts) {
        this.charFonts = charFonts;
    }


    public void reset() {
        this.alignment = Alignment.LEFT;
        this.bold = false;
        this.charFonts = CharFonts.FONT_A;
    }

    protected byte[] getConfigBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bytes.write(ESC);
        bytes.write(0x4D);
        bytes.write(charFonts.value);
        //
        bytes.write(ESC);
        bytes.write(0x45);
        bytes.write(bold ? 1 : 0);
        //
        bytes.write(ESC);
        bytes.write('a');
        bytes.write(alignment.value);
        //

        return bytes.toByteArray();
    }

}
