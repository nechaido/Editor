package com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.commands.simpleDocument.AbstractSimpleDocumentCommand;
import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.document.simpleDocument.Picture;

import java.awt.*;

/**
 * Created by Nechai Dmytro nechaido@gmail.com  on 5/14/16.
 */
public class ResizeImage extends AbstractSimpleDocumentCommand {

    private Dimension oldSize;
    private Dimension newSize;

    public ResizeImage(Context context, Dimension size) {
        super(context, true);
        newSize = size;
        oldSize = ((Picture) context.getDocument().getElement(context.getCarriage().row).getElement(context.getCarriage().element)).getSize();
    }

    @Override
    public void execute() {
        ((Picture) context.getDocument().getElement(context.getCarriage().row).getElement(context.getCarriage().element)).setSize(newSize);
    }

    @Override
    public void unExecute() {
        ((Picture) context.getDocument().getElement(context.getCarriage().row).getElement(context.getCarriage().element)).setSize(oldSize);
    }

    @Override
    public Type type() {
        return null;
    }
}
