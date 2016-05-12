package com.nechaido.editor.jeditor.commands;

import java.util.Stack;

/**
 * Created by nechaido on 5/7/16.
 */
public class CommandHistory {

    private Stack<Command> undoHistory;
    private Stack<Command> redoHistory;

    public CommandHistory() {
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
    }

    public void undo() {
        Command command;
        Command.Type type = undoHistory.peek().type();
        while (!undoHistory.isEmpty() && type == undoHistory.peek().type()) {
            command = undoHistory.pop();
            redoHistory.push(command);
            command.unExecute();
            if (command.isMajor()) {
                break;
            }
        }
    }

    public void redo() {
        Command command;
        Command.Type type = undoHistory.peek().type();
        while (!redoHistory.isEmpty() && type == redoHistory.peek().type()) {
            command = redoHistory.pop();
            undoHistory.push(command);
            command.execute();
            if (command.isMajor()) {
                break;
            }
        }
    }

    public void run(Command command) {
        if (!redoHistory.isEmpty()) {
            redoHistory = new Stack<>();
        }
        undoHistory.push(command);
        command.execute();
    }

    public void empty() {
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
    }
}
