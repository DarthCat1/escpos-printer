package com.resonance.printer_protocols.EscPos;

import com.resonance.printer_protocols.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @Override
    public byte [] setCharacterCodeTable(CharacterCodeTable characterCodeTable) {
        characterCodeTableEscPos.setCurrentCharacterCodeTable(characterCodeTable);
        return new byte[]{ESC, 0x74, characterCodeTableEscPos.getCurrentCharacterCodeTableByte()};
    }

    @Override
    public Barcode getBarcode() {
        return new BarcodeEscPos();
    }

    @Override
    public QrCode getQrCode() {
        return new QrCodeEscPos();
    }

    @Override
    public String getCharsetName() {
        return characterCodeTableEscPos.getCurrentCharacterCodeTable().charsetName;
    }

    @Override
    public byte [] cut(CutMode mode) {
        return new byte[] {GS, 0x56, CUT_MODE_HASH_MAP.get(mode)};
    }

    @Override
    public byte [] cut () {
        return new byte[] {GS, 0x56, CUT_MODE_HASH_MAP.get(cutMode)};
    }

    @Override
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

    @Override
    public byte [] initConnection() {
        return new byte[] {ESC, 0x40};
    }

    @Override
    public byte[] printStoredImage() {
        return new byte[] {GS, 0x28, 0x4C, 0x02, 0x00, 0x30, (byte)2};
    }

    @Override
    public byte[] storeImage (PrintingImage printingImage) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int width = printingImage.getBufferedImage().getWidth();
        int height = printingImage.getBufferedImage().getHeight();
        int a = 48;
        int m = 48;
        int c = 49;
        int bx = 1;
        int by = 1;
        byte xL = (byte) width;
        byte xH = (byte) (width >> 8);
        byte yL = (byte) height;
        byte yH = (byte) (height >> 8);

        int k = ((width + 7)/8) * height;
        int commandLength = 10 + k;
        byte pL = (byte) commandLength;
        byte pH = (byte) (commandLength >> 8);

        os.write(new byte[] {GS, 0x28, 0x4C, pL, pH, (byte) m, 0x70, (byte) a, (byte) bx, (byte) by, (byte) c,
                xL, xH, yL, yH});
        os.write(printingImage.getImageBytesChain());
        return os.toByteArray();
    }

    @Override
    public PrintingImage getPrintingImage(BufferedImage image) throws IllegalImageSizeException{
        return new PrintingImageEscPos(image);
    }

}
