package com.example.wtfood.parser;


public class MyTokenizer extends Tokenizer {

    private String buffer;
    private Token currentToken;


    public MyTokenizer(String text){
        buffer = text;
        next();
    }

    @Override
    public void next() {
        buffer = buffer.replaceAll("\\s+","");

        if (buffer.isEmpty()){
            currentToken = null;
            return;
        }

        String current = "";
        if(buffer.charAt(0) != '<' && buffer.charAt(0) != '>' && buffer.charAt(0) != '=' && buffer.charAt(0) != ';'){
            current = getAttribute(buffer);
        }
        else if(buffer.charAt(0) == '<' || buffer.charAt(0) == '>' || buffer.charAt(0) == '='){
            current = getComparator(buffer);
        }
        else {
            current = getEND(buffer);
        }


        if(current.equals("price")){
            currentToken = new Token("price", Token.Attribute.PRICE);
        }
        if(current.equals("rating")){
            currentToken = new Token("rating", Token.Attribute.RATING);
        }
        if(current.equals("=")){
            currentToken = new Token("=", Token.Attribute.EQUAL);
        }
        if(current.equals("<")){
            currentToken = new Token("<", Token.Attribute.LESS);
        }
        if(current.equals(">")){
            currentToken = new Token(">", Token.Attribute.GREATER);
        }
        if(current.equals(">=")){
            currentToken = new Token(">=", Token.Attribute.GOE);
        }
        if(current.equals("<=")){
            currentToken = new Token("<=", Token.Attribute.LOE);
        }
        if(Character.isDigit(current.charAt(0))){
            currentToken = new Token(getValue(current), Token.Attribute.VALUE);
        }
        if(current.equals(";")){
            currentToken = new Token(";", Token.Attribute.END);
        }
        if(current.equals("delivery")){
            currentToken = new Token("delivery", Token.Attribute.DELIVERY);
        }
        if(current.equals("Y") || current.equals("N")){

            currentToken = new Token(current, Token.Attribute.DELIVERYValue);
        }


        buffer = buffer.substring(current.length());

    }

    public String getAttribute(String currentBuffer){
        int i = 0;
        while (currentBuffer.charAt(i) != '=' && currentBuffer.charAt(i) != '>' && currentBuffer.charAt(i) != '<' && currentBuffer.charAt(i) != ';'){
            i++;
            if (i == currentBuffer.length()){
                return currentBuffer.substring(0, i);
            }
        }
        return currentBuffer.substring(0, i);
    }

    public String getComparator(String currentBuffer){
        int i = 0;
        while (currentBuffer.charAt(i) == '=' || currentBuffer.charAt(i) == '>' || currentBuffer.charAt(i) == '<'){
            i++;
            if (i == currentBuffer.length()){
                return currentBuffer.substring(0, i);

            }
        }

        return currentBuffer.substring(0, i);
    }

    public String getValue(String currentBuffer){
        int i = 0;
        while (Character.isDigit(currentBuffer.charAt(0))){
            i++;
            if (i == currentBuffer.length()){
                return currentBuffer.substring(0, i);
            }
        }
        return currentBuffer.substring(0, i);

    }

    public String getEND(String currentBuffer){
        int i = 0;
        while (currentBuffer.charAt(i) == ';'){
            i++;
            if (i == currentBuffer.length()){
                return currentBuffer.substring(0, i);
            }
        }
        return currentBuffer.substring(0, i);
    }


    @Override
    public Token current() {
        return currentToken;
    }

    @Override
    public boolean hasNext() {
        return currentToken != null;
    }
}
