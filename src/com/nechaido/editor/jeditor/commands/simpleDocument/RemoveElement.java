package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;
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
        context.getCarriage().elementIndex--;
        element = context.getDocument().getElement(context.getCarriage().rowIndex).getElement(
                context.getCarriage().elementIndex);
        context.getDocument().getElement(
                context.getCarriage().rowIndex).removeElement(context.getCarriage().elementIndex);
        major = element.isMajor();
    }

    @Override
    public void unExecute() {
        context.getDocument().getElement(context.getCarriage().rowIndex).addElement(context.getCarriage().elementIndex, element);
        context.getCarriage().elementIndex++;
    }

    @Override
    public Type type() {
        return Type.REMOVE;
    }
}
