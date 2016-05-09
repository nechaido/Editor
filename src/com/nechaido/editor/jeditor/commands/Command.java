package com.nechaido.editor.jeditor.commands;

/**
 * Created by nechaido on 5/7/16.
 */
public interface Command {
    void execute();
    void unExecute();

    boolean isMajor();
}
