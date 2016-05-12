package com.nechaido.editor.jeditor;

import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.commands.CommandHistory;
import com.nechaido.editor.jeditor.commands.simpleDocument.InsertElement;
import com.nechaido.editor.jeditor.commands.simpleDocument.RemoveElement;
import com.nechaido.editor.jeditor.commands.simpleDocument.SplitLine;
import com.nechaido.editor.jeditor.document.Document;
import com.nechaido.editor.jeditor.document.simpleDocument.Picture;
import com.nechaido.editor.jeditor.document.simpleDocument.SimpleDocument;
import com.nechaido.editor.jeditor.document.simpleDocument.Style;
import com.nechaido.editor.jeditor.drawer.Drawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by nechaido on 5/7/16.
 */
public class JEditor extends JComponent implements KeyListener {

    private CommandHistory commandHistory;
    private Document document;
    private Carriage carriage;

    private Context context;
    private Carriage selectionStart;
    private Carriage selectionEnd;

    public JEditor() {
        commandHistory = new CommandHistory();
        document = new SimpleDocument(new Style(new Font("Source Code Pro", Font.PLAIN, 15)));
        carriage = new Carriage();
        selectionStart = new Carriage();
        selectionEnd = new Carriage();
        context = new Context((SimpleDocument) document, carriage, selectionStart, selectionEnd);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        super.paintComponent(graphics);
        Drawer drawer = document.getDrawer(graphics);
        drawer.draw(document);
        drawer.drawCursor(carriage);
        setPreferredSize(drawer.getSize());
    }

    private void remove() {
        Command command = new RemoveElement(context);
        commandHistory.run(command);
    }

    private void insertSymbol(char c) {
        Command command = new InsertElement(context, context.getSymbolFactory().getSymbol(c));
        commandHistory.run(command);
    }

    private void insertImage() {
        Command command = new InsertElement(context, new Picture(context.getPictureFactory().getPicure("/home/nechaido/glacier.jpg"), new Dimension(100, 100)));
        commandHistory.run(command);
    }

    private void splitLine() {
        Command command = new SplitLine(context);
        commandHistory.run(command);
    }

//    private void moveCursorLeft() {
//        if (cursorElement > 0) {
//            --cursorElement;
//        } else if (cursorRow > 0) {
//            --cursorRow;
//            cursorElement = document.getElement(cursorRow).length();
//            System.out.println(cursorElement);
//        }
//    }
//
//    private void moveCursorRight() {
//        if (cursorElement < document.getElement(cursorRow).length()) {
//            ++cursorElement;
//        } else if (cursorRow < document.length() - 1) {
//            cursorElement = 0;
//            cursorRow++;
//        }
//
//    }
//
//    private void moveCursorDown() {
//        if (cursorRow < document.length() - 1) {
//            ++cursorRow;
//            int lastSymbol = document.getElement(cursorRow).length();
//            if (cursorElement > lastSymbol) {
//                cursorElement = lastSymbol;
//            }
//        }
//    }
//
//    private void moveCursorUp() {
//        if (cursorRow > 0) {
//            --cursorRow;
//        }
//        int lastSymbol = document.getElement(cursorRow).length();
//        if (cursorElement > lastSymbol) {
//            cursorElement = lastSymbol;
//        }
//    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        System.out.println(key);
        if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
//            moveCursorRight();
        } else if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
//            moveCursorLeft();
        } else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN) {
//            moveCursorDown();
        } else if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP) {
//            moveCursorUp();
        } else if (key == KeyEvent.VK_BACK_SPACE) {
            remove();
        } else if (key == KeyEvent.VK_Z && (keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            commandHistory.undo();
        } else if (key == KeyEvent.VK_Y && (keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            commandHistory.redo();
        } else if (key == KeyEvent.VK_Z && keyEvent.isShiftDown() && (keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            commandHistory.redo();
        } else if (key == KeyEvent.VK_I && (keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
            insertImage();
        } else if (key == KeyEvent.VK_ENTER) {
            splitLine();
        } else if (Character.isAlphabetic(key) || Character.isDigit(key) || Character.isSpaceChar(key)) {
            insertSymbol(keyEvent.getKeyChar());
        }
        revalidate();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
