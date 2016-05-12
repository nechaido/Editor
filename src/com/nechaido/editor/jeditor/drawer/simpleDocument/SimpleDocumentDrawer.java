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
        int row = carriage.rowIndex;
        int element = carriage.elementIndex;
        ArrayList<? extends VisualElement> lines = new ArrayList<>(visualDocument.getLines());
        int y = padding;
        int x = padding;
        int width = style.getCharWidth(' ');
        int hight = style.getFont().getSize();
        for (int i = 0; i < row; i++) {
            y += lines.get(i).getSize().height;
        }
        if (((Line) lines.get(row)).amountOfElements() > 0) {
            int[] partialWidth = ((Line) lines.get(row)).getPartialWidthes();
            int elementCount = partialWidth.length - 1;
            x += partialWidth[element];
            if (element != elementCount) {
                width = partialWidth[element + 1] - partialWidth[element];
                hight = ((Line) lines.get(row)).getSize().height;
            }
        }
        graphics2D.fillRect(x, y, width, hight);
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
