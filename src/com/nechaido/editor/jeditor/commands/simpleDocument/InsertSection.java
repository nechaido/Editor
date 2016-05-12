package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.document.simpleDocument.ElementComposition;

import java.util.ArrayList;

/**
 * Created by nechaido on 5/9/16.
 */
public class InsertSection extends AbstractSimpleDocumentCommand {
    private int selectionStartRow;
    private int selectionStartElement;
    private int selectionEndRow;
    private int selectionEndElement;
    private ArrayList<Element> inserted;

    public InsertSection(Context context, int selectionStartRow, int selectionStartElement, ArrayList<Element> section) {
        super(context, true);
        this.selectionStartRow = selectionStartRow;
        this.selectionStartElement = selectionStartElement;
        selectionEndRow = selectionStartRow + section.size() - 1;
        inserted = section;
        if (section.size() == 1) {
            selectionEndElement = selectionStartElement;
        }
        selectionEndElement += section.get(section.size() - 1).length();
    }

    @Override
    public void execute() {
        if (inserted.size() == 1) {
            context.getDocument().getElement(selectionStartRow).addAllElements(selectionStartElement,
                    inserted.get(0));
        } else {
            for (int i = 0; i < inserted.size(); ++i) {
                //TODO insert action
                Element currentRow = context.getDocument().getElement(i + selectionStartRow);
                if (i != inserted.size() - 1) {
                    currentRow.addAllElements(inserted.get(i));
                    if (i < selectionStartRow - selectionEndRow) {
                        context.getDocument().addElement(i + 1, new ElementComposition());
                    }
                } else {
                    currentRow.addAllElements(0, inserted.get(i));
                }
            }
        }
    }

    @Override
    public void unExecute() {
        if (inserted.size() == 1) {
            context.getDocument().getElement(selectionStartRow).removeElements(selectionStartElement, selectionEndElement);
        } else {
            Element firstPart = context.getDocument().getElement(selectionStartRow);
            firstPart.removeElements(selectionStartElement);
            //TODO insert action
        }
    }

    @Override
    public Type type() {
        return null;
    }
}
