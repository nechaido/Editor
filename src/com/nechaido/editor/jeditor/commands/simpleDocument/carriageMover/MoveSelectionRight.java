package com.nechaido.editor.jeditor.commands.simpleDocument.carriageMover;

import com.nechaido.editor.jeditor.Carriage;
import com.nechaido.editor.jeditor.Context;

/**
 * Created by Nechai Dmytro nechaido@gmail.com  on 5/12/16.
 */
public class MoveSelectionRight extends MoveCarriageRight {

    private Carriage previousEnd;

    public MoveSelectionRight(Context context)  {
        super(context);
        previousEnd = new Carriage();
        previousEnd.row = context.getSelectionEnd().row;
        previousEnd.element = context.getSelectionEnd().element;
    }

    @Override
    public void execute() {
        super.execute();
        context.getSelectionEnd().row = context.getCarriage().row;
        context.getSelectionEnd().element = context.getCarriage().element;
    }

    @Override
    public void unExecute() {
        super.unExecute();
        context.getSelectionEnd().row = previousEnd.row;
        context.getSelectionEnd().element = previousEnd.element;
    }

//    @Override
//    public void execute() {
//        super.execute();
//        if (context.getSelectionStart().row > context.getCarriage().row){
//            context.getSelectionEnd().row = context.getSelectionStart().row ;
//            context.getSelectionEnd().element = context.getSelectionStart().element;
//            context.getSelectionStart().row = context.getCarriage().row;
//            context.getSelectionStart().element = context.getCarriage().element;
//        } else if (context.getSelectionStart().row == context.getCarriage().row){
//            if (context.getSelectionStart().element > context.getCarriage().element){
//                context.getSelectionStart().element = context.getCarriage().element;
//                context.getSelectionEnd().row = previousStart.row;
//                context.getSelectionEnd().element = previousStart.element;
//            } else {
//                context.getSelectionEnd().row = context.getCarriage().row;
//                context.getSelectionEnd().element = context.getCarriage().element;
//            }
//        } else {
//            context.getSelectionEnd().row = context.getCarriage().row ;
//            context.getSelectionEnd().element = context.getCarriage().element;
//        }
//    }

//    @Override
//    public void unExecute() {
//        super.unExecute();
//        context.getSelectionStart().row = previousStart.row;
//        context.getSelectionStart().element = previousStart.element;
//        context.getSelectionEnd().row = previousEnd.row;
//        context.getSelectionEnd().element = previousEnd.element;
//    }
}
