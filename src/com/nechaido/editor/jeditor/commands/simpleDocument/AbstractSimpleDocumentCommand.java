package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;

/**
 * Created by nechaido on 5/9/16.
 */
public abstract class AbstractSimpleDocumentCommand implements Command {

    protected Context context;
    protected boolean major;

    protected AbstractSimpleDocumentCommand(Context context) {
        this.context = context;
    }

    protected AbstractSimpleDocumentCommand(Context context, boolean major) {
        this.context = context;
        this.major = major;
    }

    @Override
    public boolean isMajor() {
        return major;
    }

}
