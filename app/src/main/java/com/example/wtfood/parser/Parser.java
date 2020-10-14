package com.example.wtfood.parser;

import java.util.ArrayList;

public class Parser {

    MyTokenizer _tokenizer;
    public Query q = new Query("", "", "");

    public Parser(MyTokenizer tokenizer) {
        _tokenizer = tokenizer;
    }

    public static ArrayList<Query> totalQuery = new ArrayList<>();

    public int count = 0;


    public Query parseAttribute() {

        if (count == 0){
            totalQuery.clear();
        }

        count++;
        if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("price")) {
            _tokenizer.next();
            q.setCompareAttribute("price");
            return parseOperator();

        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("rating")) {
            _tokenizer.next();
            q.setCompareAttribute("rating");
            return parseOperator();
        } else {
            _tokenizer.next();
            q.setCompareAttribute("delivery");
            return parseOperator();
        }

    }

    public Query parseOperator() {

        if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("=")) {
            _tokenizer.next();
            q.setSign("=");
            return parseValue();
        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("<")) {
            _tokenizer.next();
            q.setSign("<");
            return parseValue();
        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals(">")) {
            _tokenizer.next();
            q.setSign(">");
            return parseValue();
        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals(">=")) {
            _tokenizer.next();
            q.setSign(">=");
            return parseValue();
        } else {
            _tokenizer.next();
            q.setSign("<=");
            return parseValue();
        }

    }


    public Query parseValue() {
        if (q.CompareAttribute.equals("price") || q.CompareAttribute.equals("rating")) {
            String value = _tokenizer.current().getToken();
            q.setValue(value);
            _tokenizer.next();
        }
        if (q.CompareAttribute.equals("delivery")) {
            String value = _tokenizer.current().getToken();
            q.setValue(value);
            _tokenizer.next();
        }

        totalQuery.add(new Query(q.CompareAttribute, q.sign, q.Value));

        if (!_tokenizer.hasNext()) {
            count = 0;
            return q;
        } else {
            _tokenizer.next();
            return parseEnd();
        }
    }


    public Query parseEnd() {
        if (_tokenizer.hasNext()) {
            return parseAttribute();
        } else {
            return q;
        }
    }
}

