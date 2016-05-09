package com.nechaido.editor.jeditor.commands;

import java.util.Stack;

/**
 * Created by nechaido on 5/7/16.
 */
public class CommandHistory {

    private Stack<Command> undoHistory;
    private Stack<Command> redoHistory;

    public CommandHistory(){
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
    }

    public void undo(){
        Command command = null;
        while(!undoHistory.isEmpty() && !(command = undoHistory.pop()).isMajor()){
            redoHistory.push(command);
            command.unExecute();
        }
        if (command != null && command.isMajor()){
            redoHistory.push(command);
            command.unExecute();
        }
    }

    public void redo(){
        Command command = null;
        while(!redoHistory.isEmpty() && !(command = redoHistory.pop()).isMajor()){
            undoHistory.push(command);
            command.execute();
        }
        if (command != null && command.isMajor()){
            undoHistory.push(command);
            command.execute();
        }
    }

    public void push(Command command){
        if (!redoHistory.isEmpty()){
            redoHistory = new Stack<>();
        }
        undoHistory.push(command);
    }

    public void empty(){
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
    }
}
