package com.nechaido.editor.jeditor;

import com.nechaido.editor.jeditor.commands.CommandHistory;
import com.nechaido.editor.jeditor.document.Document;
import com.nechaido.editor.jeditor.document.simpleDocument.SimpleDocument;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nechaido on 5/7/16.
 */
public class JEditor extends JComponent{

    private CommandHistory commandHistory;
    private Document document;

    public JEditor(){
        commandHistory = new CommandHistory();
        document = new SimpleDocument();
        setFont(new Font("Source Code Pro", Font.PLAIN, 15));
        setPreferredSize(new Dimension(1000, 1000));
    }

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        ((Graphics2D)graphics).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        graphics.drawString("Somegjjhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhdasfafdsssssssssgdaffffffff", 10, 25);
        graphics.drawString("Somegjjhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhdasfafdsssssssssgdaffffffff", 100, 100);
    }
}
