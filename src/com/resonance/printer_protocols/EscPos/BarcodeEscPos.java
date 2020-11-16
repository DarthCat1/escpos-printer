package com.resonance.printer_protocols.EscPos;

import com.resonance.printer_protocols.Barcode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class BarcodeEscPos implements EscPosConst, Barcode{

    private byte barcodeHeight; //  Each height unit corresponds to a dot of 0.125 mm, so the final height
                                        //  is n x 0.125 mm where 1 ≤ n ≤ 255. The default is n=162.
    private BarcodeWidthEnum barcodeWidth;  //  This command changes the barcode width where n=2 correspond to
                                            //  normal width, n=3 is double width and n=4 is quadruple width. The default is n=3.
    private HriPositionEnum hriPosition;   //  n=0: No HRI
                                            //  n=1: On top of the barcode
                                             //  n=2: On the bottom of the barcode (default)
                                            //  n=3: Both on top and on the bottom of the barcode
    private HriFontEnum hriFont;    //  For a normal font (‘N’ characters per line), n=0 or n=48. For a
                                    //  condensed font (‘N’ characters per line), n=1 or n=49. The default is normal font.

    private static final HashMap<BarcodeFormat, byte []> BARCODE_FORMAT_BYTE_HASH_MAP;
    private static final HashMap <BarcodeWidthEnum, Byte> WIDTH_ENUM_HASH_MAP;
    private static final HashMap <HriPositionEnum, Byte> HRI_POSITION_ENUM_HASH_MAP;
    private static final HashMap <HriFontEnum, Byte> HRI_FONT_ENUM_HASH_MAP;


    static {
        BARCODE_FORMAT_BYTE_HASH_MAP = new HashMap<>();
        BARCODE_FORMAT_BYTE_HASH_MAP.put(BarcodeFormat.EAN_8, new byte[]{0x44, 0x07});
        BARCODE_FORMAT_BYTE_HASH_MAP.put(BarcodeFormat.EAN_13, new byte[]{0x43, 0x0C});
        BARCODE_FORMAT_BYTE_HASH_MAP.put(BarcodeFormat.CODE_128, new byte[] {0x49});

        WIDTH_ENUM_HASH_MAP = new HashMap<>();
        WIDTH_ENUM_HASH_MAP.put(BarcodeWidthEnum.NORMAL_WIDTH, (byte) 2);
        WIDTH_ENUM_HASH_MAP.put(BarcodeWidthEnum.DOUBLE_WIDTH, (byte) 3);
        WIDTH_ENUM_HASH_MAP.put(BarcodeWidthEnum.QUADRUPLE_WIDTH, (byte) 4);

        HRI_POSITION_ENUM_HASH_MAP = new HashMap<>();
        HRI_POSITION_ENUM_HASH_MAP.put(HriPositionEnum.NO_HRI, (byte) 0);
        HRI_POSITION_ENUM_HASH_MAP.put(HriPositionEnum.ON_TOP, (byte) 1);
        HRI_POSITION_ENUM_HASH_MAP.put(HriPositionEnum.ON_BOTTOM, (byte) 2);
        HRI_POSITION_ENUM_HASH_MAP.put(HriPositionEnum.BOTH, (byte) 3);

        HRI_FONT_ENUM_HASH_MAP = new HashMap<>();
        HRI_FONT_ENUM_HASH_MAP.put(HriFontEnum.NORMAL, (byte) 0);
        HRI_FONT_ENUM_HASH_MAP.put(HriFontEnum.CONDENSED, (byte) 1);

    }


    public BarcodeEscPos () {
        reset();
    }

    public byte [] setBarcodeHeightMillimeter(int barcodeHeight) {
        if (barcodeHeight > 31)
            throw new IllegalArgumentException("Barcode height must be not more 31 mm");

        this.barcodeHeight = (byte) (barcodeHeight/0.125);
        return new byte [] {GS, 0x68, this.barcodeHeight};
    }

    public byte [] setBarcodeHeight (byte barcodeHeight) {
        this.barcodeHeight = barcodeHeight;
        return new byte [] {GS, 0x68, this.barcodeHeight};
    }

    public byte [] setBarcodeWidth(BarcodeWidthEnum width) {
        this.barcodeWidth = width;
        return new byte[] {GS, 0x77, WIDTH_ENUM_HASH_MAP.get(barcodeWidth)};
    }

    public byte [] setHriPosition(HriPositionEnum hriPosition) {
        this.hriPosition = hriPosition;
        return new byte[] {GS, 0x48,  HRI_POSITION_ENUM_HASH_MAP.get(hriPosition)};
    }

    public byte [] setHriFont(HriFontEnum hriFont) {
        this.hriFont = hriFont;
        return new byte[] {GS, 0x66, HRI_FONT_ENUM_HASH_MAP.get(hriFont)};
    }

    public byte [] getBarcodeConfigBytes() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bytes.write(GS);
        bytes.write(0x68);
        bytes.write(barcodeHeight);
        //
        bytes.write(GS);
        bytes.write(0x77);
        bytes.write(WIDTH_ENUM_HASH_MAP.get(barcodeWidth));
        //
        bytes.write(GS);
        bytes.write(0x48);
        bytes.write(HRI_POSITION_ENUM_HASH_MAP.get(hriPosition));
        //
        bytes.write(GS);
        bytes.write(0x66);
        bytes.write(HRI_FONT_ENUM_HASH_MAP.get(hriFont));

        return bytes.toByteArray();
    }

    public byte [] getPrintBarcodeBytes(BarcodeFormat format, String barcode) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bytes.write(new byte[]{GS, 0x6B});
        if (format.equals(BarcodeFormat.CODE_128)) {
            if (barcode.getBytes().length > 128)
                throw new IllegalArgumentException("code 128 must be no more than 128 bytes");
            else {
                bytes.write(BARCODE_FORMAT_BYTE_HASH_MAP.get(BarcodeFormat.CODE_128));
                bytes.write(barcode.getBytes().length);
            }
        }

        else if (format.equals(BarcodeFormat.EAN_8)) {
            if (!barcode.matches("\\d{7}"))
                throw new IllegalArgumentException("ean-8 must be no more than 7 bytes and consist only of numbers");
            else
                bytes.write(BARCODE_FORMAT_BYTE_HASH_MAP.get(BarcodeFormat.EAN_8));
        }

        else if (format.equals(BarcodeFormat.EAN_13)) {
            if (!barcode.matches("\\d{12}"))
                throw new IllegalArgumentException("ean-13 must be no more than 12 bytes and consist only of numbers");
            else
                bytes.write(BARCODE_FORMAT_BYTE_HASH_MAP.get(BarcodeFormat.EAN_13));
        }

        bytes.write(barcode.getBytes());
        return bytes.toByteArray();
    }

    public void reset() {
        barcodeHeight = (byte) 162;
        barcodeWidth = BarcodeWidthEnum.DOUBLE_WIDTH;
        hriPosition = HriPositionEnum.ON_BOTTOM;
        hriFont = HriFontEnum.NORMAL;
    }

}
