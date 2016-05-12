package com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.simpleDocument.AbstractSimpleDocumentCommand;
import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.document.simpleDocument.ElementComposition;

import java.util.Collection;

/**
 * Created by nechaido on 5/12/16.
 */
public class RemoveLineBreak extends AbstractSimpleDocumentCommand {
    private int elementAmount;
    private boolean executed;

    public RemoveLineBreak(Context context) {
        super(context, true);
        executed = false;
    }

    @Override
    public void execute() {
        if (context.getCarriage().row == 0) {
            return;
        }
        elementAmount = context.getDocument().getElement(context.getCarriage().row - 1).length();
        Element secondPart = context.getDocument().getElement(context.getCarriage().row);
        context.getDocument().getElement(context.getCarriage().row - 1).addAllElements(secondPart);
        context.getDocument().removeElement(context.getCarriage().row);
        context.getCarriage().row--;
        context.getCarriage().element = elementAmount;
        executed = true;
    }

    @Override
    public void unExecute() {
        if (!executed) {
            return;
        }
        Element firstRow = context.getDocument().getElement(context.getCarriage().row);
        Element newRow = firstRow.getSubElement(elementAmount, firstRow.length() - 1);
        context.getDocument().addElement(context.getCarriage().row + 1, newRow);
        firstRow.removeElements(elementAmount);
        context.getCarriage().row++;
        context.getCarriage().element = 0;
    }

    @Override
    public Type type() {
        return null;
    }
}
