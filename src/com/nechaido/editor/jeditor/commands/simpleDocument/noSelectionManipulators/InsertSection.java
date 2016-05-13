package com.nechaido.editor.jeditor.commands.simpleDocument.noSelectionManipulators;

import com.nechaido.editor.jeditor.Carriage;
import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.commands.simpleDocument.AbstractSimpleDocumentCommand;
import com.nechaido.editor.jeditor.document.Element;
import com.nechaido.editor.jeditor.document.simpleDocument.ElementComposition;

import java.util.ArrayList;

/**
 * Created by nechaido on 5/9/16.
 */
public class InsertSection extends AbstractSimpleDocumentCommand {

    private Carriage previousCarriage;
    private Carriage previousEnd;
    private Carriage previousStart;

    private Element[] inserts;

    public InsertSection(Context context, Element[] inserts) {
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
        this.inserts = inserts;
    }

    @Override
    public void execute() {
        if (inserts.length == 1){
            context.getDocument().getElement(context.getCarriage().row).addAllElements(context.getCarriage().element, inserts[0]);
            context.getCarriage().element += inserts[0].length();
        } else {
            Element newRow = context.getDocument().getElement(previousCarriage.row).getSubElement(
                    previousCarriage.element, context.getDocument().getElement(previousCarriage.row).length() - 1);
            context.getDocument().getElement(previousCarriage.row).removeElements(previousCarriage.element);
            for (int i = 0; i < inserts.length - 1; i++) {
                context.getDocument().getElement(previousCarriage.row + i).addAllElements(inserts[i]);
                context.getDocument().addElement(previousCarriage.row + i + 1, new ElementComposition(context.getCurrentStyle()));
            }
            context.getDocument().getElement(previousCarriage.row + inserts.length - 1).addAllElements(inserts[inserts.length - 1]);
            context.getDocument().getElement(previousCarriage.row + inserts.length - 1).addAllElements(newRow);
            context.getCarriage().row = previousCarriage.row + inserts.length - 1;
            context.getCarriage().element = inserts[inserts.length - 1].length() - 1;
        }
    }

    @Override
    public void unExecute() {
        if (inserts.length == 1){
            context.getDocument().getElement(context.getCarriage().row).removeElements(
                    previousCarriage.element, previousCarriage.element + inserts[0].length());
        } else {
            context.getDocument().getElement(previousCarriage.row).removeElements(previousCarriage.element);
            for (int i = 1; i < inserts.length - 1; i++) {
                context.getDocument().removeElement(previousCarriage.row + 1);
            }
            for (int i = 0; i < inserts[inserts.length - 1].length(); i++) {
                context.getDocument().getElement(previousCarriage.row + 1).removeElement(0);
            }
            context.getDocument().getElement(previousCarriage.row).addAllElements(
                    context.getDocument().getElement(previousCarriage.row + 1));
            context.getDocument().removeElement(previousCarriage.row + 1);
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
        return Type.INSERT;
    }
}
