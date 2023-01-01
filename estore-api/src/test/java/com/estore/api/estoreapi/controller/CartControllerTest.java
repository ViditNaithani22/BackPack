package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.CartDAO;

@Tag("Controller-tier")
class CartControllerTest {
    private CartController cartController;
    private CartDAO mockCartDAO;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setupCartController() {
        mockCartDAO = mock(CartDAO.class);
        mockProductDAO = mock(ProductDAO.class);
        cartController = new CartController(mockCartDAO, mockProductDAO);
    }

    @Test
    void getAllCartItems() throws IOException {
        CartItem[] array = new CartItem[1];
        CartItem item = new CartItem(1, 2, 3);
        array[0] = item;
        when(mockCartDAO.getCart()).thenReturn(array);
        ResponseEntity<CartItem[]> response = cartController.getCart();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(array, response.getBody());
    }

    @Test
    void getCartForUser() throws IOException {
        CartItem[] array = new CartItem[1];
        CartItem item = new CartItem(1, 2, 3);
        array[0] = item;
        Product product = new Product(2, "Fishing rod", "Can be used for fishing", 35.0, 10, "fish",
                "http://www.google.com");

        when(mockCartDAO.getCartForUser(1)).thenReturn(array);
        when(mockProductDAO.getProduct(2)).thenReturn(product);
        ResponseEntity<Product[]> response = cartController.getCartForUser(1);
        product.setQuantity(3);
        Product[] productArray = new Product[] { product };

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(productArray, response.getBody());
    }

