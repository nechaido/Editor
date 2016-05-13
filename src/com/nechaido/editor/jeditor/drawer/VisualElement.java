package com.nechaido.editor.jeditor.drawer;

import com.nechaido.editor.jeditor.document.simpleDocument.Style;

import java.awt.*;

/**
 * Created by nechaido on 5/9/16.
 */
public interface VisualElement {
    Dimension getSize();

    void drawBy(Drawer drawer);
}
