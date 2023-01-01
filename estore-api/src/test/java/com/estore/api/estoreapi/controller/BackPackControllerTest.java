package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.BackPack;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.BackPackDAO;
import com.estore.api.estoreapi.persistence.ProductDAO;

@Tag("Controller-tier")
class BackPackControllerTest {

    private BackPackController backpackController;
    private BackPackDAO mockBackPackDAO;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, create a new UserController object and inject
     * a mock BackPack DAO
     */

    @BeforeEach
    void setupBackPackController() {
        mockBackPackDAO = mock(BackPackDAO.class);
        mockProductDAO = mock(ProductDAO.class);
        backpackController = new BackPackController(mockBackPackDAO, mockProductDAO);
    }

    @Test
    void getBackPack() throws IOException {
        int[] products = new int[] { 2, 3, 4 };
        BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products);

        when(mockBackPackDAO.getBackPack(backpack.getId())).thenReturn(backpack);
        ResponseEntity<BackPack> response = backpackController.getBackPack(backpack.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(backpack, response.getBody());
    }

    @Test
    void getBackPackNotFound() throws Exception {
        int backpackId = 99;
        when(mockBackPackDAO.getBackPack(backpackId)).thenReturn(null);
        ResponseEntity<BackPack> response = backpackController.getBackPack(backpackId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getBackPackWithException() throws Exception {
        int backpackId = 99;
        doThrow(new IOException()).when(mockBackPackDAO).getBackPack(backpackId);
        ResponseEntity<BackPack> response = backpackController.getBackPack(backpackId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void createBackPackSuccessfully() throws IOException {
        int[] products = new int[] { 2, 3, 4 };
        BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products);
        when(mockBackPackDAO.createBackPack(backpack)).thenReturn(backpack);
        when(mockBackPackDAO.findBackPacks("Lake Onterio fishing")).thenReturn(new BackPack[] {});
        ResponseEntity<BackPack> response = backpackController.createBackPack(backpack);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(backpack, response.getBody());
    }

    @Test
    void createUserFail() throws IOException {
        int[] products = new int[] { 2, 3, 4 };
        BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products);
        when(mockBackPackDAO.createBackPack(backpack)).thenReturn(null);
        BackPack[] matching = { backpack };
        when(mockBackPackDAO.findBackPacks("Lake Onterio fishing")).thenReturn(matching);
        ResponseEntity<BackPack> response = backpackController.createBackPack(backpack);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void createUserWithException() throws IOException {
        int[] products = new int[] { 2, 3, 4 };
        BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products);
        doThrow(new IOException()).when(mockBackPackDAO).createBackPack(backpack);
        BackPack[] matching = new BackPack[0];
        when(mockBackPackDAO.findBackPacks("Lake Onterio fishing")).thenReturn(matching);
        ResponseEntity<BackPack> response = backpackController.createBackPack(backpack);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updateBackPackSuccessfully() throws IOException {
        int[] products = new int[] { 2, 3, 4 };
        BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products);
        when(mockBackPackDAO.updateBackPack(backpack)).thenReturn(backpack);
        ResponseEntity<BackPack> response = backpackController.updateBackPack(backpack);
        backpack.setName("Onterio lake fishing");
        response = backpackController.updateBackPack(backpack);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(backpack, response.getBody());
    }

    @Test
    void updateBackPackFailed() throws IOException {
        int[] products = new int[] { 2, 3, 4 };
        BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products);
        when(mockBackPackDAO.updateBackPack(backpack)).thenReturn(null);
        ResponseEntity<BackPack> response = backpackController.updateBackPack(backpack);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateBackPackHandleException() throws IOException {
        int[] products = new int[] { 2, 3, 4 };
        BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products);
        doThrow(new IOException()).when(mockBackPackDAO).updateBackPack(backpack);
        ResponseEntity<BackPack> response = backpackController.updateBackPack(backpack);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getBackPacks() throws IOException {
        BackPack[] backpacks = new BackPack[2];

        int[] products1 = new int[] { 2, 3, 4 };
        backpacks[0] = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products1);

        int[] products2 = new int[] { 5, 6, 4 };
        backpacks[1] = new BackPack(2, 2, "Lake fatehsagar fishing",
                "this backpack is for those who want to go fishing at lake fatehsagar",
                "Udaipur Rajasthan India", "fishing", products2);

        when(mockBackPackDAO.getBackPacks()).thenReturn(backpacks);
        ResponseEntity<BackPack[]> response = backpackController.getBackPacks(null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(backpacks, response.getBody());
    }

    @Test
    void getBackPacksWithException() throws IOException {
        doThrow(new IOException()).when(mockBackPackDAO).getBackPacks();
        ResponseEntity<BackPack[]> response = backpackController.getBackPacks(null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getBackPacksWithSearch() throws IOException {
        String searchString = "camp";

        BackPack[] backpacks = new BackPack[3];

        int[] products1 = new int[] { 2, 3, 4 };
        backpacks[0] = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products1);

        int[] products2 = new int[] { 5, 6, 4 };
        backpacks[1] = new BackPack(2, 2, "Lake fatehsagar fishing",
                "this backpack is for those who want to go fishing at lake fatehsagar",
                "Udaipur Rajasthan India", "fishing", products2);

        int[] products3 = new int[] { 5, 4 };
        backpacks[2] = new BackPack(3, 3, "Amazon forest camping",
                "this backpack is for those who want to go camping in amazon",
                "Amazon forest brazil", "camping", products3);

        BackPack[] searched = { backpacks[1] };
        when(mockBackPackDAO.findBackPacks(searchString)).thenReturn(searched);
        ResponseEntity<BackPack[]> response = backpackController.getBackPacks(searchString);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(searched, response.getBody());
    }

    @Test
    void testSearchBackPacksHandleException() throws IOException {
        String searchString = "camp";
        doThrow(new IOException()).when(mockBackPackDAO).findBackPacks(searchString);
        ResponseEntity<BackPack[]> response = backpackController.getBackPacks(searchString);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteBackPack() throws IOException {
        int Id = 99;
        when(mockBackPackDAO.deleteBackPack(Id)).thenReturn(true);
        ResponseEntity<BackPack> response = backpackController.deleteBackPack(Id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteBackPackNotFound() throws IOException {
        int Id = 99;
        when(mockBackPackDAO.deleteBackPack(Id)).thenReturn(false);
        ResponseEntity<BackPack> response = backpackController.deleteBackPack(Id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteBackPackHandleException() throws IOException {
        int Id = 99;
        doThrow(new IOException()).when(mockBackPackDAO).deleteBackPack(Id);
        ResponseEntity<BackPack> response = backpackController.deleteBackPack(Id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getProductsInBackPack() throws IOException {
        int[] products = new int[] { 2, 3, 4 };
        BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                "this backpack is for those who want to go fishing at lake Onterio",
                "Lake Onterio Rochester NewYork", "fishing", products);

        Product product2 = new Product(2, "Product2", "Description2", 10, 10, "giehige", "http://www.google.com");
        Product product3 = new Product(3, "Product3", "Description3", 10, 10, "giehige", "http://www.google.com");
        Product product4 = new Product(4, "Product4", "Description4", 10, 10, "giehige", "http://www.google.com");
        Product[] productsArray = {product2, product3, product4};
        when(mockBackPackDAO.getBackPack(backpack.getId())).thenReturn(backpack);
        when(mockProductDAO.getProduct(2)).thenReturn(product2);
        when(mockProductDAO.getProduct(3)).thenReturn(product3);
        when(mockProductDAO.getProduct(4)).thenReturn(product4);
        ResponseEntity<Product[]> response = backpackController.getProductsInBackPack(backpack.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(productsArray, response.getBody());
    }

    @Test
    void getProductsInBackpackWhenBackpackNotFound() throws IOException{
        when(mockBackPackDAO.getBackPack(1)).thenReturn(null);
        ResponseEntity<Product[]> response = backpackController.getProductsInBackPack(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getProductsInternalServerError() throws IOException{
        int Id = 99;
        doThrow(new IOException()).when(mockBackPackDAO).getBackPack(Id);
        ResponseEntity<Product[]> response = backpackController.getProductsInBackPack(Id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}