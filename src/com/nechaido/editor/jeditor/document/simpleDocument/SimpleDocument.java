package com.nechaido.editor.jeditor.document.simpleDocument;

import com.nechaido.editor.jeditor.document.Document;
import com.nechaido.editor.jeditor.drawer.Drawer;
import com.nechaido.editor.jeditor.drawer.simpleDocument.SimpleDocumentDrawer;

import java.awt.*;

/**
 * Created by nechaido on 5/7/16.
 */
public class SimpleDocument extends ElementComposition implements Document {
    private Style style;

    public SimpleDocument(Style style) {
        super();
        this.style = style;
        addElement(new ElementComposition());
    }

    @Override
    public Drawer getDrawer(Graphics graphics) {
        return new SimpleDocumentDrawer(graphics, this, style);
    }

    public Style getStyle() {
        return style;
    }
}