package com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.simpleDocument.AbstractSimpleDocumentCommand;
import com.nechaido.editor.jeditor.document.Element;

/**
 * Created by nechaido on 5/12/16.
 */
public class SplitLine extends AbstractSimpleDocumentCommand {

    public SplitLine(Context context) {
        super(context, true);
    }

    @Override
    public void execute() {
        Element currentRow = context.getDocument().getElement(context.getCarriage().row);
        Element element = currentRow.getSubElement(context.getCarriage().element,
                currentRow.length() - 1);
        currentRow.removeElements(context.getCarriage().element, currentRow.length());
        context.getDocument().addElement(context.getCarriage().row + 1, element);
        context.getCarriage().row++;
        context.getCarriage().element = 0;
    }

    @Override
    public void unExecute() {
        Element secondPart = context.getDocument().getElement(context.getCarriage().row);
        context.getCarriage().row--;
        context.getCarriage().element = context.getDocument().getElement(context.getCarriage().row).length();
        context.getDocument().getElement(context.getCarriage().row).addAllElements(secondPart);
        context.getDocument().removeElement(context.getCarriage().row + 1);
    }

    @Override
    public Type type() {
        return Type.BREAKLINE;
    }
}
