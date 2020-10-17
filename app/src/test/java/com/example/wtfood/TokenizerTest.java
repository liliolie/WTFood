package com.example.wtfood;

import com.example.wtfood.parser.MyTokenizer;
import com.example.wtfood.parser.Token;
import com.example.wtfood.parser.Tokenizer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TokenizerTest {

    private static Tokenizer tokenizer;
    private static final String twoCase = "rating <= 2; price > 50" ;
    private static final String oneCase = "price >= 10";
    private static final String threeCase = "delivery = Y; rating < 5; price <= 100";
    private static final String wrongCase = "pric >= abd";
    private static final String wrongOrderCase = "Y = 10";
    private static final String EndCase = "price = 10; rating = 2";

    @Test(timeout=1000)
    public void testWrongToken() {
        tokenizer = new MyTokenizer(wrongOrderCase);

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.DELIVERYValue, tokenizer.current().getAttribute());

        //check the actual token value"
        assertEquals("wrong token value", "Y", tokenizer.current().getToken());

        tokenizer.next();

        assertEquals("wrong token type", Token.Attribute.EQUAL, tokenizer.current().getAttribute());

        //check the actual token value"
        assertEquals("wrong token value", "=", tokenizer.current().getToken());

        tokenizer.next();

        assertEquals("wrong token type", Token.Attribute.VALUE, tokenizer.current().getAttribute());

        //check the actual token value"
        assertEquals("wrong token value", "10", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testWrongToken2() {
        tokenizer = new MyTokenizer(wrongCase);

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.UNKNOWN, tokenizer.current().getAttribute());

        //check the actual token value"
        assertEquals("wrong token value", "pric", tokenizer.current().getToken());

        tokenizer.next();

        assertEquals("wrong token type", Token.Attribute.GOE, tokenizer.current().getAttribute());

        //check the actual token value"
        assertEquals("wrong token value", ">=", tokenizer.current().getToken());

        tokenizer.next();

        assertEquals("wrong token type", Token.Attribute.UNKNOWN, tokenizer.current().getAttribute());

        //check the actual token value"
        assertEquals("wrong token value", "abd", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testPriceToken() {
        tokenizer = new MyTokenizer(oneCase);

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.PRICE, tokenizer.current().getAttribute());

        //check the actual token value"
        assertEquals("wrong token value", "price", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testEqualToken() {
        tokenizer = new MyTokenizer(oneCase);

        //extract next token (just to skip first passCase token)
        tokenizer.next();

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.GOE, tokenizer.current().getAttribute());

        //check the actual token value
        assertEquals("wrong token value", ">=", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testValueToken() {
        tokenizer = new MyTokenizer(oneCase);

        //extract next token (just to skip first passCase token)
        tokenizer.next();

        tokenizer.next();

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.VALUE, tokenizer.current().getAttribute());

        //check the actual token value
        assertEquals("wrong token value", "10", tokenizer.current().getToken());
    }
    @Test(timeout=1000)
    public void testEndToken() {
        tokenizer = new MyTokenizer(EndCase);

        //extract next token (just to skip three token)
        tokenizer.next();
        tokenizer.next();
        tokenizer.next();

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.END, tokenizer.current().getAttribute());

        //check the actual token value
        assertEquals("wrong token value", ";", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testFirstToken(){
        tokenizer = new MyTokenizer(twoCase);

        //check the type of the first token
        assertEquals("wrong token type", Token.Attribute.RATING, tokenizer.current().getAttribute());
        //check the actual token value
        assertEquals("wrong token value", "rating", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenResult(){
        tokenizer = new MyTokenizer(twoCase);

        // test first token (
        assertEquals(Token.Attribute.RATING, tokenizer.current().getAttribute());

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
        assertEquals("price", tokenizer.current().getToken());

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

    @Test(timeout=1000)
    public void testDelivery(){
        tokenizer = new MyTokenizer(threeCase);
        //Delivery = Y; Rating < 5; Price <= 100
        //Delivery
        assertEquals(Token.Attribute.DELIVERY, tokenizer.current().getAttribute());

        // =
        tokenizer.next();
        assertEquals(Token.Attribute.EQUAL, tokenizer.current().getAttribute());
        assertEquals("=", tokenizer.current().getToken());

        // Y
        tokenizer.next();
        assertEquals(Token.Attribute.DELIVERYValue, tokenizer.current().getAttribute());
        assertEquals("Y", tokenizer.current().getToken());

        //;
        tokenizer.next();
        assertEquals(Token.Attribute.END, tokenizer.current().getAttribute());
        assertEquals(";", tokenizer.current().getToken());

        // Rating
        tokenizer.next();
        assertEquals(Token.Attribute.RATING, tokenizer.current().getAttribute());
        assertEquals("rating", tokenizer.current().getToken());

        // <
        tokenizer.next();
        assertEquals(Token.Attribute.LESS, tokenizer.current().getAttribute());
        assertEquals("<", tokenizer.current().getToken());

        // 5
        tokenizer.next();
        assertEquals(Token.Attribute.VALUE, tokenizer.current().getAttribute());
        assertEquals("5", tokenizer.current().getToken());

        //;
        tokenizer.next();
        assertEquals(Token.Attribute.END, tokenizer.current().getAttribute());
        assertEquals(";", tokenizer.current().getToken());

        // Price
        tokenizer.next();
        assertEquals(Token.Attribute.PRICE, tokenizer.current().getAttribute());
        assertEquals("price", tokenizer.current().getToken());

        // <=
        tokenizer.next();
        assertEquals(Token.Attribute.LOE, tokenizer.current().getAttribute());
        assertEquals("<=", tokenizer.current().getToken());

        // 100
        tokenizer.next();
        assertEquals(Token.Attribute.VALUE, tokenizer.current().getAttribute());
        assertEquals("100", tokenizer.current().getToken());
    }
}