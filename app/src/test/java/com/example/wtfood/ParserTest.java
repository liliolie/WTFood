package com.example.wtfood;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ParserTest {

    private static MyTokenizer tokenizer;


    private static final String[] testExample = new String[]{"Price = 10", "Rating <= 3", "Delivery = Y"};
    private static final String testExample2 = "Price = 8; Rating <= 2; Delivery = N";

    @Test(timeout=1000)
    public void testOnePrice() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample[0]);
        Query t1 = new Parser(queryTokenizer).parseAttribute();

        assertEquals("Price=10", t1.toString());
    }
    @Test(timeout=1000)
    public void testOneRating() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample[1]);
        Query t1 = new Parser(queryTokenizer).parseAttribute();
        assertEquals("Rating<=3", t1.toString());
    }
    @Test(timeout=1000)
    public void testOneDelivery() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample[2]);
        Query t1 = new Parser(queryTokenizer).parseAttribute();
        assertEquals("Delivery=Y", t1.toString());
    }

    @Test(timeout=1000)
    public void testMultiCondition() {
        tokenizer = new MyTokenizer(testExample2);
        try{
            Query q = new Parser(tokenizer).parseAttribute();
            assertEquals("Incorrect item", "Delivery=N", q.toString());
            assertEquals("Incorrect size", 3, Parser.totalQuery.size());
            assertEquals("incorrect display format", "Price=8", Parser.totalQuery.get(0).toString());
            assertEquals("incorrect display format", "Rating<=2", Parser.totalQuery.get(1).toString());
            assertEquals("incorrect display format", "Delivery=N", Parser.totalQuery.get(2).toString());

        }catch (Exception e){
            fail(e.getMessage());
        }
    }


}
