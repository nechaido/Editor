package com.nechaido.editor.jeditor.document.simpleDocument;

import com.nechaido.editor.jeditor.Carriage;
import com.nechaido.editor.jeditor.Context;
import com.nechaido.editor.jeditor.document.Document;
import com.nechaido.editor.jeditor.document.Element;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nechai Dmytro nechaido@gmail.com  on 5/13/16.
 */
public class DocumentSerialiser {
    private Context context;

    public DocumentSerialiser(Context context){
        this.context = context;
    }

    public void readDocument(File file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            Document document = context.getDocument();
            document.empty();
            while ((s = br.readLine()) != null){
                document.addElement(getLine(s));
            }
            if (document.length() == 0){
                document.addElement(new ElementComposition(context.getCurrentStyle()));
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public String serializeDocument(){
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < context.getDocument().length(); i++) {
            int start = 0;
            int end =  context.getDocument().getElement(i).length();
            for (int j = start; j < end; j++){
                Element element = context.getDocument().getElement(i).getElement(j);
                if (element instanceof Symbol){
                    result.append(((Symbol) element).getCharacter());
                } else if (element instanceof Picture){
                    if (((Picture) element).getImage() != null){
                        String path = context.getPictureFactory().getPath(((Picture) element).getImage());
                        int width = ((Picture) element).getSize().width;
                        int height = ((Picture) element).getSize().height;
                        result.append("<img src=\"" + path  + "\" width=\"" + width + "\" height=\"" + height + "\">");
                    }
                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public String serializeSelection(){
        StringBuffer result = new StringBuffer();
        Carriage selectionStart = new Carriage();
        Carriage selectionEnd = new Carriage();
        if (context.getSelectionEnd().row > context.getSelectionStart().row){
            selectionEnd = context.getSelectionEnd();
            selectionStart = context.getSelectionStart();
        } else if (context.getSelectionEnd().row < context.getSelectionStart().row){
            selectionEnd = context.getSelectionStart();
            selectionStart = context.getSelectionEnd();
        } else if (context.getSelectionEnd().element > context.getSelectionStart().element){
            selectionEnd = context.getSelectionEnd();
            selectionStart = context.getSelectionStart();
        } else {
            selectionEnd = context.getSelectionStart();
            selectionStart = context.getSelectionEnd();
        }
        for (int i = selectionStart.row; i <= selectionEnd.row; i++) {
            int start = ((i == selectionStart.row) ? selectionStart.element : 0);
            int end = ((i == selectionEnd.row) ? selectionEnd.element : context.getDocument().getElement(i).length());
            for (int j = start; j < end; j++){
                Element element = context.getDocument().getElement(i).getElement(j);
                if (element instanceof Symbol){
                    result.append(((Symbol) element).getCharacter());
                } else if (element instanceof Picture){
                    if (((Picture) element).getImage() != null){
                        String path = context.getPictureFactory().getPath(((Picture) element).getImage());
                        int width = ((Picture) element).getSize().width;
                        int height = ((Picture) element).getSize().height;
                        result.append("<img src=\"" + path  + "\" width=\"" + width + "\" height=\"" + height + "\">");
                    }
                }
            }
            if (end == context.getDocument().getElement(i).length()){
                result.append('\n');
            }
        }
        return result.toString();
    }

    public Element[] convert(String input){
        String[] lines = input.split("\n");
        Element[] result = new Element[lines.length];
        for (int i = 0; i < lines.length; i++) {
            result[i] = getLine(lines[i]);
        }
        return result;
    }

    public Element getLine(String input){
        ElementComposition row = new ElementComposition(context.getCurrentStyle());
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != '<'){
                row.addElement(context.getSymbolFactory().getSymbol(input.charAt(i)));
            } else {
                int j = 0;
                for (j = i; j < input.length() && input.charAt(j) != '>'; j++) {
                }
                if (j == input.length()){
                    row.addElement(context.getSymbolFactory().getSymbol(input.charAt(i)));
                } else {
                    String sub = input.substring(i + 1, j);
                    Pattern pattern = Pattern.compile("img\\s+src=\"([\\w./]*)\"\\s+width=\"(\\d+)\"\\s+height=\"(\\d+)\"");
                    Matcher matcher = pattern.matcher(sub);
                    String path = null;
                    int width = -1;
                    int height = -1;
                    if (matcher.find()){
                        path = matcher.group(1);
                        width = Integer.parseInt(matcher.group(2));
                        height = width = Integer.parseInt(matcher.group(3));
                    }
                    if (path != null && width != -1){
                        row.addElement(new Picture(context.getPictureFactory().getPicure(path), new Dimension(width, height)));
                        i = j;
                    } else {
                        row.addElement(context.getSymbolFactory().getSymbol(input.charAt(i)));
                    }

                }
            }
        }
        return row;
    }
}
