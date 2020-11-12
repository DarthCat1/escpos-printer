package com.resonance.escpos;

import com.resonance.printers.Printer;

import java.io.*;

public class EscPos implements EscPosConst{

    private Printer printer;
    private CharacterCodeTable characterCodeTable;


    public EscPos (Printer printer) throws IOException {
        this.printer = printer;
        initConnection();
    }

    public enum CharacterCodeTable {
        CP437_USA_Standard_Europe(0, "cp437"),
        Katakana(1),
        CP850_Multilingual(2, "cp850"),
        CP860_Portuguese(3, "cp860"),
        CP863_Canadian_French(4, "cp863"),
        CP865_Nordic(5, "cp865"),
        CP857_Turkish(13, "cp857"),
        CP737_Greek(14, "cp737"),
        WPC1252(16),
        CP866_Cyrillic_2(17, "cp866"),
        CP852_Latin2(18, "cp852"),
        CP858_Euro(19, "cp858"),
        TIS11_Thai(21),
        TIS18_Thai(26),
        Farsi(27),
        WPC775_BalticRim(33),
        CP855_Cyrillic(34, "cp855"),
        CP862_Hebrew(36, "cp862"),
        CP864_Arabic(37, "cp864"),
        CP1125_Ukrainian(44),   //  ???
        WCP1250_Latin2(45, "cp1250"),
        WCP1251_Cyrillic(46, "cp1251"),
        WCP1253_Greek(47, "cp1253"),
        WCP1255_Hebrew(49, "cp1255"),
        WCP1256_Arabic(50, "cp1256"),
        WCP1257_BalticRim(51, "cp1257"),
        WCP1258_Vietnamese(52, "cp1258"),
        KZ_1048_Kazakhstan(53),
        Thai_Industrial_Standard_620 (95),
        Thai_42(96),
        Thai_14(97),
        Thai_16(98),
        IRAN_SYSTEM_Arabic(99),
        Space_Page(255);
        public int value;
        public String charsetName;

        CharacterCodeTable(int value) {
            this.value = value;
            this.charsetName = "cp437";
        }

        CharacterCodeTable(int value, String charsetName) {
            this.value = value;
            this.charsetName = charsetName;
        }
    }

    public void setCharacterCodeTable(CharacterCodeTable characterCodeTable) throws IOException {
        this.characterCodeTable = characterCodeTable;
        write(ESC);
        write(0x74);
        write(17);
    }

    public enum CutMode {
        FULL(67),   //  GIANT-100, GIANT-150 and GIANT PRO does not support ‘Full-Cut’ function.
        PART(0);
        public int value;

        CutMode(int value) {
            this.value = value;
        }
    }

    public void write (int bytes) throws IOException {
        OutputStream outputStream = new FileOutputStream(printer.getPort());
        outputStream.write(bytes);
        outputStream.close();
    }

    public void write (String text) throws IOException {
        OutputStream outputStream = new FileOutputStream(printer.getPort());
        outputStream.write(text.getBytes());
        outputStream.close();
    }

    public void write (byte [] bytes) throws IOException {
        OutputStream outputStream = new FileOutputStream(printer.getPort());
        outputStream.write(bytes);
        outputStream.close();

    }

    //  write text and line feed
    public void writeLF (String text) throws IOException {
        OutputStream outputStream = new FileOutputStream(printer.getPort());
        outputStream.write(text.getBytes());
        outputStream.write(EscPosConst.LF);
        outputStream.close();
    }

    public void write(byte b[], int off, int len) throws IOException {
        OutputStream outputStream = new FileOutputStream(printer.getPort());
        outputStream.write(b, off, len);
        outputStream.close();
    }

    public void writeLF(PrintingStyle printingStyle, String text) throws IOException {
        byte[] configBytes = printingStyle.getConfigBytes();
        write(configBytes, 0, configBytes.length);
        OutputStream outputStream = new FileOutputStream(printer.getPort());
        outputStream.write(text.getBytes());
        outputStream.close();
    }

    public void cut(CutMode mode) throws IOException {
        write(EscPosConst.GS);
        write(0x56);
        write(mode.value);
    }

    public void cut () throws IOException {
        write(EscPosConst.GS);
        write(0x56);
        write(CutMode.PART.value);
    }

    public void feed (int linesCount) throws IOException {
        if (linesCount < 1 || linesCount > 255) {
            throw new IllegalArgumentException("lineCounts must be between 1 and 255");
        }
        if(linesCount == 1){
            write(LF);
        }
        else {
            write(ESC);
            write(0x64);
            write(linesCount);
        }
    }

    public void initConnection() throws IOException {
        write(ESC);
        write(0x40);
        setCharacterCodeTable(CharacterCodeTable.CP866_Cyrillic_2);
    }

}
