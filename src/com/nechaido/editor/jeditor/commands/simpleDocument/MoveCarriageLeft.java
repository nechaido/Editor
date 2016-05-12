package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;

/**
 * Created by nechaido on 5/12/16.
 */
public class MoveCarriageLeft extends AbstractSimpleDocumentCommand {

    public MoveCarriageLeft(Context context){
        super(context);
    }

    @Override
    public void execute() {

    }

    @Override
    public void unExecute() {

    }

    @Override
    public Type type() {
        return null;
    }
}
