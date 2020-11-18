package com.resonance.printer_protocols;

import java.util.HashMap;
import java.util.Map;

public interface PrintingStyle {
    byte[] getConfigBytes();
    void reset();
    byte [] setBold(boolean bold);
    byte [] setCharFonts(CharFonts charFonts);
    byte [] setAlignment(Alignment alignment);
    byte [] setUnderline(UnderlineMode underlineMode);
    byte [] setLineSpacing(byte lineSpacing);
    byte [] setDefaultLineSpacing();
    byte [] setReverseColor(boolean reverseColor);
    byte [] setCharSize(CharSize width, CharSize height);

    enum Alignment {
        LEFT,
        CENTER,
        RIGHT
    }

    enum CharFonts {
        FONT_A,
        FONT_B
    }

    enum UnderlineMode {
        UNDERLINE_OFF(),
        UNDERLINE_1_DOT(),
        UNDERLINE_2_DOT()
    }

    enum CharSize {
        x1_NORMAL(),
        x2_DOUBLE(),
        x3(),
        x4(),
        x5(),
        x6(),
        x7(),
        x8()
    }
}
