package com.resonance.printer_protocols;

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
    CP1125_Ukrainian(44),   //  not supported by Sam4s Giant-100
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
