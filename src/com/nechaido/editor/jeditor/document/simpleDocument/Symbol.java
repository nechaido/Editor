package com.nechaido.editor.jeditor.document.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.drawer.Drawer;
import com.nechaido.editor.jeditor.drawer.simpleDocument.SimpleDocumentDrawer;

import java.awt.*;
import java.util.Collection;

/**
 * Created by nechaido on 5/7/16.
 */
public class Symbol extends PrimitiveElement {

    private char character;

    public Symbol(char character) {
        this.character = character;
    }

    public Dimension getSize(Style style) {
        return style.getCharSize(character);
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public void drawBy(Drawer drawer) {
        ((SimpleDocumentDrawer)drawer).draw(this);
    }

    @Override
    public boolean isMajor() {
        return false;
    }


}
