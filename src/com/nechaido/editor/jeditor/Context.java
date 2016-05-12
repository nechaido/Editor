package com.nechaido.editor.jeditor;

import com.nechaido.editor.jeditor.commands.simpleDocument.elementFactories.PictureFactory;
import com.nechaido.editor.jeditor.commands.simpleDocument.elementFactories.SymbolFactory;
import com.nechaido.editor.jeditor.document.simpleDocument.SimpleDocument;

/**
 * Created by nechaido on 5/9/16.
 */
public class Context {
    private SimpleDocument document;
    private SymbolFactory symbolFactory;
    private PictureFactory pictureFactory;

    private Carriage carriage;
    private Carriage selectionStart;
    private Carriage selectionEnd;

    public Context(SimpleDocument document, Carriage carriage, Carriage selectionStart, Carriage selectionEnd) {
        this.document = document;
        this.carriage = carriage;
        this.selectionStart = selectionStart;
        this.selectionEnd = selectionEnd;
        symbolFactory = SymbolFactory.getInstance();
        pictureFactory = PictureFactory.getInstance();
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
}
