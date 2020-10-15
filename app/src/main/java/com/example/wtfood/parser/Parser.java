package com.example.wtfood.parser;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    MyTokenizer _tokenizer;

    public Parser(MyTokenizer tokenizer) {
        _tokenizer = tokenizer;
    }

    public List<Query> totalQuery = new ArrayList<>();



    public void parseAttribute() {
        System.out.println("Hi");

        if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("price")) {
            _tokenizer.next();
            parseOperator("price");

        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("rating")) {
            _tokenizer.next();
            parseOperator("rating");
        } else {
            _tokenizer.next();

            parseOperator("delivery");
        }

    }

    public void parseOperator(String Attribute) {
        System.out.println("Hi");
        String A = Attribute;
        if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("=")) {
            _tokenizer.next();
            parseValue(A, "=");
        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("<")) {
            _tokenizer.next();
            parseValue(A, "<");
        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals(">")) {
            _tokenizer.next();
            parseValue(A, ">");
        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals(">=")) {
            _tokenizer.next();
            parseValue(A, ">=");
        } else {
            _tokenizer.next();
            parseValue(A, "<=");
        }

    }


    public void parseValue(String Attribute, String Operator) {
        System.out.println("Hi");
        if (Attribute.equals("price") || Attribute.equals("rating")) {
            String value = _tokenizer.current().getToken();
            totalQuery.add(new Query(Attribute,Operator,value));
            _tokenizer.next();
            _tokenizer.next();
        }
        if (Attribute.equals("delivery")) {
            String value = _tokenizer.current().getToken();
            totalQuery.add(new Query(Attribute,Operator,value));
            _tokenizer.next();
            _tokenizer.next();
        }


        if (_tokenizer.hasNext()) {
            parseAttribute();
        }
    }
}

