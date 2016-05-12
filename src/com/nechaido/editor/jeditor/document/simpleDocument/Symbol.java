package com.nechaido.editor.jeditor.document.simpleDocument;

/**
 * Created by nechaido on 5/7/16.
 */
public class Symbol extends PrimitiveElement {

    private char character;

    public Symbol(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public boolean isMajor() {
        return Character.isSpaceChar(character);
    }


}
