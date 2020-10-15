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
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();

        assertEquals("price=10", p.totalQuery.get(0).toString());
    }
    @Test(timeout=1000)
    public void testOneRating() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample[1]);
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();
        assertEquals("rating<=3", p.totalQuery.get(0).toString());
    }
    @Test(timeout=1000)
    public void testOneDelivery() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample[2]);
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();
        assertEquals("delivery=Y", p.totalQuery.get(0).toString());
    }

    @Test(timeout=1000)
    public void testMultiCondition() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample2);
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();
        try{
            assertEquals("Incorrect item", "[price>=100, rating<=2, delivery=N]", p.totalQuery.toString());
            assertEquals("Incorrect size", 3, p.totalQuery.size());
            assertEquals("incorrect display format", "price>=100", p.totalQuery.get(0).toString());
            assertEquals("incorrect display format", "rating<=2", p.totalQuery.get(1).toString());
            assertEquals("incorrect display format", "delivery=N", p.totalQuery.get(2).toString());

        }catch (Exception e){
            fail(e.getMessage());
        }
    }


}
