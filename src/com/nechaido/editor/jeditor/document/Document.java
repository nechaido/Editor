package com.nechaido.editor.jeditor.document;

import com.nechaido.editor.jeditor.drawer.Drawer;

import java.awt.*;

/**
 * Created by nechaido on 5/7/16.
 */
public interface Document extends Element {
    Drawer getDrawer(Graphics graphics);
}
