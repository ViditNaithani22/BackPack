package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.estore.api.estoreapi.model.CartItem;
import com.fasterxml.jackson.databind.ObjectMapper;

class CartFileDaoTest {
    CartFileDAO cartFileDAO;
    CartItem[] testItems;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    void setupCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testItems = new CartItem[3];
        testItems[0] = new CartItem(1, 1, 1);
        testItems[1] = new CartItem(2, 2, 2);
        testItems[2] = new CartItem(3, 3, 3);

        when(mockObjectMapper
                .readValue(new File("samplecart.json"), CartItem[].class))
                .thenReturn(testItems);
        cartFileDAO = new CartFileDAO("samplecart.json", mockObjectMapper);
    }

    @Test
    void getCart() {
        CartItem[] items = assertDoesNotThrow(() -> cartFileDAO.getCart(), "Unexpected exception thrown");
        for (int i = 0; i < testItems.length; i++) {
            assertEquals(testItems[i], items[i]);
        }
    }

    @Test
    void getCartForUser() {
        CartItem[] items = assertDoesNotThrow(() -> cartFileDAO.getCartForUser(1), "Unexpected exception thrown");
        assertEquals(1, items.length);
    }

    @Test
    void getCartForNonExistingUser() {
        CartItem[] items = assertDoesNotThrow(() -> cartFileDAO.getCartForUser(4), "Unexpected exception thrown");
        assertEquals(0, items.length);
    }

    @Test
    void testAddToCartForExistingUser() {
        CartItem itemToAdd = new CartItem(1, 2, 1);
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.addToCart(itemToAdd), "Unexpected exception thrown");
        assertEquals(item, itemToAdd);
    }

    @Test
    void testAddToCartForNonExistingUser() {
        CartItem itemToAdd = new CartItem(4, 2, 1);
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.addToCart(itemToAdd), "Unexpected exception thrown");
        assertEquals(item, itemToAdd);
    }

    @Test
    void testGetProductInUserCart() {
        CartItem matchItem = testItems[0];
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.getProductInUserCart(1, 1), "Unexpected exception thrown");
        assertEquals(matchItem, item);
    }

    @Test
    void testGetProductNotInUserCart() {
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.getProductInUserCart(5, 4), "Unexpected exception thrown");
        assertNull(item);
    }

    @Test
    void testIncrease() {
        CartItem matchItem = new CartItem(1, 1, 2);
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.increase(1, 1, 5), "Unexpected exception thrown");
        assertEquals(matchItem, item);
    }

    @Test
    void testIncreaseWhenLimitExceeds() {
        CartItem matchItem = new CartItem(1, 1, 1);
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.increase(1, 1, 1), "Unexpected exception thrown");
        assertEquals(matchItem, item);
    }

    @Test
    void testIncreaseWhenProductDoesNotExist() {
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.increase(1, 4, 1), "Unexpected exception thrown");
        assertNull(item);
    }

    @Test
    void testDecrease() {
        CartItem matchItem = new CartItem(2, 2, 1);
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.decrease(2, 2), "Unexpected exception thrown");
        assertEquals(matchItem, item);
    }

    @Test
    void testDecreaseWhenQtyOne() {
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.decrease(1, 1), "Unexpected exception thrown");
        assertNull(item);
    }

    @Test
    void testDecreaseWhenProductDoesNotExist() {
        CartItem item = assertDoesNotThrow(() -> cartFileDAO.decrease(1, 4), "Unexpected exception thrown");
        assertNull(item);
    }

    @Test
    void testClearItemWhenCartExists() {
        boolean response = assertDoesNotThrow(() -> cartFileDAO.clearItem(1, 1), "Unexpected exception thrown");
        assertTrue(response);

    }

    @Test
    void testClearItemWhenCartDoesNotExist() {
        boolean response = assertDoesNotThrow(() -> cartFileDAO.clearItem(1, 5), "Unexpected exception thrown");
        assertFalse(response);

    }

    @Test
    void testClearCartWhenCartExists() {
        boolean response = assertDoesNotThrow(() -> cartFileDAO.clearUserCart(1), "Unexpected exception thrown");
        assertTrue(response);

    }

    @Test
    void testClearCartWhenCartDoesNotExist() {
        boolean response = assertDoesNotThrow(() -> cartFileDAO.clearUserCart(5), "Unexpected exception thrown");
        assertFalse(response);

    }

    @Test
    void testGetIdsForClearing() {
        Integer[] match = { 1 };
        Integer[] ids = assertDoesNotThrow(() -> cartFileDAO.getIdsForClearing(1), "Unexpected exception thrown");
        assertArrayEquals(match, ids);
    }

    @Test
    void testGetQuantityIfProductExists() {
        int expected = 1;
        int quantity = assertDoesNotThrow(() -> cartFileDAO.getQuantity(1, 1), "Unexpected exception thrown");
        assertEquals(expected, quantity);
    }

    @Test
    void testGetQuantityIfProductDoesNotExist() {
        int expected = 0;
        int quantity = assertDoesNotThrow(() -> cartFileDAO.getQuantity(1, 5), "Unexpected exception thrown");
        assertEquals(expected, quantity);
    }

}