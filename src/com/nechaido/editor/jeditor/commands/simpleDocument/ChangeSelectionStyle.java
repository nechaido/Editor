package com.nechaido.editor.jeditor.commands.simpleDocument;

import com.nechaido.editor.jeditor.Carriage;
import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.commands.Command;
import com.nechaido.editor.jeditor.document.simpleDocument.ElementComposition;
import com.nechaido.editor.jeditor.document.simpleDocument.Style;

/**
 * Created by Nechai Dmytro nechaido@gmail.com  on 5/14/16.
 */
public class ChangeSelectionStyle extends AbstractSimpleDocumentCommand {

    private Style[][] oldStyles;
    private Style newStyle;

    private Carriage selectionStart;
    private Carriage selectionEnd;

    public ChangeSelectionStyle(Context context, Style style) {
        super(context, true);
        newStyle = style;
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
        int size = selectionEnd.row - selectionStart.row + 1;
        oldStyles = new Style[size][];
        for (int i = selectionStart.row; i <= selectionEnd.row ; i++) {
            ElementComposition currentRow = (ElementComposition) context.getDocument().getElement(i);
            int start = ((i == selectionStart.row) ? selectionStart.element : 0);
            int end = ((i == selectionEnd.row) ? selectionEnd.element : currentRow.length());
            int length = end - start;
            oldStyles[i - selectionStart.row] = new Style[length];
            for (int j = 0; j < length; j++) {
                   oldStyles[i  - selectionStart.row][j] = currentRow.getStyle(start+j);
            }
        }
    }

    @Override
    public void execute() {
        for (int i = context.getSelectionStart().row; i <= context.getSelectionEnd().row ; i++) {
            ElementComposition currentRow = (ElementComposition) context.getDocument().getElement(i);
            int start = ((i == selectionStart.row) ? selectionStart.element : 0);
            int end = ((i == selectionEnd.row) ? selectionEnd.element : currentRow.length());
            int length = end - start;
            for (int j = 0; j < length; j++) {
                currentRow.setStyle(start+j, newStyle);
            }
        }
    }

    @Override
    public void unExecute() {
        for (int i = selectionStart.row; i <= selectionEnd.row ; i++) {
            ElementComposition currentRow = (ElementComposition) context.getDocument().getElement(i);
            int start = ((i == selectionStart.row) ? selectionStart.element : 0);
            int end = ((i == selectionEnd.row) ? selectionEnd.element : currentRow.length());
            int length = end - start;
            for (int j = 0; j < length; j++) {
                currentRow.setStyle(start+j, oldStyles[i - selectionStart.row][j]);
            }
        }
    }

    @Override
    public Type type() {
        return null;
    }
}
