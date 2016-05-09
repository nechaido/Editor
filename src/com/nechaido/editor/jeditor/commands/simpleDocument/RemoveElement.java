package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;

/**
 * Created by nechaido on 5/9/16.
 */
public class RemoveElement extends AbstractSimpleDocumentCommand {
    private int rowIndex;
    private int elementIndex;
    private Element element;

    public RemoveElement(Context context, int rowIndex, int elementIndex) {
        super(context);
        this.rowIndex = rowIndex;
        this.elementIndex = elementIndex;
        this.element = context.getDocument().getChildElement(rowIndex).getChildElement(elementIndex);
        super.major = element.isMajor();
    }

    @Override
    public void execute() {
        context.getDocument().getChildElement(rowIndex).removeChildElement(elementIndex);
    }

    @Override
    public void unExecute() {
        context.getDocument().getChildElement(rowIndex).addChildElement(elementIndex, element);
    }
}
