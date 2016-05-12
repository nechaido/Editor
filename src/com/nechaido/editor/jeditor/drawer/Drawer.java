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
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
        graphics.setColor(new Color(34, 34, 34));
    }

    public abstract void draw(Document document);

    public abstract void draw(VisualElement element);

    public abstract void drawCursor(Carriage carriage);

    public abstract void drawSelection(Carriage selectionStart, Carriage selectionEnd);

    public abstract Dimension getSize();
}
