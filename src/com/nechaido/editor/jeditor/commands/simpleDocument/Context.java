package com.nechaido.editor.jeditor.commands.simpleDocument;

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

    public Context(SimpleDocument document) {
        this.document = document;
        symbolFactory = SymbolFactory.getInstance();
        pictureFactory = pictureFactory.getInstance();
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
}
