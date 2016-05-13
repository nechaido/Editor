package com.nechaido.editor.jeditor.document.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by nechaido on 5/9/16.
 */
public class ElementComposition implements Element {

    private LinkedList<Element> contents;
    private LinkedList<Style> styles;

    private Style style;

    public ElementComposition(Style style) {
        contents = new LinkedList<>();
        styles = new LinkedList<>();
        this.style = style;
    }

    public ElementComposition(Collection<Element> elements, Style style) {
        contents = new LinkedList<>(elements);
        styles = new LinkedList<>();
        this.style = style;
        for (int i = 0; i < elements.size(); i++) {
            styles.add(style);
        }
    }

    @Override
    public boolean isMajor() {
        return true;
    }

    @Override
    public void addElement(Element element) {
        contents.add(element);
        if (styles.size() > 0){
            styles.add(styles.get(styles.size() - 1));
        } else {
            styles.add(style);
        }
    }

    @Override
    public void addElement(int index, Element element) {
        contents.add(index, element);
        if (index > 0){
            styles.add(index, styles.get(index - 1));
        } else {
            styles.add(style);
        }
    }

    @Override
    public void addAllElements(Collection<? extends Element> childElements) {
        contents.addAll(childElements);
        for (int i = 0; i < childElements.size(); i++) {
            if (styles.size() > 0){
                styles.add(styles.get(styles.size() - 1));
            } else {
                styles.add(style);
            }
        }
    }

    @Override
    public void addAllElements(Element element) {
        Collection< ? extends Element> elements =  element.getElements();
        contents.addAll(elements);
        for (int i = 0; i < elements.size(); i++) {
            if (styles.size() > 0){
                styles.add(styles.get(styles.size() - 1));
            } else {
                styles.add(style);
            }
        }
    }

    @Override
    public void addAllElements(int index, Element element) {
        Collection< ? extends Element> elements =  element.getElements();
        contents.addAll(index, elements);
        for (int i = 0; i < elements.size(); i++) {
            if (index > 0){
                styles.add(index, styles.get(index - 1));
            } else {
                styles.add(style);
            }
        }
    }

    @Override
    public void removeElement(int index) {
        contents.remove(index);
        styles.remove(index);
    }

    @Override
    public void removeElements(int from, int to) {
        contents.subList(from, to).clear();
        styles.subList(from, to).clear();
    }

    @Override
    public void removeElements(int from) {
        contents.subList(from, contents.size()).clear();
        styles.subList(from, styles.size()).clear();
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
        return new ElementComposition(contents.subList(from, to + 1), style);
    }

    @Override
    public void setElement(int index, Element element) {
        contents.set(index, element);
    }

    @Override
    public int length() {
        return contents.size();
    }

    @Override
    public void empty() {
        contents.clear();
    }

    public Style getStyle() {
        return style;
    }

    public Style getStyle(int i) {
        if (styles.size() == 0){
            return style;
        }
        return styles.get(i);
    }

    public void setStyle(int i, Style style) {
        if (styles.size() == 0){
            this.style = style;
        }
        styles.set(i, style);
    }

    public void setStyle(Style style) {
        style = style;
    }
}
