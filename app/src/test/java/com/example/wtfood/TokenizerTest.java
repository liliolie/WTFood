package com.example.wtfood;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TokenizerTest {

    private static Tokenizer tokenizer;
    private static final String testCase = "Rating <= 2; Price > 50" ;
    private static final String passCase = "Price >= 10";

    @Test(timeout=1000)
    public void testPriceToken() {
        tokenizer = new MyTokenizer(passCase);

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.PRICE, tokenizer.current().getAttribute());

        //check the actual token value"
        assertEquals("wrong token value", "Price", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testEqualToken() {
        tokenizer = new MyTokenizer(passCase);

        //extract next token (just to skip first passCase token)
        tokenizer.next();

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.GOE, tokenizer.current().getAttribute());

        //check the actual token value
        assertEquals("wrong token value", ">=", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testValueToken() {
        tokenizer = new MyTokenizer(passCase);

        //extract next token (just to skip first passCase token)
        tokenizer.next();

        tokenizer.next();

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.VALUE, tokenizer.current().getAttribute());

        //check the actual token value
        assertEquals("wrong token value", "10", tokenizer.current().getToken());
    }
//    @Test(timeout=1000)
//    public void testEndToken() {
//        tokenizer = new MyTokenizer(passCase);
//
//        //extract next token (just to skip first passCase token)
//        tokenizer.next();
//        tokenizer.next();
//        tokenizer.next();
//
//        //check the type of the first token
//        assertEquals("wrong token type", Token.Attribute.END, tokenizer.current().getAttribute());
//
//        //check the actual token value
//        assertEquals("wrong token value", ";", tokenizer.current().getToken());
//    }

    @Test(timeout=1000)
    public void testFirstToken(){
        tokenizer = new MyTokenizer(testCase);

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.Rating, tokenizer.current().getAttribute());
        //check the actual token value
        assertEquals("wrong token value", "Rating", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenResult(){
        tokenizer = new MyTokenizer(testCase);

        // test first token (
        assertEquals(Token.Attribute.Rating, tokenizer.current().getAttribute());

        // test second token 100
        tokenizer.next();
        assertEquals(Token.Attribute.LOE, tokenizer.current().getAttribute());
        assertEquals("<=", tokenizer.current().getToken());

        // test third token +
        tokenizer.next();
        assertEquals(Token.Attribute.VALUE, tokenizer.current().getAttribute());
        assertEquals("2", tokenizer.current().getToken());

        // test fourth token 2
        tokenizer.next();
        assertEquals(Token.Attribute.END, tokenizer.current().getAttribute());
        assertEquals(";", tokenizer.current().getToken());

        // test fourth token -
        tokenizer.next();
        assertEquals(Token.Attribute.PRICE, tokenizer.current().getAttribute());
        assertEquals("Price", tokenizer.current().getToken());

        //we skip token 5
        tokenizer.next();
        assertEquals(Token.Attribute.GREATER, tokenizer.current().getAttribute());
        assertEquals(">", tokenizer.current().getToken());

        // test sixth token ), note that we call next twice.
        // Correct implementation of tokenizer.current() should return ')' this case (not 40)
        tokenizer.next();
        assertEquals(Token.Attribute.VALUE, tokenizer.current().getAttribute());
        assertEquals("50", tokenizer.current().getToken());
    }
}