    @Test
    void testDecrease() throws IOException {
        CartItem[] array = new CartItem[1];
        CartItem item = new CartItem(1, 2, 2);
        array[0] = item;
        Product product = new Product(2, "Fishing rod", "Can be used for fishing", 35.0, 10, "fish",
                "http://www.google.com");

        when(mockCartDAO.decrease(2, 1)).thenReturn(item);
        when(mockProductDAO.getProduct(2)).thenReturn(product);
        ResponseEntity<Product> response = cartController.decrease(item);
        product.setQuantity(3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testIncrease() throws IOException {
        CartItem[] array = new CartItem[1];
        CartItem item = new CartItem(1, 2, 3);
        array[0] = item;
        Product product = new Product(2, "Fishing rod", "Can be used for fishing", 35.0, 10, "fish",
                "http://www.google.com");

        when(mockCartDAO.increase(2, 1, 10)).thenReturn(item);
        when(mockProductDAO.getProduct(2)).thenReturn(product);
        ResponseEntity<Product> response = cartController.increase(item);
        product.setQuantity(3);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testClearItem() throws IOException {
        CartItem[] array = new CartItem[0];
        CartItem item = new CartItem(1, 2, 3);

        Product product = new Product(2, "Fishing rod", "Can be used for fishing", 35.0, 10, "fish",
                "http://www.google.com");

        when(mockCartDAO.getCartForUser(1)).thenReturn(array);
        when(mockProductDAO.getProduct(2)).thenReturn(product);
        when(mockCartDAO.clearItem(2, 1)).thenReturn(true);
        ResponseEntity<Product[]> response = cartController.clearItem(item);
        product.setQuantity(3);
        Product[] productArray = new Product[0];

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(productArray, response.getBody());
    }

   @Test
   void testDeleteUserCart() throws IOException{
        when(mockCartDAO.clearUserCart(1)).thenReturn(true);
        ResponseEntity<Boolean> response= cartController.deleteUserCart(1);
        assertTrue(response.getBody());
   }

    @Test
    void testDeleteUserCartWithException() throws Exception {
        doThrow(new IOException()).when(mockCartDAO).clearUserCart(10);
        ResponseEntity<Boolean> response = cartController.deleteUserCart(10);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetCartWithException() throws IOException{
        doThrow(new IOException()).when(mockCartDAO).getCart();
        ResponseEntity<CartItem[]> response = cartController.getCart();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetCartForUserWithException() throws IOException{
        doThrow(new IOException()).when(mockCartDAO).getCartForUser(10);
        ResponseEntity<Product[]> response = cartController.getCartForUser(10);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDecreaseWhenCartItemIsRemoved() throws IOException{
        when(mockCartDAO.decrease(2, 1)).thenReturn(null);
        CartItem item = new CartItem(1, 2, 2);
        Product product = new Product(0, "", "", 0, 0, "", "");
        ResponseEntity<Product> response = cartController.decrease(item);
        assertEquals(product, response.getBody());
    }

    @Test
    void testDecreaseWithException() throws IOException{
        CartItem item = new CartItem(1, 2, 2);
        doThrow(new IOException()).when(mockCartDAO).decrease(2, 1);
        ResponseEntity<Product> response = cartController.decrease(item);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testIncreaseWithException() throws IOException{
        CartItem item = new CartItem(1, 2, 2);
        doThrow(new IOException()).when(mockCartDAO).increase(2, 1,1);
        doThrow(new IOException()).when(mockProductDAO).getProduct(2);
        ResponseEntity<Product> response = cartController.increase(item);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testClearWithException() throws IOException{
        CartItem item = new CartItem(1, 2, 2);
        doThrow(new IOException()).when(mockCartDAO).clearItem(2, 1);
        ResponseEntity<Product[]> response = cartController.clearItem(item);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddToCart() throws IOException{
        CartItem item = new CartItem(1, 2, 2);
        Product product2 = new Product(2, "Product2", "Description2", 10, 10, "giehige", "http://www.google.com");
        when(mockCartDAO.getProductInUserCart(2, 1)).thenReturn(null);
        when(mockCartDAO.addToCart(item)).thenReturn(item);
        when(mockProductDAO.getProduct(2)).thenReturn(product2);
        ResponseEntity<Product> response = cartController.addToCart(item);
        assertEquals(product2, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAddToCartWhenItemExists() throws IOException{
        CartItem item = new CartItem(1, 2, 2);
        Product product2 = new Product(2, "Product2", "Description2", 10, 10, "giehige", "http://www.google.com");
        when(mockCartDAO.getProductInUserCart(2, 1)).thenReturn(item);
        when(mockCartDAO.increase(2,1,10)).thenReturn(item);
        when(mockProductDAO.getProduct(2)).thenReturn(product2);
        ResponseEntity<Product> response = cartController.addToCart(item);
        assertEquals(product2, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testAddToCartWithException() throws IOException{
        CartItem item = new CartItem(1, 2, 2);
        when(mockCartDAO.getProductInUserCart(2, 1)).thenReturn(null);
        doThrow(new IOException()).when(mockCartDAO).addToCart(item);
        ResponseEntity<Product> response = cartController.addToCart(item);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCheckout() throws IOException{
        Integer[] productIds = {2};
        when(mockCartDAO.getIdsForClearing(1)).thenReturn(productIds);
        Product product2 = new Product(2, "Product2", "Description2", 10, 10, "giehige", "http://www.google.com");
        when(mockProductDAO.getProduct(2)).thenReturn(product2);
        when(mockCartDAO.clearUserCart(1)).thenReturn(true);
        ResponseEntity<Boolean> response = cartController.checkout(1);
        assertTrue(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCheckoutWhenFalse() throws IOException{
        Integer[] productIds = {2};
        when(mockCartDAO.getIdsForClearing(1)).thenReturn(productIds);
        Product product2 = new Product(2, "Product2", "Description2", 10, 10, "giehige", "http://www.google.com");
        when(mockProductDAO.getProduct(2)).thenReturn(product2);
        when(mockCartDAO.clearUserCart(1)).thenReturn(false);
        ResponseEntity<Boolean> response = cartController.checkout(1);
        assertFalse(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCheckoutWithException() throws IOException{
        Integer[] productIds = {2};
        when(mockCartDAO.getIdsForClearing(1)).thenReturn(productIds);
        Product product2 = new Product(2, "Product2", "Description2", 10, 10, "giehige", "http://www.google.com");
        when(mockProductDAO.getProduct(2)).thenReturn(product2);
        when(mockCartDAO.clearUserCart(1)).thenReturn(true);
        doThrow(new IOException()).when(mockCartDAO).clearUserCart(1);
        ResponseEntity<Boolean> response = cartController.checkout(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}