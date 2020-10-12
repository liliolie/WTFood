package com.example.wtfood;

import com.example.wtfood.fileprocess.FileProcess;
import com.example.wtfood.fileprocess.Restaurant;
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
        restaurants = new FileProcess().fileRead(new FileInputStream(new File("src/main/assets/list.json")));
        smallRestaurants = new FileProcess().fileRead(new FileInputStream(new File("src/main/assets/small_list.json")));
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
        assertEquals(priceTree.size(), 1000);
        priceTree.insert(restaurants.get(restaurants.size() - 1));
        assertEquals(priceTree.size(), 1000);

        for (Restaurant r : smallRestaurants) {
            smallPriceTree.insert(r);
        }
        assertEquals(smallPriceTree.size(), 10);

        for (Restaurant r : restaurants) {
            ratingTree.insert(r);
        }
        assertEquals(ratingTree.size(), 1000);

        for (Restaurant r : smallRestaurants) {
            smallRatingTree.insert(r);
        }
        assertEquals(smallRatingTree.size(), 10);
    }

    @Test
    public void testOrder() {
        for (Restaurant r : smallRestaurants) {
            smallPriceTree.insert(r);
        }

        assertEquals(smallPriceTree.priceInOrder(), "17 73 109 120 141 173 177 185 196 247");


        for (Restaurant r : smallRestaurants) {
            smallRatingTree.insert(r);
        }
        assertEquals(smallRatingTree.ratingInOrder(), "2 3 3 4 4 4 5 5 5 5");
    }

    @Test
    public void testSearchForNodes() {
        for (Restaurant r : smallRestaurants) {
            smallPriceTree.insert(r);
        }
        assertEquals(smallPriceTree.searchForNodes("<", 141).size(), 4);
        assertEquals(smallPriceTree.searchForNodes(">", 141).size(), 5);
        assertEquals(smallPriceTree.searchForNodes("=", 141).size(), 1);
        assertEquals(smallPriceTree.searchForNodes("<=", 141).size(), 5);
        assertEquals(smallPriceTree.searchForNodes(">=", 141).size(), 6);
    }
}
