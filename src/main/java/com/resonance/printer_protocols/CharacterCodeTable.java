package com.resonance.printer_protocols;

public enum CharacterCodeTable {
    CP437_USA_Standard_Europe( "cp437"),
    CP850_Multilingual( "cp850"),
    CP866_Cyrillic_2("cp866"),
    CP855_Cyrillic( "cp855"),
    CP1125_Ukrainian(),   //  not supported by Sam4s Giant-100
    WCP1251_Cyrillic("cp1251");

    public String charsetName;

    CharacterCodeTable() {
        this.charsetName = "cp437";
    }

    CharacterCodeTable(String charsetName) {
        this.charsetName = charsetName;
    }
}