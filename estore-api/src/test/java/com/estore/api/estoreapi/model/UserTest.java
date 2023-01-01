package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@Tag("Model-tier")
class UserTest {

    @Test
    void createUser() {
        User user = new User(1, "Regina", true);
        assertEquals(1, user.getId());
        assertEquals("Regina", user.getUsername());
        assertEquals(true, user.getIsAdmin());
    }

    @Test
    void testSetAdmin(){
        User user = new User(1, "Regina", true);
        assertTrue(user.getIsAdmin());
        user.setAdmin(false);
        assertFalse(user.getIsAdmin());
    }

    @Test
    void testhashCode(){
        User user = new User(1, "Regina", true);
        assertEquals(1, user.hashCode());
    }
   
    @Test
    void testEqualsNull(){
        User user = new User(1, "Regina", true);
        assertNotEquals(null, user);  
    }

    @Test
    void testEqualsDifferentObjects(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        User user = new User(1, "user", false);
        assertNotEquals(user, product);
    }

    @Test
    void testEqualsProductWithDifferentId(){
        User user = new User(1, "Regina", true);
        User user2 = new User(2, "Regina", true);
        assertNotEquals(user, user2);
    }

    @Test
    void testEqualsWithSameId(){
        User user = new User(1, "Regina", true);
        User user2 = new User(1, "Regina", true);
        assertEquals(user, user2);
    }
}
