package com.nechaido.editor.jeditor.commands.simpleDocument;

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

    public InsertSection(Context context, int selectionStartRow, int selectionStartElement, ArrayList<Element> section){
        super(context, true);
        this.selectionStartRow = selectionStartRow;
        this.selectionStartElement = selectionStartElement;
        selectionEndRow = selectionStartRow + section.size() - 1;
        inserted = section;
        if (section.size() == 1) {
            selectionEndElement = selectionStartElement;
        }
        selectionEndElement += section.get(section.size() - 1).amountOfChildElements();
    }

    @Override
    public void execute() {
        if (inserted.size() == 1){
            context.getDocument().getChildElement(selectionStartRow).addAllChildElements(selectionStartElement,
                                                                                            inserted.get(0));
        } else {
            Element firstRowOfSection = context.getDocument().getChildElement(selectionStartRow);
            Element element = firstRowOfSection.getSubElement(selectionStartElement,
                    firstRowOfSection.amountOfChildElements());
            firstRowOfSection.removeChildElements(selectionStartElement, firstRowOfSection.amountOfChildElements());
            context.getDocument().addChildElement(selectionStartRow + 1, element);
            for (int i = 0; i < inserted.size(); ++i){
                Element currentRow = context.getDocument().getChildElement(i + selectionStartRow);
                if (i != inserted.size() - 1){
                    currentRow.addAllChildElements(inserted.get(i));
                    if (i < selectionStartRow - selectionEndRow){
                        context.getDocument().addChildElement(i+1, new ElementComposition());
                    }
                } else {
                    currentRow.addAllChildElements(0, inserted.get(i));
                }
            }
        }
    }

    @Override
    public void unExecute() {
        if (inserted.size() == 1){
            context.getDocument().getChildElement(selectionStartRow).removeChildElements(selectionStartElement, selectionEndElement);
        } else {
            Element firstPart = context.getDocument().getChildElement(selectionStartRow);
            firstPart.removeChildElements(selectionStartElement);
            Element secondPart = context.getDocument().getChildElement(selectionEndRow);
            secondPart.removeChildElements(0, selectionEndElement);
            firstPart.addAllChildElements(secondPart);
            context.getDocument().removeChildElements(selectionStartRow + 1, selectionEndRow);
        }
    }
}
