package com.nechaido.editor.jeditor.commands.simpleDocument.carriageMover;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.simpleDocument.AbstractSimpleDocumentCommand;

/**
 * Created by nechaido on 5/12/16.
 */
public class MoveCarriageLeft extends AbstractSimpleDocumentCommand {

    public MoveCarriageLeft(Context context){
        super(context, false);
    }

    @Override
    public void execute() {
        if (context.getCarriage().element > 0) {
            --context.getCarriage().element;
        } else if (context.getCarriage().row > 0) {
            --context.getCarriage().row;
            context.getCarriage().element =
                    context.getDocument().getElement(context.getCarriage().row).length();
        }
    }

    @Override
    public void unExecute() {
        if (context.getCarriage().element < context.getDocument().getElement(context.getCarriage().row).length()) {
            context.getCarriage().element++;
        } else if (context.getCarriage().row < context.getDocument().length() - 1) {
            context.getCarriage().element = 0;
            context.getCarriage().row++;
        }
    }

    @Override
    public Type type() {
        return Type.MOVECARRIEGE;
    }
}
