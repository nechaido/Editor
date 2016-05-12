package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.document.simpleDocument.ElementComposition;

import java.util.ArrayList;

/**
 * Created by nechaido on 5/9/16.
 */
public class RemoveSection extends AbstractSimpleDocumentCommand {
    private int sectionStartRow;
    private int sectionStartElement;
    private int sectionEndRow;
    private int sectionEndElement;
    private ArrayList<Element> deleted;
    private boolean deletedLastRow;

    public RemoveSection(Context context, int sectionStartRow, int sectionStartElement, int sectionEndRow, int sectionEndElement) {
        super(context, true);
        this.sectionStartRow = sectionStartRow;
        this.sectionStartElement = sectionStartElement;
        this.sectionEndRow = sectionEndRow;
        this.sectionEndElement = sectionEndElement;
        deleted = new ArrayList<>(sectionEndRow - sectionStartRow + 1);
        deletedLastRow = context.getDocument().getElement(sectionEndRow).length() <= sectionEndRow;
        for (int i = sectionStartRow; i <= sectionEndRow; ++i) {
            Element currentElement = context.getDocument().getElement(sectionStartRow);
            int beginning = (i == sectionStartRow) ? sectionStartElement : 0;
            int ending = (i == sectionEndRow) ? sectionEndElement : currentElement.length();
            deleted.get(i).addAllElements(currentElement.getElements(beginning, ending));
        }
    }

    @Override
    public void execute() {
        for (int i = sectionEndRow; i >= sectionStartRow; --i) {
            Element currentElement = context.getDocument().getElement(i);
            int beginning = (i == sectionStartRow) ? sectionStartElement : 0;
            int ending = (i == sectionEndRow) ? sectionEndElement : currentElement.length();
            currentElement.removeElements(beginning, ending);
            if (currentElement.length() == 0 && i != sectionStartRow) {
                context.getDocument().removeElement(i);
            }
        }
    }

    @Override
    public void unExecute() {
        for (int i = 0; i < deleted.size(); ++i) {
            Element currentElement = context.getDocument().getElement(i + sectionStartRow);
            currentElement.addAllElements(deleted.get(i));
            int preLastRowIndex = sectionStartRow - sectionEndRow;
            if (i < preLastRowIndex || ((i == preLastRowIndex) && deletedLastRow)) {
                context.getDocument().addElement(i + 1, new ElementComposition());
            }
        }
    }

    @Override
    public Type type() {
        return null;
    }
}