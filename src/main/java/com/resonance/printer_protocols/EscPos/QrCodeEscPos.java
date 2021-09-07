package com.resonance.printer_protocols.EscPos;

import com.resonance.printer_protocols.QrCode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class QrCodeEscPos implements QrCode, EscPosConst {

    private static final HashMap <QrCodeModelEnum, Byte> QR_CODE_MODEL_HASH_MAP;
    private static final HashMap <QrCodeErrorCorrectionLvlEnum, Byte> QR_CODE_ERROR_CORRECTION_LVL_HASH_MAP;
    static {
        QR_CODE_MODEL_HASH_MAP = new HashMap<>();
        QR_CODE_MODEL_HASH_MAP.put(QrCodeModelEnum.MODEL_2, (byte) 0x32);
        QR_CODE_MODEL_HASH_MAP.put(QrCodeModelEnum.MICRO_QR, (byte) 30);

        QR_CODE_ERROR_CORRECTION_LVL_HASH_MAP = new HashMap<>();
        QR_CODE_ERROR_CORRECTION_LVL_HASH_MAP.put(QrCodeErrorCorrectionLvlEnum.LEVEL_L, (byte) 0x30);
        QR_CODE_ERROR_CORRECTION_LVL_HASH_MAP.put(QrCodeErrorCorrectionLvlEnum.LEVEL_M, (byte) 0x31);
        QR_CODE_ERROR_CORRECTION_LVL_HASH_MAP.put(QrCodeErrorCorrectionLvlEnum.LEVEL_Q, (byte) 0x32);
        QR_CODE_ERROR_CORRECTION_LVL_HASH_MAP.put(QrCodeErrorCorrectionLvlEnum.LEVEL_H, (byte) 0x33);
    }

    private QrCodeModelEnum model;
    private byte moduleSize;
    private QrCodeErrorCorrectionLvlEnum errorCorrectionLvl;

    QrCodeEscPos () {
        model = QrCodeModelEnum.MICRO_QR;
        moduleSize = (byte) 0xFE;
        errorCorrectionLvl = QrCodeErrorCorrectionLvlEnum.LEVEL_H;
    }

    public byte [] setModel(QrCodeModelEnum model) {
        this.model = model;
        return new byte[] {GS,  0x28, 0x6b, 0x04, 0x00, 0x31, 0x41, QR_CODE_MODEL_HASH_MAP.get(model), 0x00};
    }

    public byte [] setModuleSize(byte moduleSize) {
        this.moduleSize = moduleSize;
        return new byte[]{GS, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x43, moduleSize};
    }

    public byte [] setErrorCorrectionLvl(QrCodeErrorCorrectionLvlEnum errorCorrectionLvl) {
        this.errorCorrectionLvl = errorCorrectionLvl;
        return new byte[]{GS, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x45, QR_CODE_ERROR_CORRECTION_LVL_HASH_MAP.get(errorCorrectionLvl)};
    }

    public byte [] getQrCodePrintBytes (String data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int store_len = data.length() + 3;
        byte store_pL = (byte) (store_len % 256);
        byte store_pH = (byte) (store_len / 256);

        // QR Code: Select the model
        // Hex     1D      28      6B      04      00      31      41      n1(x32)     n2(x00) - size of model
        // set n1 [49 x31, model 1] [50 x32, model 2] [51 x33, micro qr code]
        outputStream.write(new byte[]{GS,  0x28, 0x6b, 0x04, 0x00, 0x31, 0x41, QR_CODE_MODEL_HASH_MAP.get(model), 0x00});
        // QR Code: Set the size of module
        // Hex      1D      28      6B      03      00      31      43      n
        // n depends on the printer
        outputStream.write(new byte[]{GS, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x43, moduleSize});
        //          Hex     1D      28      6B      03      00      31      45      n
        // Set n for error correction [48 x30 -> 7%] [49 x31-> 15%] [50 x32 -> 25%] [51 x33 -> 30%]
        outputStream.write(new byte[]{GS, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x45, QR_CODE_ERROR_CORRECTION_LVL_HASH_MAP.get(errorCorrectionLvl)});
        // QR Code: Store the data in the symbol storage area
        // Hex      1D      28      6B      pL      pH      31      50      30      d1...dk
        //                        1D          28          6B         pL          pH  cn(49->x31) fn(80->x50) m(48->x30) d1…dk
        outputStream.write(new byte[]{GS, 0x28, 0x6b, store_pL, store_pH, 0x31, 0x50, 0x30});
        outputStream.write(data.getBytes());
        // QR Code: Print the symbol data in the symbol storage area
        // Hex      1D      28      6B      03      00      31      51      m
        outputStream.write(new byte[]{GS, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x51, 0x30});
        return outputStream.toByteArray();
    }

}
