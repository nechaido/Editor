package com.nechaido.editor.jeditor.document.simpleDocument;

import java.awt.*;

/**
 * Created by nechaido on 5/8/16.
 */
public class Style {

    private FontMetrics fontMetrics;

    public Style(Font font) {
        Canvas canvas = new Canvas();
        fontMetrics = canvas.getFontMetrics(font);
    }

    public Font getFont() {
        return fontMetrics.getFont();
    }

    public Dimension getCharSize(char c){
        return new Dimension(fontMetrics.charWidth(c), fontMetrics.getHeight());
    }

    public int getCharHeight(char c){
        return fontMetrics.getHeight();
    }

    public int getCharWidth(char c){
        return fontMetrics.charWidth(c);
    }
}
