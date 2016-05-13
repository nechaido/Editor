package com.nechaido.editor.jeditor.document.simpleDocument;

import com.nechaido.editor.jeditor.document.Document;
import com.nechaido.editor.jeditor.drawer.Drawer;
import com.nechaido.editor.jeditor.drawer.simpleDocument.SimpleDocumentDrawer;

import java.awt.*;

/**
 * Created by nechaido on 5/7/16.
 */
public class SimpleDocument extends ElementComposition implements Document {

    public SimpleDocument(Style style) {
        super(style);
    }

    @Override
    public Drawer getDrawer(Graphics graphics) {
        return new SimpleDocumentDrawer(graphics, this);
    }
}