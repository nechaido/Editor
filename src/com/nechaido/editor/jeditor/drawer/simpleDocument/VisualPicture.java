package com.nechaido.editor.jeditor.drawer.simpleDocument;

import com.nechaido.editor.jeditor.document.simpleDocument.Picture;
import com.nechaido.editor.jeditor.document.simpleDocument.Style;
import com.nechaido.editor.jeditor.drawer.Drawer;
import com.nechaido.editor.jeditor.drawer.VisualElement;

import java.awt.*;

/**
 * Created by nechaido on 5/9/16.
 */
public class VisualPicture implements VisualElement {
    private Picture picture;

    public VisualPicture(Picture picture) {
        this.picture = picture;
    }

    public Image getImage() {
        return picture.getImage();
    }

    @Override
    public Dimension getSize() {
        return picture.getSize();
    }

    @Override
    public void drawBy(Drawer drawer) {
        ((SimpleDocumentDrawer) drawer).draw(this);
    }
}
