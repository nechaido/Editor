package com.nechaido.editor.jeditor.commands.simpleDocument.elementFactories;

import com.nechaido.editor.jeditor.document.simpleDocument.Symbol;

import java.util.HashMap;

/**
 * Created by nechaido on 5/9/16.
 */
public class SymbolFactory {
    private static SymbolFactory instance;
    private HashMap<Character, Symbol> symbols;

    private SymbolFactory() {
        symbols = new HashMap<>();
    }

    public static SymbolFactory getInstance() {
        if (instance == null) {
            instance = new SymbolFactory();
        }
        return instance;
    }

    public Symbol getSymbol(char character) {
        if (!symbols.containsKey(character)) {
            symbols.put(character, new Symbol(character));
        }
        return symbols.get(character);
    }
}
