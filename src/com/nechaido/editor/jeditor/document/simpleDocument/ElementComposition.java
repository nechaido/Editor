package com.nechaido.editor.jeditor.document.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.drawer.Drawer;
import com.nechaido.editor.jeditor.drawer.simpleDocument.SimpleDocumentDrawer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nechaido on 5/9/16.
 */
//TODO Nigga
public class ElementComposition implements Element{
    ArrayList<Element> contents;

    @Override
    public void drawBy(Drawer drawer) {
        ((SimpleDocumentDrawer)drawer).draw(this);
    }

    @Override
    public boolean isMajor() {
        return true;
    }

    @Override
    public void addChildElement(Element element) {

    }

    @Override
    public void addChildElement(int index, Element element) {

    }

    @Override
    public void addAllChildElements(Collection<? extends Element> childElements) {

    }

    @Override
    public void addAllChildElements(Element element) {

    }

    @Override
    public void addAllChildElements(int index, Element element) {

    }

    @Override
    public void removeChildElement(int index) {

    }

    @Override
    public void removeChildElements(int from, int to) {

    }

    @Override
    public void removeChildElements(int from) {

    }

    @Override
    public Element getChildElement(int index) {
        return null;
    }

    @Override
    public Collection<? extends Element> getChildElements() {
        return null;
    }

    @Override
    public Collection<? extends Element> getChildElements(int from, int to) {
        return null;
    }

    @Override
    public Element getSubElement(int from, int to) {
        return null;
    }

    @Override
    public void setChildElement(int index, Element element) {

    }

    @Override
    public int amountOfChildElements() {
        return 0;
    }
}
