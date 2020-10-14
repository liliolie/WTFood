package com.example.wtfood.parser;


public abstract class Tokenizer {

    public abstract boolean hasNext();

    public abstract Token current();

    public abstract void next();

}
