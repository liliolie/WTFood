package com.example.wtfood;

import com.example.wtfood.model.Location;
import com.example.wtfood.model.Restaurant;
import com.example.wtfood.model.Type;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RestaurantTest {

    @Test
    public void testGetters() {
        Restaurant r = new Restaurant(2, "abc", true, new Location(10.0, 10.0), Type.chinesefood, 50, "99 A St.", "01234");
        assertEquals(2, r.getRating());
        assertEquals("abc", r.getName());
        assertTrue(r.isDeliveryService());
        assertEquals(new Location(10.0, 10.0), r.getLocation());
        assertEquals(Type.chinesefood, r.getType());
        assertEquals(50, r.getPrice());
        assertEquals("99 A St.", r.getAddress());
        assertEquals("01234", r.getPhone());
    }

    @Test
    public void testSetters() {
        Restaurant r = new Restaurant("abc");
        r.setAddress("10 A St");
        assertEquals("10 A St", r.getAddress());
        r.setDeliveryService(false);
        assertFalse(r.isDeliveryService());
        r.setLocation(new Location(20.0, 10.0));
        assertEquals(new Location(20.0, 10.0), r.getLocation());
        r.setName("ABC");
        assertEquals("ABC", r.getName());
        r.setPrice(20);
        assertEquals(20, r.getPrice());
        r.setRating(2);
        assertEquals(2, r.getRating());
        r.setPhone("3213414");
        assertEquals("3213414", r.getPhone());
    }
}
