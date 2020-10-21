package com.example.wtfood.parser;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    // This class is parsing the token from user input. Create by Yen Kuo.

    MyTokenizer _tokenizer;

    public Parser(MyTokenizer tokenizer) {
        _tokenizer = tokenizer;
    }

    // The List store all query. Main Activity will base on this list's Query to show restaurant result.
    public List<Query> totalQuery = new ArrayList<>();

    // Query : Sentence ; Query | Sentence
    // Sentence : Attribute + Operator + Value
    // Attribute : price | rating | delivery

    /**
     * Parse the attribute and check whether it's right or not.
     * If it's correct, keep parsing next token.
     * If it's incorrect, add it as a Query, mark it's attribute, operator and value by * and stop parsing.
     */
    public void parseAttribute() {
        // Check whether has next token and Attribute is valid or not.
        // If it's valid attribute, store the Attribute type and keep going to parse next token.
        if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("price")) {
            _tokenizer.next();
            parseOperator("price");

        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("rating")) {
            _tokenizer.next();
            parseOperator("rating");
        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("delivery")) {
            _tokenizer.next();
            parseOperator("delivery");
        }
        // If there's no next token or attribute is invalid. Mark *.
        else if (_tokenizer.hasNext() && (!_tokenizer.current().getAttribute().equals("price") || !_tokenizer.current().getAttribute().equals("rating") || !_tokenizer.current().getAttribute().equals("delivery"))) {
            _tokenizer.next();
            totalQuery.add(new Query("*", "*", "*"));
        } else if (!_tokenizer.hasNext()) {
            totalQuery.add(new Query("*", "*", "*"));
        }
    }

    /**
     * Parse the operator and check whether it's right or not.
     * If it's correct, keep parsing next token.
     * If it's incorrect, add it as a Query, mark it's attribute, operator and value by * and stop parsing.
     *
     * @param Attribute String, The attribute we read and want to store in query.
     */
    // Operator : = | >= | <= | > | <
    public void parseOperator(String Attribute) {

        // A is relative attribute from last token.
        String A = Attribute;
        // Check whether has next token and operator is valid or not.
        // If it's valid operator, store the Attribute type and operator, and keep going to parse next token.
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
        } else if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("<=")) {
            _tokenizer.next();
            parseValue(A, "<=");
        }

        // If there's no next token or operator is invalid. Mark *.
        else if (_tokenizer.hasNext() && (!_tokenizer.current().getAttribute().equals(Token.Attribute.EQUAL) || !_tokenizer.current().getAttribute().equals(Token.Attribute.GREATER) || !_tokenizer.current().getAttribute().equals(Token.Attribute.LESS)
                || !_tokenizer.current().getAttribute().equals(Token.Attribute.GOE) || !_tokenizer.current().getAttribute().equals(Token.Attribute.LOE))) {
            totalQuery.add(new Query("*", "*", "*"));
        } else if (!_tokenizer.hasNext()) {
            totalQuery.add(new Query("*", "*", "*"));
        }
    }

    // Value : number | deliveryValue
    // DeliveryValue : Y | N

    /**
     * Parse the value and check whether it's right or not.
     * If it's correct, use the attribute and operator store from previous two parsing, and the value which just parsed in this function and add a new query using these information.
     * If it's incorrect, add it as a Query, mark it's attribute, operator and value by * and stop parsing.
     * Check whether have token after ;. If there's a token repeat all steps again.
     *
     * @param Attribute String, The attribute we read and want to store in query.
     * @param Operator  String, The operator we read and want to store in query.
     */
    public void parseValue(String Attribute, String Operator) {
        // Check the query for attribute price and rating.
        if (Attribute.equals("price") || Attribute.equals("rating")) {
            String value = _tokenizer.current().getToken();
            // The value must be number, or it will be an invalid query.
            if (_tokenizer.current().getAttribute().equals(Token.Attribute.VALUE)) {
                totalQuery.add(new Query(Attribute, Operator, value));
                _tokenizer.next();
                _tokenizer.next();
            } else {
                totalQuery.add(new Query("*", "*", "*"));
                _tokenizer.next();
                _tokenizer.next();
            }
        }
        // Check the query for attribute delivery.
        else if (Attribute.equals("delivery")) {
            String value = _tokenizer.current().getToken();
            // The value must be type deliveryValue which mean 'Y' or 'N', or it will an invalid query.
            if (_tokenizer.current().getAttribute().equals(Token.Attribute.DELIVERYValue)) {
                totalQuery.add(new Query(Attribute, Operator, value));
                _tokenizer.next();
                _tokenizer.next();
            } else {
                totalQuery.add(new Query("*", "*", "*"));
                _tokenizer.next();
                _tokenizer.next();
            }
        }
        // Any condition is invalid query.
        else {
            //String value = _tokenizer.current().getToken();
            totalQuery.add(new Query("*", "*", "*"));
            _tokenizer.next();
            _tokenizer.next();
        }

        // Repeat from parsing attribute again if there's a token after ; .
        if (_tokenizer.hasNext()) {
            parseAttribute();
        }
    }
}

