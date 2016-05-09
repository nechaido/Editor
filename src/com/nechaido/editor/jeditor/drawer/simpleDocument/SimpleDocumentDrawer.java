package com.nechaido.editor.jeditor.drawer.simpleDocument;

import com.nechaido.editor.jeditor.document.*;
import com.nechaido.editor.jeditor.document.simpleDocument.*;
import com.nechaido.editor.jeditor.drawer.Drawer;

import java.awt.*;

/**
 * Created by nechaido on 5/7/16.
 */
public class SimpleDocumentDrawer extends Drawer {
    private Style style;
    private SimpleDocument document;
    private Dimension currentOffset;

    public SimpleDocumentDrawer(Graphics graphics, SimpleDocument document, Style style) {
        super(graphics);
        this.document = document;
        this.style = style;
        currentOffset = new Dimension(0,0);
    }

    @Override
    public void draw(Document document) {

    }

    public void draw(SimpleDocument simpleDocument) {

    }


    public void draw(ElementComposition row) {

    }

    public void draw(Symbol symbol) {

    }

    public void draw(Picture picture) {

    }
}
