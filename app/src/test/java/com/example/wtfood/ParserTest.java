package com.example.wtfood;

import org.junit.Test;

import com.example.wtfood.parser.MyTokenizer;
import com.example.wtfood.parser.Parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ParserTest {


    private static final String[] testExample = new String[]{"price >= 10", "rating <= 3", "delivery = Y"};
    private static final String testExample2 = "price > 100; rating < 2; delivery = N";
    private static final String testWrongOrderCase = "Y = Delivery";
    private static final String[] testWrongCase = new String[]{"Name = ABC", "Price : 5", "Rating = A", "Pr; Rating <= 4"};
    private static final String[] testSpecialCase = new String[]{"; rating = 5", "price > 100, rating = 2"};
    private static final String emptyCase = "    ";

    @Test(timeout = 1000)
    public void testOneRequirement() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample[0]);
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();
        assertEquals("price>=10", p.totalQuery.get(0).toString());

        MyTokenizer queryTokenizer1 = new MyTokenizer(testExample[1]);
        Parser p1 = new Parser(queryTokenizer1);
        p1.parseAttribute();
        assertEquals("rating<=3", p1.totalQuery.get(0).toString());

        MyTokenizer queryTokenizer2 = new MyTokenizer(testExample[2]);
        Parser p2 = new Parser(queryTokenizer2);
        p2.parseAttribute();
        assertEquals("delivery=y", p2.totalQuery.get(0).toString());
    }


    @Test(timeout = 1000)
    public void testMultiRequirements() {
        MyTokenizer queryTokenizer = new MyTokenizer(testExample2);
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();
        try {
            assertEquals("Incorrect item", "[price>100, rating<2, delivery=n]", p.totalQuery.toString());
            assertEquals("Incorrect size", 3, p.totalQuery.size());
            assertEquals("incorrect display format", "price>100", p.totalQuery.get(0).toString());
            assertEquals("incorrect display format", "rating<2", p.totalQuery.get(1).toString());
            assertEquals("incorrect display format", "delivery=n", p.totalQuery.get(2).toString());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test(timeout = 1000)
    public void wrongOrderCase() {
        MyTokenizer queryTokenizer = new MyTokenizer(testWrongOrderCase);
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();
        assertEquals("***", p.totalQuery.get(0).toString());
    }

    @Test(timeout = 1000)
    public void testEmptyCase() {
        MyTokenizer queryTokenizer = new MyTokenizer(emptyCase);
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();

        assertEquals("***", p.totalQuery.get(0).toString());
    }

    @Test(timeout = 1000)
    public void testWrongCase() {
        MyTokenizer queryTokenizer = new MyTokenizer(testWrongCase[0]);
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();

        assertEquals("***", p.totalQuery.get(0).toString());


        MyTokenizer queryTokenizer1 = new MyTokenizer(testWrongCase[1]);
        Parser p1 = new Parser(queryTokenizer1);
        p1.parseAttribute();

        assertEquals("***", p1.totalQuery.get(0).toString());

        MyTokenizer queryTokenizer2 = new MyTokenizer(testWrongCase[2]);
        Parser p2 = new Parser(queryTokenizer2);
        p2.parseAttribute();

        assertEquals("***", p2.totalQuery.get(0).toString());


        MyTokenizer queryTokenizer3 = new MyTokenizer(testWrongCase[3]);
        Parser p3 = new Parser(queryTokenizer3);
        p3.parseAttribute();

        assertEquals("***", p3.totalQuery.get(0).toString());
        assertEquals("rating<=4", p3.totalQuery.get(1).toString());

    }

    @Test(timeout = 1000)
    public void testSpecialCase() {
        MyTokenizer queryTokenizer = new MyTokenizer(testSpecialCase[0]);
        Parser p = new Parser(queryTokenizer);
        p.parseAttribute();

        assertEquals("***", p.totalQuery.get(0).toString());
        assertEquals("rating=5", p.totalQuery.get(1).toString());


        MyTokenizer queryTokenizer1 = new MyTokenizer(testSpecialCase[1]);
        Parser p1 = new Parser(queryTokenizer1);
        p1.parseAttribute();


        assertEquals("price>100", p1.totalQuery.get(0).toString());
        assertEquals("rating=2", p1.totalQuery.get(1).toString());

        MyTokenizer queryTokenizer2 = new MyTokenizer(testWrongCase[2]);
        Parser p2 = new Parser(queryTokenizer2);
        p2.parseAttribute();

        assertEquals("***", p2.totalQuery.get(0).toString());


        MyTokenizer queryTokenizer3 = new MyTokenizer(testWrongCase[3]);
        Parser p3 = new Parser(queryTokenizer3);
        p3.parseAttribute();


        assertEquals("***", p3.totalQuery.get(0).toString());
        assertEquals("rating<=4", p3.totalQuery.get(1).toString());

    }


}
