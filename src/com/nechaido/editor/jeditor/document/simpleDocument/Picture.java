package com.nechaido.editor.jeditor.document.simpleDocument;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by nechaido on 5/7/16.
 */
public class Picture extends PrimitiveElement {
    private BufferedImage image;
    private Dimension size;

    public Picture(BufferedImage image, Dimension size) {
        this.image = image;
        this.size = new Dimension(size);
    }

    @Override
    public boolean isMajor() {
        return true;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = new Dimension(size);
    }

    public BufferedImage getImage() {
        return image;
    }
}
