package com.nechaido.editor.jeditor.commands.simpleDocument.carriageMover;

import com.nechaido.editor.jeditor.Carriage;
import com.nechaido.editor.jeditor.Context;

/**
 * Created by Nechai Dmytro nechaido@gmail.com  on 5/12/16.
 */
public class MoveSelectionUp extends MoveCarriageUp{

    private Carriage previousEnd;

    public MoveSelectionUp(Context context)  {
        super(context);
        previousEnd = new Carriage();
        previousEnd.row = context.getSelectionEnd().row;
        previousEnd.element = context.getSelectionEnd().element;
    }

    @Override
    public void execute() {
        super.execute();
        context.getSelectionEnd().row = context.getCarriage().row;
        context.getSelectionEnd().element = context.getCarriage().element;
    }

    @Override
    public void unExecute() {
        super.unExecute();
        context.getSelectionEnd().row = previousEnd.row;
        context.getSelectionEnd().element = previousEnd.element;
    }
}
