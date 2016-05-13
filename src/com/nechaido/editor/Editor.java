package com.nechaido.editor;

import com.nechaido.editor.jeditor.JEditor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.net.URL;

/**
 * Created by nechaido on 5/7/16.
 */
public class Editor extends JFrame{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Editor();
            }
        });
    }

    private Editor(){
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        createGUI();
        setVisible(true);
    }

    private  void createGUI() {
        setTitle("Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editor = new JEditor();
        editor.setFocusable(true);
        editorScrollPane = new JScrollPane(editor);
        getContentPane().add(editorScrollPane, BorderLayout.CENTER);
        createMenuBar();
        createToolbar();
        getContentPane().add(toolBar, BorderLayout.PAGE_END);
        pack();
        setSize(900, 400);
        setMinimumSize(new Dimension(900, 400));
        editor.addKeyListener(editor);
        createActions();
    }

    private JEditor editor;
    private JScrollPane editorScrollPane;

    private JMenuBar menuBar;

    private JMenu file;
    private JMenuItem newFile;
    private JMenuItem openFile;
    private JMenuItem saveFile;
    private JMenuItem saveFileAs;
    private JMenuItem exit;

    private JMenu help;
    private JMenuItem about;

    private JToolBar toolBar;

    private JButton newFileButton;
    private JButton openFileButton;
    private JButton saveFileButton;
    private JButton saveFileAsButton;

    private JButton undoButton;
    private JButton redoButton;

    private JButton copyButton;
    private JButton cutButton;
    private JButton pasteButton;

    private JButton insertImageButton;
    private JButton resizeImageButton;

    private JComboBox fontBox;
    private JSpinner fontSize;
    private JToggleButton boldText;
    private JToggleButton italicText;

    private void createMenuBar(){
        menuBar = new JMenuBar();
        file = new JMenu("File");

        newFile = new JMenuItem("New File");
        file.add(newFile);
        openFile = new JMenuItem("Open File");
        file.add(openFile);
        saveFile = new JMenuItem("Save File");
        file.add(saveFile);
        saveFileAs = new JMenuItem("Save File As");
        file.add(saveFileAs);
        file.add(new JSeparator());
        exit = new JMenuItem("Exit");
        file.add(exit);

        help = new JMenu("Help");
        about = new JMenuItem("About");
        help.add(about);

        menuBar.add(file);
        menuBar.add(help);
        setJMenuBar(menuBar);
    }

    private void createToolbar() {
        toolBar = new JToolBar();
        newFileButton = makeButton("document-new", "New File");
        openFileButton = makeButton("document-open", "Open File");
        saveFileButton = makeButton("document-save", "Save File");
        saveFileAsButton = makeButton("document-save-as", "Save File As");
        toolBar.add(newFileButton);
        toolBar.add(openFileButton);
        toolBar.add(saveFileButton);
        toolBar.add(saveFileAsButton);
        toolBar.addSeparator();

        undoButton = makeButton("edit-undo", "Undo");
        redoButton = makeButton("edit-redo", "Redo");
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.addSeparator();

        copyButton = makeButton("edit-copy", "Copy");
        cutButton = makeButton("edit-cut", "Cut");
        pasteButton = makeButton("edit-paste", "Paste");

        toolBar.add(copyButton);
        toolBar.add(cutButton);
        toolBar.add(pasteButton);
        toolBar.addSeparator();

        insertImageButton = makeButton("image-insert", "Insert Image");
        resizeImageButton = makeButton("image-resize", "Resize Image");
        toolBar.add(insertImageButton);
        toolBar.add(resizeImageButton);
        toolBar.addSeparator();


        fontBox = new JComboBox();
        fillFonts();
        fontSize = new JSpinner(new SpinnerNumberModel());
        Dimension d = fontBox.getSize();
        d.width = 150;
        fontBox.setSize(d);
        fontSize.setMinimumSize(d);
        fontSize.setMaximumSize(d);
        d = fontSize.getSize();
        d.width = 50;
        fontSize.setSize(d);
        fontSize.setMinimumSize(d);
        fontSize.setValue(15);

        boldText = makeToggleButton("format-text-bold", "Bold");
        italicText = makeToggleButton("format-text-italic", "Italic");
        toolBar.add(fontBox);
        toolBar.add(fontSize);
        toolBar.add(boldText);
        toolBar.add(italicText);
    }

    private JButton makeButton(String imageName, String altText) {
        JButton button = new JButton();
        String imgLocation = "resources/icons/" + imageName + ".png";
        URL imageURL = Editor.class.getResource(imgLocation);
        button.setToolTipText(altText);

        if (imageURL != null) {
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {
            button.setText(altText);
            System.out.println("Resource not found: " + imgLocation);
        }
        button.setFocusable(false);
        return button;
    }

    private JToggleButton makeToggleButton(String imageName, String altText) {
        JToggleButton button = new JToggleButton();
        String imgLocation = "resources/icons/" + imageName + ".png";
        URL imageURL = Editor.class.getResource(imgLocation);
        button.setToolTipText(altText);

        if (imageURL != null) {
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {
            button.setText(altText);
            System.out.println("Resource not found: " + imgLocation);
        }
        button.setFocusable(false);
        return button;
    }

    private void  fillFonts(){
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = e.getAllFonts();
        for (Font f : fonts) {
           fontBox.addItem(f.getFontName());
        }
        fontBox.setSelectedItem("Source Sans Pro");
    }

    private void createActions(){
        about.addActionListener(actionEvent -> JOptionPane.showMessageDialog(null, "Developed by Dmytro Nechai\nnechaido@gmail.com", "About", JOptionPane.INFORMATION_MESSAGE));
        newFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        openFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        saveFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveFileAs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        newFile.addActionListener(actionEvent -> editor.newFile());
        newFileButton.addActionListener(actionEvent -> editor.newFile());
        openFile.addActionListener(actionEvent -> editor.open());
        openFileButton.addActionListener(actionEvent -> editor.open());
        saveFile.addActionListener(actionEvent -> editor.save());
        saveFileButton.addActionListener(actionEvent -> editor.save());
        saveFileAs.addActionListener(actionEvent -> editor.saveAs());
        saveFileAsButton.addActionListener(actionEvent -> editor.saveAs());
        undoButton.addActionListener(actionEvent -> editor.undo());
        redoButton.addActionListener(actionEvent -> editor.redo());
        copyButton.addActionListener(actionEvent -> editor.copy());
        cutButton.addActionListener(actionEvent -> editor.cut());
        pasteButton.addActionListener(actionEvent -> editor.paste());
        insertImageButton.addActionListener(actionEvent -> editor.insertImage());
        resizeImageButton.addActionListener(actionEvent -> editor.resizeImage());
        fontBox.addActionListener(actionEvent -> {
            editor.updateStyle(getStyle());
            editor.grabFocus();
        });
        fontSize.addChangeListener(changeEvent -> {
            editor.updateStyle(getStyle());
            editor.grabFocus();
        });
        boldText.addActionListener(actionEvent -> {
            editor.updateStyle(getStyle());
            editor.grabFocus();
        });
        italicText.addActionListener(actionEvent -> {
            editor.updateStyle(getStyle());
            editor.grabFocus();
        });
    }

    private Font getStyle() {
        int type = Font.PLAIN;
        if (boldText.isSelected()){
            type += Font.BOLD;
        }
        if (italicText.isSelected()){
            type += Font.ITALIC;
        }
        return new Font(fontBox.getSelectedItem().toString(), type, (Integer) fontSize.getValue());
    }
}
