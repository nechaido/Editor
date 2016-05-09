package com.nechaido.editor.jeditor.document;

import com.nechaido.editor.jeditor.drawer.Drawer;

import java.util.Collection;

/**
 * Created by nechaido on 5/7/16.
 */
public interface Element {
    void drawBy(Drawer drawer);
    boolean isMajor();

    void addChildElement(Element element);
    void addChildElement(int index, Element element);

    void addAllChildElements(Collection<? extends Element> childElements);

    void addAllChildElements(Element element);
    void addAllChildElements(int index, Element element);

    void removeChildElement(int index);
    void removeChildElements(int from, int to);

    void removeChildElements(int from);

    Element getChildElement(int index);
    Collection<? extends Element> getChildElements();

    Collection<? extends Element> getChildElements(int from, int to);

    Element getSubElement(int from, int to);

    void setChildElement(int index, Element element);

    int amountOfChildElements();
}
