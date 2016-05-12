package com.nechaido.editor.jeditor.drawer.simpleDocument;

import com.nechaido.editor.jeditor.Carriage;
import com.nechaido.editor.jeditor.document.Document;
import com.nechaido.editor.jeditor.document.simpleDocument.SimpleDocument;
import com.nechaido.editor.jeditor.document.simpleDocument.Style;
import com.nechaido.editor.jeditor.drawer.Drawer;
import com.nechaido.editor.jeditor.drawer.VisualElement;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by nechaido on 5/7/16.
 */
public class SimpleDocumentDrawer extends Drawer {
    VisualDocument visualDocument;
    private Style style;
    private SimpleDocument document;
    private Dimension currentOffset;
    private int padding;

    public SimpleDocumentDrawer(Graphics graphics, SimpleDocument document, Style style) {
        super(graphics);
        this.document = document;
        this.style = style;
        padding = style.getFont().getSize();
        currentOffset = new Dimension(0, 0);
    }

    public void draw(SimpleDocument simpleDocument) {
        visualDocument = new VisualDocument(simpleDocument);
        visualDocument.drawBy(this);
    }

    @Override
    public void draw(Document document) {
        if (document instanceof SimpleDocument) {
            draw((SimpleDocument) document);
        }
    }

    @Override
    public void draw(VisualElement element) {
    }

    @Override
    public void drawCursor(Carriage carriage) {
        int row = carriage.row;
        int element = carriage.element;
        ArrayList<? extends VisualElement> lines = new ArrayList<>(visualDocument.getLines());
        int y = padding;
        int x = padding;
        int width = style.getCharWidth(' ');
        int height = style.getFont().getSize();
        for (int i = 0; i < row; i++) {
            y += lines.get(i).getSize().height;
        }
        if (((Line) lines.get(row)).amountOfElements() > 0) {
            int[] partialWidth = ((Line) lines.get(row)).getPartialWidthes();
            int elementCount = partialWidth.length - 1;
            x += partialWidth[element];
            if (element != elementCount) {
                width = partialWidth[element + 1] - partialWidth[element];
                height = ((Line) lines.get(row)).getSize().height;
            }
        }
        Color oldColor = graphics2D.getColor();
        Color newColor = new Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getGreen(), 128);
        graphics2D.setColor(newColor);
        graphics2D.fillRect(x, y, width, height + 3);
        graphics2D.setColor(oldColor);
    }

    @Override
    public void drawSelection(Carriage selectionStartExternal, Carriage selectionEndExternal) {
        ArrayList<VisualElement> lines = new ArrayList<>(visualDocument.getLines());
        int y = padding;
        int x = padding;
        Carriage selectionStart = new Carriage();
        Carriage selectionEnd = new Carriage();
        if (selectionEndExternal.row > selectionStartExternal.row){
            selectionEnd = selectionEndExternal;
            selectionStart = selectionStartExternal;
        } else if (selectionEndExternal.row < selectionStartExternal.row){
            selectionEnd = selectionStartExternal;
            selectionStart = selectionEndExternal;
        } else if (selectionEndExternal.element > selectionStartExternal.element){
            selectionEnd = selectionEndExternal;
            selectionStart = selectionStartExternal;
        } else  {
            selectionEnd = selectionStartExternal;
            selectionStart = selectionEndExternal;
        }
        for (int i = 0; i < selectionStart.row; i++) {
            y += lines.get(i).getSize().height;
        }
        for (int i = selectionStart.row; i <= selectionEnd.row; i++) {
            x = padding;
            int start = ((i == selectionStart.row) ? selectionStart.element : 0);
            int[] partialWidth = ((Line) lines.get(i)).getPartialWidthes();
            int end = ((i == selectionEnd.row) ? selectionEnd.element : partialWidth.length);
            int width = 0;
            if (end == partialWidth.length){
                width += style.getCharWidth(' ');
                if (end != 0){
                    end--;
                }
            }
            width += partialWidth[end] - partialWidth[start];
            x += partialWidth[start];
            int height = lines.get(i).getSize().height;

            Color oldColor = graphics2D.getColor();
            Color newColor = new Color(0, 0, 255, 128);
            graphics2D.setColor(newColor);
            graphics2D.fillRect(x, y, width, height);
            graphics2D.setColor(oldColor);
            y += height;
        }
    }

    @Override
    public Dimension getSize() {
        return new Dimension(visualDocument.getSize().width + 2 * padding, visualDocument.getSize().height + 2 * padding);
    }

    public void draw(VisualDocument visualDocument) {
        graphics2D.setFont(style.getFont());
        currentOffset.height = padding;
        currentOffset.width = padding;
        for (VisualElement element : visualDocument.getLines()) {
            element.drawBy(this);
        }
    }

    public void draw(Line line) {
        currentOffset.height += line.getSize().height;
        for (VisualElement element : line.getContents()) {
            element.drawBy(this);
        }
        currentOffset.width = padding;
    }

    public void draw(VisualPicture visualPicture) {
        graphics2D.drawImage(visualPicture.getImage(),
                currentOffset.width, currentOffset.height - visualPicture.getSize().height,
                visualPicture.getSize().width, visualPicture.getSize().height, null);
        currentOffset.width += visualPicture.getSize().width;
    }

    public void draw(Word word) {
        graphics2D.setFont(word.getStyle().getFont());
        graphics2D.drawString(word.toString(), currentOffset.width, currentOffset.height);
        currentOffset.width += word.getSize().width;
    }
}
