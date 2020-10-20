package com.example.wtfood;

import com.example.wtfood.fileprocess.FileProcess;
import com.example.wtfood.model.Restaurant;
import com.example.wtfood.rbtree.RBTree;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class RBTreeTest {

    private static List<Restaurant> restaurants;
    private static List<Restaurant> smallRestaurants;
    RBTree priceTree;
    RBTree smallPriceTree;
    RBTree ratingTree;
    RBTree smallRatingTree;

    @BeforeClass
    public static void readFile() throws IOException {
        FileProcess fileProcess = new FileProcess();
        restaurants = fileProcess.jsonFileRead(new FileInputStream(new File("src/main/assets/list.json")));
        restaurants.addAll(fileProcess.csvFileRead(new FileInputStream(new File("src/main/assets/list.csv"))));
        smallRestaurants = fileProcess.jsonFileRead(new FileInputStream(new File("src/main/assets/small_list.json")));
        assertEquals(restaurants.size(), 1000);
        assertEquals(smallRestaurants.size(), 10);
    }

    @Before
    public void setup() {
        priceTree = new RBTree("price");
        smallPriceTree = new RBTree("price");
        ratingTree = new RBTree("rating");
        smallRatingTree = new RBTree("rating");
    }

    @Test
    public void testInsert() {
        for (Restaurant r : restaurants) {
            priceTree.insert(r);
        }
        assertEquals(1000, priceTree.size());
        priceTree.insert(restaurants.get(restaurants.size() - 1));
        assertEquals(1000, priceTree.size());

        for (Restaurant r : smallRestaurants) {
            smallPriceTree.insert(r);
        }
        assertEquals(10, smallPriceTree.size());

        for (Restaurant r : restaurants) {
            ratingTree.insert(r);
        }
        assertEquals(1000, ratingTree.size());

        for (Restaurant r : smallRestaurants) {
            smallRatingTree.insert(r);
        }
        assertEquals(10, smallRatingTree.size());
    }

    @Test
    public void testOrder() {
        for (Restaurant r : smallRestaurants) {
            smallPriceTree.insert(r);
        }

        assertEquals("17 73 109 120 141 173 177 185 196 247", smallPriceTree.priceInOrder());
        assertEquals("141 73 17 120 109 196 177 173 185 247", smallPriceTree.pricePreOrder());


        for (Restaurant r : smallRestaurants) {
            smallRatingTree.insert(r);
        }
        assertEquals("2 3 3 4 4 4 5 5 5 5", smallRatingTree.ratingInOrder());
        assertEquals("5 3 3 2 4 4 4 5 5 5", smallRatingTree.ratingPreOrder());
    }

    @Test
    public void testSearchForNodes() {
        for (Restaurant r : smallRestaurants) {
            smallPriceTree.insert(r);
        }
        assertEquals(4, smallPriceTree.searchForNodes("<", 141).size());
        assertEquals(5, smallPriceTree.searchForNodes(">", 141).size());
        assertEquals(1, smallPriceTree.searchForNodes("=", 141).size());
        assertEquals(5, smallPriceTree.searchForNodes("<=", 141).size());
        assertEquals(6, smallPriceTree.searchForNodes(">=", 141).size());
        assertEquals(0, smallPriceTree.searchForNodes("<", 0).size());
    }
}
