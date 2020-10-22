package com.example.wtfood;

import com.example.wtfood.parser.MyTokenizer;
import com.example.wtfood.parser.Token;
import com.example.wtfood.parser.Tokenizer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TokenizerTest {

    private static Tokenizer tokenizer;
    private static final String threeCase = "delivery = Y; rating < 5; price <= 100";
    private static final String wrongCase1 = "pric >= abd";
    private static final String wrongCase2 = "price = 10, rating = 5";
    private static final String wrongOrderCase = "N > Delivery";
    private static final String emptyCase = "    ";
    private static final String hasNext = "price =< 100";


    @Test(timeout=1000)
    public void testHasNext() {
        tokenizer = new MyTokenizer(hasNext);

        assertTrue(tokenizer.hasNext());

        tokenizer.next();
        tokenizer.next();
        tokenizer.next();
        assertFalse(tokenizer.hasNext());
    }

    @Test(timeout=1000)
    public void testWrongToken1() {
        tokenizer = new MyTokenizer(wrongCase1);

        // pric
        assertEquals("wrong token type", Token.Attribute.UNKNOWN, tokenizer.current().getAttribute());
        assertEquals("wrong token value", "pric", tokenizer.current().getToken());

        tokenizer.next();
        // >=
        assertEquals("wrong token type", Token.Attribute.GOE, tokenizer.current().getAttribute());
        assertEquals("wrong token value", ">=", tokenizer.current().getToken());

        tokenizer.next();
        // abd
        assertEquals("wrong token type", Token.Attribute.UNKNOWN, tokenizer.current().getAttribute());
        assertEquals("wrong token value", "abd", tokenizer.current().getToken());

        tokenizer = new MyTokenizer(wrongCase2);

        // price
        assertEquals("wrong token type", Token.Attribute.PRICE, tokenizer.current().getAttribute());
        assertEquals("wrong token value", "price", tokenizer.current().getToken());

        tokenizer.next();
        // =
        assertEquals("wrong token type", Token.Attribute.EQUAL, tokenizer.current().getAttribute());
        assertEquals("wrong token value", "=", tokenizer.current().getToken());

        tokenizer.next();
        // 10
        assertEquals("wrong token type", Token.Attribute.VALUE, tokenizer.current().getAttribute());
        assertEquals("wrong token value", "10", tokenizer.current().getToken());

        tokenizer.next();
        // ,
        assertEquals("wrong token type", Token.Attribute.UNKNOWN, tokenizer.current().getAttribute());
        assertEquals("wrong token value", ",", tokenizer.current().getToken());

        tokenizer.next();
        // rating
        assertEquals("wrong token type", Token.Attribute.RATING, tokenizer.current().getAttribute());
        assertEquals("wrong token value", "rating", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void emptyCase() {
        tokenizer = new MyTokenizer(emptyCase);

        // empty
        assertNull(tokenizer.current());

    }

    @Test(timeout=1000)
    public void testWrongOrder() {
        tokenizer = new MyTokenizer(wrongOrderCase);

        // N
        assertEquals("wrong token type", Token.Attribute.DELIVERYValue, tokenizer.current().getAttribute());
        assertEquals("wrong token value", "n", tokenizer.current().getToken());

        tokenizer.next();
        // >
        assertEquals("wrong token type", Token.Attribute.GREATER, tokenizer.current().getAttribute());
        assertEquals("wrong token value", ">", tokenizer.current().getToken());

        tokenizer.next();
        // delivery
        assertEquals("wrong token type", Token.Attribute.DELIVERY, tokenizer.current().getAttribute());
        assertEquals("wrong token value", "delivery", tokenizer.current().getToken());
    }



    @Test(timeout=1000)
    public void testMultipleRequirement(){
        tokenizer = new MyTokenizer(threeCase);
        //Delivery = Y; Rating < 5; Price <= 100
        //Delivery
        assertEquals(Token.Attribute.DELIVERY, tokenizer.current().getAttribute());
        assertEquals("delivery", tokenizer.current().getToken());

        // =
        tokenizer.next();
        assertEquals(Token.Attribute.EQUAL, tokenizer.current().getAttribute());
        assertEquals("=", tokenizer.current().getToken());

        // Y
        tokenizer.next();
        assertEquals(Token.Attribute.DELIVERYValue, tokenizer.current().getAttribute());
        assertEquals("y", tokenizer.current().getToken());

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