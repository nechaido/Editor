package com.nechaido.editor.jeditor.document.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;

import java.util.Collection;

/**
 * Created by nechaido on 5/9/16.
 */
public abstract class PrimitiveElement implements Element {

    @Override
    public void addElement(Element element) {
    }

    @Override
    public void addElement(int index, Element element) {
    }

    @Override
    public void addAllElements(Collection<? extends Element> childElements) {
    }

    @Override
    public void addAllElements(Element element) {
    }

    @Override
    public void addAllElements(int index, Element element) {
    }

    @Override
    public void removeElement(int index) {
    }

    @Override
    public void removeElements(int from, int to) {
    }

    @Override
    public void removeElements(int from) {
    }

    @Override
    public Element getElement(int index) {
        return null;
    }

    @Override
    public Collection<? extends Element> getElements() {
        return null;
    }

    @Override
    public Collection<? extends Element> getElements(int from, int to) {
        return null;
    }

    @Override
    public Element getSubElement(int from, int to) {
        return null;
    }

    @Override
    public void setElement(int index, Element element) {

    }

    @Override
    public int length() {
        return 0;
    }
}
