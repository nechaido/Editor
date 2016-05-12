package com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.simpleDocument.AbstractSimpleDocumentCommand;
import com.nechaido.editor.jeditor.document.Element;

/**
 * Created by nechaido on 5/9/16.
 */
public class RemoveElement extends AbstractSimpleDocumentCommand {
    private Element element;

    public RemoveElement(Context context) {
        super(context);
    }

    @Override
    public void execute() {
        context.getCarriage().element--;
        element = context.getDocument().getElement(context.getCarriage().row).getElement(
                context.getCarriage().element);
        context.getDocument().getElement(
                context.getCarriage().row).removeElement(context.getCarriage().element);
        major = element.isMajor();
    }

    @Override
    public void unExecute() {
        context.getDocument().getElement(context.getCarriage().row).addElement(context.getCarriage().element, element);
        context.getCarriage().element++;
    }

    @Override
    public Type type() {
        return Type.REMOVE;
    }
}
