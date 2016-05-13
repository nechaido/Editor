package com.nechaido.editor.jeditor.drawer.simpleDocument;

import com.nechaido.editor.jeditor.document.simpleDocument.SimpleDocument;
import com.nechaido.editor.jeditor.document.simpleDocument.Style;
import com.nechaido.editor.jeditor.drawer.Drawer;
import com.nechaido.editor.jeditor.drawer.VisualElement;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by nechaido on 5/9/16.
 */
public class VisualDocument implements VisualElement {

    private Dimension size;
    private LinkedList<Line> lines;

    public VisualDocument(SimpleDocument simpleDocument) {
        lines = new LinkedList<>();
        size = new Dimension(0, 0);
        for (int i = 0; i < simpleDocument.length(); i++) {
            Line newLine = new Line(simpleDocument.getElement(i));
            lines.add(newLine);
            if (size.width < newLine.getSize().width) {
                size.width = newLine.getSize().width;
            }
            size.height += newLine.getSize().height;
        }
    }

    public Collection<? extends VisualElement> getLines() {
        return lines;
    }

    @Override
    public Dimension getSize() {
        return size;
    }


    @Override
    public void drawBy(Drawer drawer) {
        ((SimpleDocumentDrawer) drawer).draw(this);
    }
}
