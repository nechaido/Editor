package com.nechaido.editor.jeditor.drawer.simpleDocument;

import com.nechaido.editor.jeditor.document.simpleDocument.Style;
import com.nechaido.editor.jeditor.drawer.Drawer;
import com.nechaido.editor.jeditor.drawer.VisualElement;

import java.awt.*;

/**
 * Created by nechaido on 5/9/16.
 */
public class Word implements VisualElement {
    private StringBuffer stringBuffer;
    private Style style;
    private Dimension size;

    public Word(Style style) {
        stringBuffer = new StringBuffer();
        this.style = style;
        size = new Dimension();
    }

    public void append(char character) {
        stringBuffer.append(character);
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    public Style getStyle() {
        return style;
    }

    @Override
    public void drawBy(Drawer drawer) {
        ((SimpleDocumentDrawer) drawer).draw(this);
    }

    @Override
    public String toString() {
        return stringBuffer.toString();
    }
}
