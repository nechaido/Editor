package com.nechaido.editor.jeditor.document;

import java.util.Collection;

/**
 * Created by nechaido on 5/7/16.
 */
public interface Element {
    boolean isMajor();

    void addElement(Element element);

    void addElement(int index, Element element);

    void addAllElements(Collection<? extends Element> childElements);

    void addAllElements(Element element);

    void addAllElements(int index, Element element);

    void removeElement(int index);

    void removeElements(int from, int to);

    void removeElements(int from);

    Element getElement(int index);

    Collection<? extends Element> getElements();

    Collection<? extends Element> getElements(int from, int to);

    Element getSubElement(int from, int to);

    void setElement(int index, Element element);

    int length();
}
