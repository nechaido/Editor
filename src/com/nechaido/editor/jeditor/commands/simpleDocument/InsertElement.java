package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
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
        context.getDocument().getElement(context.getCarriage().rowIndex).addElement(
                context.getCarriage().elementIndex, element);
        context.getCarriage().elementIndex++;
    }

    @Override
    public void unExecute() {
        context.getCarriage().elementIndex--;
        context.getDocument().getElement(context.getCarriage().rowIndex).removeElement(
                context.getCarriage().elementIndex);
    }

    @Override
    public Type type() {
        return Type.INSERT;
    }
}
