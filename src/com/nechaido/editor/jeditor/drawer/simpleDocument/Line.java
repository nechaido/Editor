package com.nechaido.editor.jeditor.drawer.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.document.simpleDocument.Picture;
import com.nechaido.editor.jeditor.document.simpleDocument.Style;
import com.nechaido.editor.jeditor.document.simpleDocument.Symbol;
import com.nechaido.editor.jeditor.drawer.Drawer;
import com.nechaido.editor.jeditor.drawer.VisualElement;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by nechaido on 5/9/16.
 */
public class Line implements VisualElement {

    private Dimension size;
    private LinkedList<VisualElement> list;
    private Style style;
    private int[] partialWidthes;

    public Line(Element element, Style style) {
        size = new Dimension();
        list = new LinkedList<>();
        this.style = style;
        size.height = style.getFont().getSize();
        int i = 0;
        partialWidthes = new int[element.length() + 1];
        int currentIndex = 0;
        partialWidthes[currentIndex++] = 0;
        while (i < element.length()) {
            Element currentElement = element.getElement(i);
            if (currentElement instanceof Picture) {
                Picture picture = (Picture) currentElement;
                list.add(new VisualPicture(picture));
                if (picture.getSize().getHeight() > size.getHeight()) {
                    size.height = (int) picture.getSize().getHeight();
                }
                int picWidth = picture.getSize().width;
                size.width += picWidth;
                partialWidthes[currentIndex++] = partialWidthes[currentIndex - 2] + picWidth;
                ++i;
            } else if (currentElement instanceof Symbol) {
                Word currentWord = new Word(style);
                currentWord.getSize().height = style.getFont().getSize();
                while (currentElement instanceof Symbol) {
                    char ch = ((Symbol) currentElement).getCharacter();
                    currentWord.append(ch);
                    int chaWidth = style.getCharWidth(ch);
                    size.width += chaWidth;
                    currentWord.getSize().width += chaWidth;
                    partialWidthes[currentIndex++] = partialWidthes[currentIndex - 2] + chaWidth;
                    if (i == element.length() - 1) {
                        ++i;
                        break;
                    }
                    currentElement = element.getElement(++i);
                }
                list.add(currentWord);
            } else {
                ++i;
            }
        }
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public void drawBy(Drawer drawer) {
        ((SimpleDocumentDrawer) drawer).draw(this);
    }

    public Collection<? extends VisualElement> getContents() {
        return list;
    }

    public int[] getPartialWidthes() {
        return partialWidthes;
    }

    public int amountOfElements() {
        return list.size();
    }
}
