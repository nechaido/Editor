package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.document.simpleDocument.ElementComposition;

import java.util.Collection;

/**
 * Created by nechaido on 5/12/16.
 */
public class RemoveLineBreak extends AbstractSimpleDocumentCommand {
    private int elementAmount;

    public RemoveLineBreak(Context context, int rowIndex) {
        super(context, true);
    }

    @Override
    public void execute() {
        if (context.getCarriage().rowIndex == 0) {
            return;
        }
        elementAmount = context.getDocument().getElement(context.getCarriage().rowIndex - 1).length();
        Element secondPart = context.getDocument().getElement(context.getCarriage().rowIndex);
        context.getDocument().getElement(context.getCarriage().rowIndex - 1).addAllElements(secondPart);
        context.getDocument().removeElement(context.getCarriage().rowIndex);
        context.getCarriage().rowIndex--;
        context.getCarriage().elementIndex = context.getDocument().getElement(
                context.getCarriage().rowIndex - 1).length();
    }

    @Override
    public void unExecute() {
        if (context.getCarriage().rowIndex == 0) {
            return;
        }
        Element firstRow = context.getDocument().getElement(context.getCarriage().rowIndex - 1);
        Element newRow = new ElementComposition(
                (Collection<Element>) firstRow.getElements(elementAmount, firstRow.length() - 1));
        context.getDocument().addElement(context.getCarriage().rowIndex, newRow);
        firstRow.removeElements(elementAmount);
        context.getCarriage().rowIndex++;
        context.getCarriage().elementIndex = 0;
    }

    @Override
    public Type type() {
        return null;
    }
}
