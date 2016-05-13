package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Carriage;
import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.document.simpleDocument.ElementComposition;

import java.util.ArrayList;

/**
 * Created by nechaido on 5/9/16.
 */
public class RemoveSection extends AbstractSimpleDocumentCommand {

    private Carriage previousCarriage;
    private Carriage previousEnd;
    private Carriage previousStart;

    private Carriage selectionStart;
    private Carriage selectionEnd;

    private ArrayList<Element> deleted;
    private int amountOfElements;

    public RemoveSection(Context context) {
        super(context, true);
        previousCarriage = new Carriage();
        previousEnd = new Carriage();
        previousStart = new Carriage();
        previousCarriage.row = context.getCarriage().row;
        previousCarriage.element = context.getCarriage().element;
        previousEnd.row = context.getSelectionEnd().row;
        previousEnd.element = context.getSelectionEnd().element;
        previousStart.row = context.getSelectionStart().row;
        previousStart.element = context.getSelectionStart().element;
        selectionStart = new Carriage();
        selectionEnd = new Carriage();
        if (context.getSelectionEnd().row > context.getSelectionStart().row){
            selectionEnd = context.getSelectionEnd();
            selectionStart = context.getSelectionStart();
        } else if (context.getSelectionEnd().row < context.getSelectionStart().row){
            selectionEnd = context.getSelectionStart();
            selectionStart = context.getSelectionEnd();
        } else if (context.getSelectionEnd().element > context.getSelectionStart().element){
            selectionEnd = context.getSelectionEnd();
            selectionStart = context.getSelectionStart();
        } else  {
            selectionEnd = context.getSelectionStart();
            selectionStart = context.getSelectionEnd();
        }
        deleted = new ArrayList<>(selectionEnd.row - selectionStart.row);
        for (int i = selectionStart.row; i <= selectionEnd.row; i++) {
            Element currentRow = context.getDocument().getElement(i);
            int start = ((i == selectionStart.row) ? selectionStart.element : 0);
            int end = ((i == selectionEnd.row) ? selectionEnd.element : currentRow.length());
            end--;
            if (end < 0){
                deleted.add(new ElementComposition(context.getCurrentStyle()));
            } else {
                deleted.add(currentRow.getSubElement(start, end));
            }
        }
        amountOfElements = 0;
    }

    @Override
    public void execute() {
        if (selectionStart.row == selectionEnd.row){
            context.getDocument().getElement(selectionStart.row).removeElements(
                    selectionStart.element, selectionEnd.element);
        } else {
            context.getDocument().getElement(selectionStart.row).removeElements(selectionStart.element);
            for (int i = selectionStart.row + 1; i < selectionEnd.row; i++) {
                context.getDocument().removeElement(selectionStart.row + 1);
            }
            amountOfElements = context.getDocument().getElement(selectionStart.row).length();
            for (int i = 0; i < selectionEnd.element; i++) {
                context.getDocument().getElement(selectionStart.row + 1).removeElement(0);
            }
            context.getDocument().getElement(selectionStart.row).addAllElements(
                    context.getDocument().getElement(selectionStart.row + 1));
            context.getDocument().removeElement(selectionStart.row + 1);
        }
        context.getCarriage().row = selectionStart.row;
        context.getCarriage().element = selectionStart.element;
        context.getSelectionEnd().row = selectionStart.row;
        context.getSelectionEnd().element = selectionStart.element;
        context.getSelectionStart().row = selectionStart.row;
        context.getSelectionStart().element = selectionStart.element;
    }

    @Override
    public void unExecute() {
        if (previousStart.row == previousEnd.row) {
            context.getDocument().getElement(selectionStart.row).addAllElements(selectionStart.element, deleted.get(0));
        } else {
            Element newRow = context.getDocument().getElement(selectionStart.row).getSubElement(amountOfElements, context.getDocument().getElement(selectionStart.row).length() - 1);
            context.getDocument().getElement(selectionStart.row).removeElements(amountOfElements);
            context.getDocument().getElement(selectionStart.row).addAllElements(deleted.get(0));
            context.getDocument().addElement(selectionStart.row + 1 , newRow);
            for (int i = 1; i < deleted.size() - 1; i++) {
                context.getDocument().addElement(selectionStart.row + i, deleted.get(i));
            }
            context.getDocument().getElement(selectionStart.row + deleted.size() - 1).addAllElements(0, deleted.get(deleted.size() - 1));
        }
        context.getCarriage().row = previousCarriage.row;
        context.getCarriage().element = previousCarriage.element;
        context.getSelectionStart().row = previousStart.row;
        context.getSelectionStart().element = previousStart.element;
        context.getSelectionEnd().row = previousEnd.row;
        context.getSelectionEnd().element = previousEnd.element;
    }

    @Override
    public Type type() {
        return Type.REMOVE;
    }
}
