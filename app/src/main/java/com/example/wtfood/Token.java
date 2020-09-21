package com.example.wtfood;

public class Token {

    public enum Attribute {UNKNOWN, NAME, TYPE, PRICE, RATING, DELIVERY, LOCATION, VALUE, EQUAL, GREATER, LESS, GOE, LOE}
    private String token = "";
    private Attribute attribute = Attribute.UNKNOWN;

    public Token(String token, Attribute attribute){
        this.token = token;
        this.attribute = attribute;
    }

    public String getToken(){
        return this.token;
    }

    public Attribute getAttribute(){
        return this.attribute;
    }

}
