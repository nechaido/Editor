package com.nechaido.editor.jeditor;

import com.nechaido.editor.jeditor.elementFactories.PictureFactory;
import com.nechaido.editor.jeditor.elementFactories.StyleFactory;
import com.nechaido.editor.jeditor.elementFactories.SymbolFactory;
import com.nechaido.editor.jeditor.document.simpleDocument.SimpleDocument;
import com.nechaido.editor.jeditor.document.simpleDocument.Style;

import java.awt.*;

/**
 * Created by nechaido on 5/9/16.
 */
public class Context {
    private SimpleDocument document;

    private StyleFactory styleFactory;
    private SymbolFactory symbolFactory;
    private PictureFactory pictureFactory;

    private Carriage carriage;
    private Carriage selectionStart;
    private Carriage selectionEnd;

    private Style currentStyle;

    public Context(SimpleDocument document, Carriage carriage, Carriage selectionStart, Carriage selectionEnd, Style style) {
        this.document = document;
        this.carriage = carriage;
        this.selectionStart = selectionStart;
        this.selectionEnd = selectionEnd;
        styleFactory = StyleFactory.getInstance();
        symbolFactory = SymbolFactory.getInstance();
        pictureFactory = PictureFactory.getInstance();
        styleFactory.add(new Style(style.getFont()));
        currentStyle = styleFactory.getStyle(style.getFont());
    }

    public SimpleDocument getDocument() {
        return document;
    }

    public void setDocument(SimpleDocument document) {
        this.document = document;
    }

    public SymbolFactory getSymbolFactory() {
        return symbolFactory;
    }

    public PictureFactory getPictureFactory() {
        return pictureFactory;
    }

    public Carriage getCarriage() {
        return carriage;
    }

    public Carriage getSelectionStart() {
        return selectionStart;
    }

    public Carriage getSelectionEnd() {
        return selectionEnd;
    }

    public Style getCurrentStyle() {
        return currentStyle;
    }

    public void setCurrentStyle(Style style) {
        currentStyle = style;
    }

    public StyleFactory getStyleFactory() {
        return styleFactory;
    }
}
