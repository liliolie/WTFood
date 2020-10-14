package com.example.wtfood;

import org.junit.Test;

import com.example.wtfood.parser.MyTokenizer;
import com.example.wtfood.parser.Parser;
import com.example.wtfood.parser.Query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ParserTest {

    private static MyTokenizer tokenizer;


    private static final String[] testExample = new String[]{"price = 10", "rating <= 3", "delivery = Y"};
    private static final String testExample2 = "price >= 100; rating <= 2; delivery = N";

    @Test(timeout=1000)
    public void testOnePrice() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample[0]);
        Query t1 = new Parser(queryTokenizer).parseAttribute();

        assertEquals("price=10", t1.toString());
    }
    @Test(timeout=1000)
    public void testOneRating() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample[1]);
        Query t1 = new Parser(queryTokenizer).parseAttribute();
        assertEquals("rating<=3", t1.toString());
    }
    @Test(timeout=1000)
    public void testOneDelivery() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample[2]);
        Query t1 = new Parser(queryTokenizer).parseAttribute();
        assertEquals("delivery=Y", t1.toString());
    }

    @Test(timeout=1000)
    public void testMultiCondition() {
        tokenizer = new MyTokenizer(testExample2);
        try{
            Query q = new Parser(tokenizer).parseAttribute();
            assertEquals("Incorrect item", "delivery=N", q.toString());
            assertEquals("Incorrect size", 3, Parser.totalQuery.size());
            assertEquals("incorrect display format", "price>=100", Parser.totalQuery.get(0).toString());
            assertEquals("incorrect display format", "rating<=2", Parser.totalQuery.get(1).toString());
            assertEquals("incorrect display format", "delivery=N", Parser.totalQuery.get(2).toString());

        }catch (Exception e){
            fail(e.getMessage());
        }
    }


}
