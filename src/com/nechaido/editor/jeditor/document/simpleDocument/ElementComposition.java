package com.nechaido.editor.jeditor.document.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by nechaido on 5/9/16.
 */
public class ElementComposition implements Element {
    LinkedList<Element> contents;


    public ElementComposition() {
        contents = new LinkedList<>();
    }

    public ElementComposition(Collection<Element> elements) {
        contents = new LinkedList<>(elements);
    }

    @Override
    public boolean isMajor() {
        return true;
    }

    @Override
    public void addElement(Element element) {
        contents.add(element);
    }

    @Override
    public void addElement(int index, Element element) {
        contents.add(index, element);
    }

    @Override
    public void addAllElements(Collection<? extends Element> childElements) {
        contents.addAll(childElements);
    }

    @Override
    public void addAllElements(Element element) {
        contents.addAll(element.getElements());
    }

    @Override
    public void addAllElements(int index, Element element) {
        contents.addAll(index, element.getElements());
    }

    @Override
    public void removeElement(int index) {
        contents.remove(index);
    }

    @Override
    public void removeElements(int from, int to) {
        contents.subList(from, to).clear();
    }

    @Override
    public void removeElements(int from) {
        contents.subList(from, contents.size()).clear();
    }

    @Override
    public Element getElement(int index) {
        return contents.get(index);
    }

    @Override
    public Collection<? extends Element> getElements() {
        return contents;
    }

    @Override
    public Collection<? extends Element> getElements(int from, int to) {
        return contents.subList(from, to + 1);
    }

    @Override
    public Element getSubElement(int from, int to) {
        return new ElementComposition(contents.subList(from, to + 1));
    }

    @Override
    public void setElement(int index, Element element) {
        contents.set(index, element);
    }

    @Override
    public int length() {
        return contents.size();
    }
}
