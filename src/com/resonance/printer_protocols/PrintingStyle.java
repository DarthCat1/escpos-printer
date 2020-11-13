package com.resonance.printer_protocols;

import java.util.HashMap;
import java.util.Map;

public interface PrintingStyle {
    byte[] getConfigBytes();
    void reset();
    byte [] setBold(boolean bold);
    byte [] setCharFonts(CharFonts charFonts);
    byte [] setAlignment(Alignment alignment);

    enum Alignment {
        LEFT,
        CENTER,
        RIGHT;
    }

    enum CharFonts {
        FONT_A,
        FONT_B;
    }
}
