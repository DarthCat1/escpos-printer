package com.resonance.printer_protocols.EscPos;

import com.resonance.printer_protocols.CharacterCodeTable;

import java.util.HashMap;

class CharacterCodeTableEscPos {

    private static final HashMap <CharacterCodeTable, Byte> CHARACTER_CODE_TABLE_BYTE_HASH_MAP;
    static {
        CHARACTER_CODE_TABLE_BYTE_HASH_MAP = new HashMap<>();
        CHARACTER_CODE_TABLE_BYTE_HASH_MAP.put(CharacterCodeTable.CP437_USA_Standard_Europe, (byte) 0);
        CHARACTER_CODE_TABLE_BYTE_HASH_MAP.put(CharacterCodeTable.CP850_Multilingual, (byte) 2);
        CHARACTER_CODE_TABLE_BYTE_HASH_MAP.put(CharacterCodeTable.CP1125_Ukrainian, (byte) 44);
        CHARACTER_CODE_TABLE_BYTE_HASH_MAP.put(CharacterCodeTable.CP866_Cyrillic_2, (byte) 17);
        CHARACTER_CODE_TABLE_BYTE_HASH_MAP.put(CharacterCodeTable.CP855_Cyrillic, (byte) 34);
        CHARACTER_CODE_TABLE_BYTE_HASH_MAP.put(CharacterCodeTable.WCP1251_Cyrillic, (byte) 46);
        }

    private CharacterCodeTable currentCharacterCodeTable;

        CharacterCodeTableEscPos () {
            this.currentCharacterCodeTable = CharacterCodeTable.CP866_Cyrillic_2;
        }

    public CharacterCodeTable getCurrentCharacterCodeTable() {
        return currentCharacterCodeTable;
    }

    public byte getCurrentCharacterCodeTableByte() {
            return CHARACTER_CODE_TABLE_BYTE_HASH_MAP.get(currentCharacterCodeTable);
    }

    public void setCurrentCharacterCodeTable(CharacterCodeTable currentCharacterCodeTable) {
        this.currentCharacterCodeTable = currentCharacterCodeTable;
    }

}