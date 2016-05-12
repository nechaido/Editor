package com.nechaido.editor.jeditor.commands.simpleDocument.carriageMover;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.commands.simpleDocument.AbstractSimpleDocumentCommand;

/**
 * Created by nechaido on 5/12/16.
 */
public class MoveCarriageDown extends AbstractSimpleDocumentCommand{

    public MoveCarriageDown(Context context) {
        super(context, false);
    }

    @Override
    public void execute() {
        if (context.getCarriage().row < context.getDocument().length() - 1) {
            context.getCarriage().row++;
            int lastSymbol = context.getDocument().getElement(context.getCarriage().row).length();
            if (context.getCarriage().element > lastSymbol) {
                context.getCarriage().element = lastSymbol;
            }
        }
    }

    @Override
    public void unExecute() {
        if (context.getCarriage().row > 0) {
            context.getCarriage().row--;
        }
        int lastSymbol = context.getDocument().getElement(context.getCarriage().row).length();
        if (context.getCarriage().element > lastSymbol) {
            context.getCarriage().element = lastSymbol;
        }
    }

    @Override
    public Type type() {
        return Type.MOVECARRIEGE;
    }
}
