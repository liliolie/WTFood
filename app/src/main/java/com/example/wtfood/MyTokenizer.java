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


    }

    public String getAttribute(String currentBuffer){
        int i = 0;
        while (currentBuffer.charAt(i) != '=' && currentBuffer.charAt(i) != '>' && currentBuffer.charAt(i) != '<'){
            i++;
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
