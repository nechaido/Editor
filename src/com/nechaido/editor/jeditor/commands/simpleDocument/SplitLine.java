package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
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
        Element currentRow = context.getDocument().getElement(context.getCarriage().rowIndex);
        Element element = currentRow.getSubElement(context.getCarriage().elementIndex,
                currentRow.length());
        currentRow.removeElements(context.getCarriage().elementIndex, currentRow.length());
        context.getDocument().addElement(context.getCarriage().rowIndex + 1, element);
        context.getCarriage().rowIndex++;
        context.getCarriage().elementIndex = 0;
    }

    @Override
    public void unExecute() {
        Element secondPart = context.getDocument().getElement(context.getCarriage().rowIndex);
        context.getDocument().getElement(context.getCarriage().rowIndex).addAllElements(secondPart);
        context.getDocument().removeElement(context.getCarriage().rowIndex + 1);
    }

    @Override
    public Type type() {
        return Type.BREAKLINE;
    }
}
