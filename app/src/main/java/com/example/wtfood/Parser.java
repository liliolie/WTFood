package com.example.wtfood;

public class Parser {

    MyTokenizer _tokenizer;
    public Query q = new Query("", "", 0);

    public Parser(MyTokenizer tokenizer) {
        _tokenizer = tokenizer;
    }


    public Query parseAttribute() {
        if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("Price")) {
            _tokenizer.next();
            q.setCompareAttribute("Price");
            System.out.println(q.toString());
            return new Parser(_tokenizer).parseOperator();
        }
        return q;
    }

    public Query parseOperator() {

        if (_tokenizer.hasNext() && _tokenizer.current().getToken().equals("=")) {
            _tokenizer.next();
            q.setSign("=");
            System.out.println(q.toString());
            return new Parser(_tokenizer).parseValue();
        }
        return q;
    }

    public Query parseValue() {
        if (_tokenizer.hasNext()) {
            return new Parser(_tokenizer).parseAttribute();
        } else {
            return q;
        }
    }

//    public String parseEnd(){
//        if(_tokenizer.hasNext() && _tokenizer.current().getToken().equals("Price")){
//            _tokenizer.next();
//        }
//        return parseSign();
//    }
}
