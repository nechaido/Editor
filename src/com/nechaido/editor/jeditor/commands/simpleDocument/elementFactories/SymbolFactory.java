package com.nechaido.editor.jeditor.commands.simpleDocument.elementFactories;

import com.nechaido.editor.jeditor.document.simpleDocument.Symbol;

import java.util.HashMap;

/**
 * Created by nechaido on 5/9/16.
 */
public class SymbolFactory {
    private HashMap<Character, Symbol> symbols;

    private static SymbolFactory instance;

    public static SymbolFactory getInstance() {
        if (instance == null) {
            instance = new SymbolFactory();
        }
        return instance;
    }

    private SymbolFactory(){
        symbols = new HashMap<>();
    }

    public Symbol getSymbol(char character) {
        if (!symbols.containsKey(character)){
            symbols.put(character, new Symbol(character));
        }
        return symbols.get(character);
    }
}
