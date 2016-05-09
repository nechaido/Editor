package com.nechaido.editor;

import com.nechaido.editor.jeditor.JEditor;

import javax.swing.*;

/**
 * Created by nechaido on 5/7/16.
 */
public class Editor {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }

    private static void createGUI(){
        JFrame jFrame = new JFrame("Text Editor");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JScrollPane jScrollPane = new JScrollPane(new JEditor());
        jFrame.add(jScrollPane);
        jFrame.pack();
        jFrame.setSize(300, 300);
        jFrame.setVisible(true);
        jScrollPane.getVerticalScrollBar().setValue(50);
    }
}
