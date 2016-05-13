package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators.InsertElement;
import com.nechaido.editor.jeditor.document.Element;

/**
 * Created by Nechai Dmytro nechaido@gmail.com  on 5/13/16.
 */
public class InsertInstead extends AbstractSimpleDocumentCommand {

    private InsertElement insertElement;
    private RemoveSection removeSection;


    public InsertInstead(Context context, Element element) {
        super(context, true);
        removeSection = new RemoveSection(context);
        insertElement = new InsertElement(context, element);
    }

    @Override
    public void execute() {
        removeSection.execute();
        insertElement.execute();
    }

    @Override
    public void unExecute() {
        insertElement.unExecute();
        removeSection.unExecute();
    }

    @Override
    public Type type() {
        return Type.INSERT;
    }
}
