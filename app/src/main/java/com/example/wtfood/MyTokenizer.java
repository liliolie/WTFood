package com.example.wtfood;

import java.util.Set;

public class MyTokenizer extends Tokenizer{

    private String buffer;
    private Token currentToken;


    public MyTokenizer(String text){
        this.buffer = text;
        next();
    }

    @Override
    public void next() {
        this.buffer = this.buffer.trim();

        if (this.buffer.isEmpty()){
            currentToken = null;
            return;
        }

        if (this.buffer.toLowerCase().substring(0, 4).equals("name") && (this.buffer.charAt(4) == '=' || this.buffer.charAt(4) == '>' || this.buffer.charAt(4) == '<' )){
            currentToken = new Token("name", Token.Attribute.NAME);
        }
        if (this.buffer.toLowerCase().substring(0, 4).equals("type") && (this.buffer.charAt(4) == '=' || this.buffer.charAt(4) == '>' || this.buffer.charAt(4) == '<' )){
            currentToken = new Token("type", Token.Attribute.TYPE);
        }
//        String currentAttribute = getAttribute(this.buffer);
//        if (currentAttribute.toLowerCase().equals("name")){
//            currentToken = new Token("name", Token.Attribute.NAME);
//        }
//        if (currentAttribute.toLowerCase().equals("type")){
//            currentToken = new Token("type", Token.Attribute.TYPE);
//        }
//        if (currentAttribute.toLowerCase().equals("price")){
//            currentToken = new Token("price", Token.Attribute.PRICE);
//        }
//        if (currentAttribute.toLowerCase().equals("rating")){
//            currentToken = new Token("rating", Token.Attribute.RATING);
//        }
//        if (currentAttribute.toLowerCase().equals("delivery")){
//            currentToken = new Token("delivery", Token.Attribute.DELIVERY);
//        }
//        if (currentAttribute.toLowerCase().equals("location")){
//            currentToken = new Token("location", Token.Attribute.LOCATION);
//        }
//        this.buffer = this.buffer.substring(currentAttribute.length());
//
//        String currentComparator = getComparator(this.buffer);
//        if (currentComparator.equals(">")){
//            currentToken = new Token(">", Token.Attribute.GREATER);
//        }


    }

    public String getAttribute(String currentBuffer){
        int i = 0;
        while (currentBuffer.charAt(i) != '=' && currentBuffer.charAt(i) != '>' && currentBuffer.charAt(i) != '<'){
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
        while (currentBuffer.charAt(i) != ';'){
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
