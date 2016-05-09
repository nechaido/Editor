package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;

/**
 * Created by nechaido on 5/9/16.
 */
public class InsertElement extends AbstractSimpleDocumentCommand {
    private int rowIndex;
    private int elementIndex;
    private Element element;

    public InsertElement(Context context, int rowIndex, int elementIndex, Element element) {
        super(context, element.isMajor());
        this.rowIndex = rowIndex;
        this.elementIndex = elementIndex;
        this.element = element;
    }

    @Override
    public void execute() {
        context.getDocument().getChildElement(rowIndex).addChildElement(elementIndex, element);
    }

    @Override
    public void unExecute() {
        context.getDocument().getChildElement(rowIndex).removeChildElement(elementIndex);
    }
}
