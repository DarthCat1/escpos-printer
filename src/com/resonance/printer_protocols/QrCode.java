package com.resonance.printer_protocols;

import java.io.IOException;

public interface QrCode {
    byte [] getQrCodePrintBytes (String data) throws IOException;
    byte [] setModel(QrCodeModelEnum model);
    byte [] setModuleSize(byte moduleSize);
    byte [] setErrorCorrectionLvl(QrCodeErrorCorrectionLvlEnum errorCorrectionLvl);


    enum  QrCodeModelEnum {
        MICRO_QR(),
        MODEL_2()
    }

    enum QrCodeErrorCorrectionLvlEnum {
        LEVEL_L(),  //  7%
        LEVEL_M(),  //  15
        LEVEL_Q(),  //  25%
        LEVEL_H()   //  30%
    }
}
