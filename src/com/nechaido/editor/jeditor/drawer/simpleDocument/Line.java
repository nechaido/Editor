package com.nechaido.editor.jeditor.drawer.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.document.simpleDocument.ElementComposition;
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
//    private Style style;
    private int[] partialWidth;

    public Line(Element element) {
        size = new Dimension();
        list = new LinkedList<>();
        size.height = ((ElementComposition)element).getStyle().getFont().getSize();
        int i = 0;
        partialWidth = new int[element.length() + 1];
        int currentIndex = 0;
        partialWidth[currentIndex++] = 0;
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
                partialWidth[currentIndex++] = partialWidth[currentIndex - 2] + picWidth;
                ++i;
            } else if (currentElement instanceof Symbol) {
                Style curStyle = ((ElementComposition) element).getStyle(i);
                Word currentWord = new Word(curStyle);
                currentWord.getSize().height = curStyle.getFont().getSize();
                if (curStyle.getFont().getSize() > size.height){
                    size.height = curStyle.getFont().getSize();
                }
                while (currentElement instanceof Symbol && curStyle == ((ElementComposition) element).getStyle(i)) {
                    char ch = ((Symbol) currentElement).getCharacter();
                    currentWord.append(ch);
                    int chaWidth = curStyle.getCharWidth(ch);
                    size.width += chaWidth;
                    currentWord.getSize().width += chaWidth;
                    partialWidth[currentIndex++] = partialWidth[currentIndex - 2] + chaWidth;
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
    public void drawBy(Drawer drawer) {
        ((SimpleDocumentDrawer) drawer).draw(this);
    }

    public Collection<? extends VisualElement> getContents() {
        return list;
    }

    public int[] getPartialWidth() {
        return partialWidth;
    }

    public int amountOfElements() {
        return list.size();
    }
}
