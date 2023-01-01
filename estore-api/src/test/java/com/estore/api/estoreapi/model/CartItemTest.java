package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
class CartItemTest {
CartItem cartItem = new CartItem(1, 1, 1);
    @Test
    void createProduct(){
        CartItem cartItem = new CartItem(1, 2, 3);
        assertEquals(2, cartItem.getProductId());
        assertEquals(1, cartItem.getUserId());
        assertEquals(3, cartItem.getQuantity());
    }

    @Test
    void equals(){
        CartItem cartItemOriginal = new CartItem(1, 1, 1);
        CartItem cartSame = new CartItem(1, 1, 1);
        CartItem cartNewDiff = new CartItem(2, 2, 2);
        assertNotEquals(cartSame, cartNewDiff);
    }

    @Test
    void testToString(){
        int userId = 1;
        int productId = 2;
        int quantity = 3;
        CartItem cartItem = new CartItem(userId, productId, quantity);
        assertEquals("CartItem [userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + "]", cartItem.toString());
    }


    @Test
    void setUserId() {
        cartItem.setUserId(15);
        assertEquals(15, cartItem.getUserId());
    }

    @Test
    void setProductId() {
        cartItem.setProductId(15);
        assertEquals(15, cartItem.getProductId());
    }

    @Test
    void setQuantity() {
        cartItem.setQuantity(2);
        assertEquals(2, cartItem.getQuantity());
    }


    @Test
    void testEqualsNull(){
        CartItem cartItem = new CartItem(1, 2, 3);
        assertNotEquals(null, cartItem);   
    }

    @Test
    void testEqualsDifferentObjects(){
        CartItem cartItem = new CartItem(1, 2, 3);
        User user = new User(1, "user", false);
        assertNotEquals(cartItem, user);   
    }

    @Test
    void testEqualsProductWithDifferentId(){
        CartItem cartItem = new CartItem(1, 2, 3);        
        CartItem cartItem2 = new CartItem(2, 2, 3); 
        assertNotEquals(cartItem, cartItem2);
    }

    @Test
    void testEqualsWithSameId(){
        CartItem cartItem = new CartItem(1, 2, 3);        
        CartItem cartItem2 = new CartItem(1, 2, 3); 
        assertEquals(cartItem, cartItem2);
    }


}