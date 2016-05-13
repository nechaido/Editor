package com.nechaido.editor.jeditor.elementFactories;

import com.nechaido.editor.jeditor.document.simpleDocument.Style;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by Nechai Dmytro nechaido@gmail.com  on 5/14/16.
 */
public class StyleFactory {
    private static StyleFactory instance;
    private HashMap<Font, Style> styles;

    private StyleFactory() {
        styles = new HashMap<>();
    }

    public static StyleFactory getInstance() {
        if (instance == null) {
            instance = new StyleFactory();
        }
        return instance;
    }

    public Style getStyle(Font font) {
        if (!styles.containsKey(font)) {
            styles.put(font, new Style(font));
        }
        return styles.get(font);
    }

    public void add(Style style) {
        styles.put(style.getFont(), style);
    }
}
