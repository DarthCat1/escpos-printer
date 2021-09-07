package com.resonance.printer_protocols;

import java.io.IOException;

public interface Barcode {

    byte [] getBarcodeConfigBytes();
    byte [] setBarcodeHeightMillimeter(int barcodeHeightMillimeter);
    byte [] setBarcodeHeight (byte barcodeHeight);
    byte [] setBarcodeWidth (BarcodeWidthEnum barcodeWidth);
    byte [] setHriPosition(HriPositionEnum hriPosition);
    byte [] setHriFont (HriFontEnum hriFont);
    byte [] getPrintBarcodeBytes(BarcodeFormat format,  String barcode) throws IOException;

    enum BarcodeFormat {
        EAN_13("EAN-13"),
        EAN_8("EAN-8"),
        CODE_128("CODE-128");

        public String formatName;
        BarcodeFormat(String formatName){
            this.formatName = formatName;
        }
    }

    enum BarcodeWidthEnum {
        NORMAL_WIDTH(),
        DOUBLE_WIDTH(),
        QUADRUPLE_WIDTH()
    }

    enum HriPositionEnum {
        NO_HRI(),
        ON_TOP(),
        ON_BOTTOM(),
        BOTH()
    }

    enum HriFontEnum {
        NORMAL(),
        CONDENSED()
    }

}
