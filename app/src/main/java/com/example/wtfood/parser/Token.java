package com.example.wtfood.parser;


public class Token {

    public enum Attribute {UNKNOWN, PRICE, DELIVERY, RATING, VALUE, EQUAL, GREATER, LESS, GOE, LOE, END, DELIVERYValue}

    private String _token = "";
    private Attribute _attribute = Attribute.UNKNOWN;

    public Token(String token, Attribute attribute) {
        _token = token;
        _attribute = attribute;
    }

    public String getToken() {
        return _token;
    }

    public Attribute getAttribute() {
        return _attribute;
    }

}
