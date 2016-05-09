package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.document.Element;

import java.util.ArrayList;

/**
 * Created by nechaido on 5/9/16.
 */
public class InsertSectionInstead extends AbstractSimpleDocumentCommand {
    private RemoveSection removeSection;
    private InsertSection insertSection;

    public InsertSectionInstead(Context context,
                         int sectionStartRow, int sectionStartElement, int sectionEndRow, int sectionEndElement,
                         ArrayList<Element> section){
        super(context, true);
        removeSection = new RemoveSection(context, sectionStartRow, sectionStartElement, sectionEndRow, sectionEndElement);
        insertSection = new InsertSection(context, sectionStartRow, sectionStartElement, section);
    }

    @Override
    public void execute() {
        removeSection.execute();
        insertSection.execute();
    }

    @Override
    public void unExecute() {
        removeSection.unExecute();
        insertSection.unExecute();
    }
}
