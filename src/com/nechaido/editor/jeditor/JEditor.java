package com.nechaido.editor.jeditor;

import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.commands.CommandHistory;
import com.nechaido.editor.jeditor.commands.simpleDocument.*;
import com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators.*;
import com.nechaido.editor.jeditor.commands.simpleDocument.carriageMover.*;
import com.nechaido.editor.jeditor.document.Document;
import com.nechaido.editor.jeditor.document.simpleDocument.*;
import com.nechaido.editor.jeditor.drawer.Drawer;

import javax.swing.*;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by nechaido on 5/7/16.
 */
public class JEditor extends JComponent implements KeyListener {

    private CommandHistory commandHistory;
    private Document document;
    private Carriage carriage;

    private Clipboard clipboard;

    private Context context;
    private Carriage selectionStart;
    private Carriage selectionEnd;

    private File currentFile;
    private Style currentStyle;

    private boolean selectionMode;

    private CommandGenerator commandGenerator;



    public JEditor() {
        commandHistory = new CommandHistory();
        commandGenerator = new CommandGenerator();
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        currentStyle = new Style(new Font("Source Sans Pro", Font.PLAIN, 15));
        setEnabled(false);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (isEnabled()){
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
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown()){
            if (keyEvent.getKeyCode() == KeyEvent.VK_Z && keyEvent.isShiftDown()){
                if (!isEnabled()){
                    return;
                }
                redo();
            } else if  (keyEvent.getKeyCode() == KeyEvent.VK_Z) {
                if (!isEnabled()){
                    return;
                }
                undo();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_S && keyEvent.isShiftDown()){
                if (!isEnabled()){
                    return;
                }
                saveAs();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
                if (!isEnabled()){
                    return;
                }
                save();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_N) {
                newFile();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_O) {
                open();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_C) {
                if (!isEnabled()){
                    return;
                }
                copy();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_X) {
                if (!isEnabled()){
                    return;
                }
                cut();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_V) {
                if (!isEnabled()){
                    return;
                }
                paste();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_I && keyEvent.isShiftDown()){
                if (!isEnabled()){
                    return;
                }
                insertImage();
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_R){
                if (!isEnabled()){
                    return;
                }
                resizeImage();
            }
        } else {
            if (!isEnabled()){
                return;
            }
            commandHistory.run(commandGenerator.getCommand(keyEvent));
            revalidate();
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    public void newFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            document = new SimpleDocument(currentStyle);
            document.addElement(new ElementComposition(currentStyle));
            commandHistory.empty();
            carriage = new Carriage();
            selectionStart = new Carriage();
            selectionEnd = new Carriage();
            context = new Context((SimpleDocument) document, carriage, selectionStart, selectionEnd, currentStyle);
            currentStyle = context.getCurrentStyle();
            setEnabled(true);
        }
    }

    public void open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            document = new SimpleDocument(currentStyle);
            currentFile = fileChooser.getSelectedFile();
            commandHistory.empty();
            carriage = new Carriage();
            selectionStart = new Carriage();
            selectionEnd = new Carriage();
            context = new Context((SimpleDocument) document, carriage, selectionStart, selectionEnd, currentStyle);
            currentStyle = context.getCurrentStyle();
            new DocumentSerialiser(context).readDocument(currentFile);
            setEnabled(true);
        }
    }

    public void save() {
        if (!isEnabled()){
            return;
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(currentFile));
            bw.write(new DocumentSerialiser(context).serializeDocument());
            bw.close();
            commandHistory.empty();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAs() {
        if (!isEnabled()){
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            save();
        }
    }

    public void undo() {
        if (!isEnabled()){
            return;
        }
        commandHistory.undo();
        revalidate();
        repaint();
    }

    public void redo() {
        if (!isEnabled()){
            return;
        }
        commandHistory.redo();
        revalidate();
        repaint();
    }

    public void copy() {
        if (!isEnabled()){
            return;
        }
        if (selectionMode){
            StringSelection selection = new StringSelection(new DocumentSerialiser(context).serializeSelection());
            clipboard.setContents(selection, selection);
        }
    }

    public void cut() {
        if (!isEnabled()){
            return;
        }
        if (selectionMode){
            StringSelection selection = new StringSelection(new DocumentSerialiser(context).serializeSelection());
            clipboard.setContents(selection, selection);
            commandHistory.run(new RemoveSection(context));
            revalidate();
            repaint();
        }
    }

    public void paste() {
        if (!isEnabled()){
            return;
        }
        try {
            String clip = clipboard.getData(DataFlavor.stringFlavor).toString();
            if (selectionMode){
                commandHistory.run(new InsertSectionInstead(context, new DocumentSerialiser(context).convert(clip)));
            } else {
                commandHistory.run(new InsertSection(context, new DocumentSerialiser(context).convert(clip)));
            }
            revalidate();
            repaint();
        } catch (UnsupportedFlavorException | IOException e) {
        }
    }

    public void insertImage() {
        if (!isEnabled()){
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            Dimension size = new Dimension();
            if (askSize(size)){
                if (selectionMode) {
                    commandHistory.run(new InsertInstead(context,
                            new Picture(context.getPictureFactory().getPicure(fileChooser.getSelectedFile().getAbsolutePath()), size)));
                } else {
                    commandHistory.run(new InsertElement(context,
                            new Picture(context.getPictureFactory().getPicure(fileChooser.getSelectedFile().getAbsolutePath()), size)));
                }
                revalidate();
                repaint();
            }
        }
    }


    public void resizeImage() {
        if (!isEnabled()){
            return;
        }
        if (!selectionMode && document.getElement(carriage.row).getElement(carriage.element) instanceof Picture){
            Dimension size = new Dimension();
            if (askSize(size)){
                commandHistory.run(new ResizeImage(context, size));
                revalidate();
                repaint();
            }
        }
    }

    public void updateStyle(Font style) {
        if (!isEnabled()){
            return;
        }
        if (selectionMode){
            commandHistory.run(new ChangeSelectionStyle(context, context.getStyleFactory().getStyle(style)));
        } else {
            commandHistory.run(new ChangeCurrentStyle(context, context.getStyleFactory().getStyle(style)));
        }
        revalidate();
        repaint();
    }

    private boolean askSize(Dimension size) {
        JSpinner heightInput = new JSpinner(new SpinnerNumberModel());
        JSpinner widthInput = new JSpinner(new SpinnerNumberModel());

        heightInput.setValue(100);
        widthInput.setValue(100);

        Object[] message = {
                "Enter height:", heightInput,
                "Enter width:", widthInput
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Enter Size", JOptionPane.OK_CANCEL_OPTION);

        size.height = (int) heightInput.getValue();
        size.width = (int) widthInput.getValue();

        return option == JOptionPane.OK_OPTION;
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
            } else if (key == KeyEvent.VK_ENTER) {
                result = new SplitLine(context);
            } else if (Character.isAlphabetic(key) || Character.isDigit(key) || Character.isSpaceChar(key)) {
                if (selectionMode){
                    result = new InsertInstead(context, context.getSymbolFactory().getSymbol(keyEvent.getKeyChar()));
                } else {
                    result = new InsertElement(context, context.getSymbolFactory().getSymbol(keyEvent.getKeyChar()));
                }
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
