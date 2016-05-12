package com.nechaido.editor.jeditor;

import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.commands.CommandHistory;
import com.nechaido.editor.jeditor.commands.simpleDocument.RemoveSection;
import com.nechaido.editor.jeditor.commands.simpleDocument.carriageMover.*;
import com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators.InsertElement;
import com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators.RemoveElement;
import com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators.RemoveLineBreak;
import com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators.SplitLine;
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

    private boolean selectionMode;

    private CommandGenerator commandGenerator;



    public JEditor() {
        commandHistory = new CommandHistory();
        document = new SimpleDocument(new Style(new Font("Source Code Pro", Font.PLAIN, 15)));
        carriage = new Carriage();
        selectionStart = new Carriage();
        selectionEnd = new Carriage();
        context = new Context((SimpleDocument) document, carriage, selectionStart, selectionEnd);
        commandGenerator = new CommandGenerator();
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
        if (selectionMode){
            drawer.drawSelection(selectionStart, selectionEnd);
        }
        setPreferredSize(drawer.getSize());
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Command command = commandGenerator.getCommand(keyEvent);
        if (command != null){
            commandHistory.run(command);
            revalidate();
            repaint();
        }
        System.out.println(selectionStart.row + " " + selectionStart.element);
        System.out.println(selectionEnd.row + " " + selectionEnd.element);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    private class CommandGenerator {
        private Command getCommand(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            Command result = null;
            if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
                if (keyEvent.isShiftDown()){
                    result = new MoveSelectionRight(context);
                } else {
                    result = new MoveCarriageRight(context);
                }
            } else if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
                if (keyEvent.isShiftDown()){
                    result = new MoveSelectionLeft(context);
                } else {
                    result = new MoveCarriageLeft(context);
                }
            } else if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN) {
                if (keyEvent.isShiftDown()){
                    result = new MoveSelectionDown(context);
                } else {
                    result = new MoveCarriageDown(context);
                }
            } else if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP) {
                if (keyEvent.isShiftDown()){
                    result = new MoveSelectionUp(context);
                } else {
                    result = new MoveCarriageUp(context);
                }
            } else if (key == KeyEvent.VK_BACK_SPACE) {
                if (selectionMode){
                    result = new RemoveSection(context);
                } else if (carriage.element == 0){
                    result = new RemoveLineBreak(context);
                } else {
                    result = new RemoveElement(context);
                }
            } else if (key == KeyEvent.VK_Z && (keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                commandHistory.undo();
            } else if (key == KeyEvent.VK_Y && (keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                commandHistory.redo();
            } else if (key == KeyEvent.VK_Z && keyEvent.isShiftDown() && (keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                commandHistory.redo();
            } else if (key == KeyEvent.VK_I && (keyEvent.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                result = new InsertElement(context, new Picture(context.getPictureFactory().getPicure("/home/nechaido/glacier.jpg"), new Dimension(100, 100)));
            } else if (key == KeyEvent.VK_ENTER) {
                result = new SplitLine(context);
            } else if (Character.isAlphabetic(key) || Character.isDigit(key) || Character.isSpaceChar(key)) {
                result = new InsertElement(context, context.getSymbolFactory().getSymbol(keyEvent.getKeyChar()));
            }
            if (!selectionMode){
                selectionStart.row = carriage.row;
                selectionStart.element = carriage.element;
                selectionEnd.row = carriage.row;
                selectionEnd.element = carriage.element;
            }
            selectionMode = keyEvent.isShiftDown();
            return result;
        }
    }
}
