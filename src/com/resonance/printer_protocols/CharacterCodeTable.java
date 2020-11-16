package com.resonance.printer_protocols;

public enum CharacterCodeTable {
    CP437_USA_Standard_Europe( "cp437"),
    Katakana(),
    CP850_Multilingual( "cp850"),
    CP860_Portuguese( "cp860"),
    CP863_Canadian_French("cp863"),
    CP865_Nordic( "cp865"),
    CP857_Turkish( "cp857"),
    CP737_Greek( "cp737"),
    WPC1252(),
    CP866_Cyrillic_2("cp866"),
    CP852_Latin2( "cp852"),
    CP858_Euro("cp858"),
    TIS11_Thai(),
    TIS18_Thai(),
    Farsi(),
    WPC775_BalticRim(),
    CP855_Cyrillic( "cp855"),
    CP862_Hebrew( "cp862"),
    CP864_Arabic( "cp864"),
    CP1125_Ukrainian(),   //  not supported by Sam4s Giant-100
    WCP1250_Latin2("cp1250"),
    WCP1251_Cyrillic("cp1251"),
    WCP1253_Greek( "cp1253"),
    WCP1255_Hebrew( "cp1255"),
    WCP1256_Arabic("cp1256"),
    WCP1257_BalticRim( "cp1257"),
    WCP1258_Vietnamese("cp1258"),
    KZ_1048_Kazakhstan(),
    Thai_Industrial_Standard_620(),
    Thai_42(),
    Thai_14(),
    Thai_16(),
    IRAN_SYSTEM_Arabic(),
    Space_Page();
    public String charsetName;

    CharacterCodeTable() {
        this.charsetName = "cp437";
    }

    CharacterCodeTable(String charsetName) {
        this.charsetName = charsetName;
    }
}