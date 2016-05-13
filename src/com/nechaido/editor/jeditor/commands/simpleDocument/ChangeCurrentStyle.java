package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.document.simpleDocument.Style;

/**
 * Created by Nechai Dmytro nechaido@gmail.com  on 5/14/16.
 */
public class ChangeCurrentStyle extends AbstractSimpleDocumentCommand {

    private Style oldStyle;
    private Style newStyle;

    public ChangeCurrentStyle(Context context, Style style) {
        super(context, false);
        newStyle = style;
        oldStyle = context.getCurrentStyle();
    }

    @Override
    public void execute() {
        context.setCurrentStyle(newStyle);
        context.getDocument().setStyle(newStyle);
    }

    @Override
    public void unExecute() {
        context.setCurrentStyle(oldStyle);
        context.getDocument().setStyle(oldStyle);
    }

    @Override
    public Type type() {
        return Type.STYLECHANGE;
    }
}
