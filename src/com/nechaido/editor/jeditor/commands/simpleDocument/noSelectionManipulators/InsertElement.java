package com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.simpleDocument.AbstractSimpleDocumentCommand;
import com.nechaido.editor.jeditor.document.Element;

/**
 * Created by nechaido on 5/9/16.
 */
public class InsertElement extends AbstractSimpleDocumentCommand {
    private Element element;

    public InsertElement(Context context, Element element) {
        super(context, element.isMajor());
        this.element = element;
    }

    @Override
    public void execute() {
        context.getDocument().getElement(context.getCarriage().row).addElement(
                context.getCarriage().element, element);
        context.getCarriage().element++;
    }

    @Override
    public void unExecute() {
        context.getCarriage().element--;
        context.getDocument().getElement(context.getCarriage().row).removeElement(
                context.getCarriage().element);
    }

    @Override
    public Type type() {
        return Type.INSERT;
    }
}
