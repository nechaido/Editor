package com.nechaido.editor.jeditor.drawer;

import com.nechaido.editor.jeditor.Carriage;
import com.nechaido.editor.jeditor.document.Document;

import java.awt.*;

/**
 * Created by nechaido on 5/7/16.
 */
public abstract class Drawer {

    protected Graphics2D graphics2D;

    protected Drawer(Graphics graphics) {
        graphics2D = (Graphics2D) graphics;
    }

    public abstract void draw(Document document);

    public abstract void draw(VisualElement element);

    public abstract void drawCursor(Carriage carriage);

    public abstract Dimension getSize();
}
