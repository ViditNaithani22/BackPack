package com.estore.api.estoreapi.controller;

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

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;

@Tag("Controller-tier")
class ProductControllerTest {
    private ProductController productController;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    void setupProductController() {
        mockProductDAO = mock(ProductDAO.class);
        productController = new ProductController(mockProductDAO);
    }

    @Test
    void getProduct() throws IOException {
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10, "fish", "http://www.google.com");
    
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        ResponseEntity<Product> response = productController.getProduct(product.getId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    void getProductNotFound() throws Exception {
        int productId = 99;
        when(mockProductDAO.getProduct(productId)).thenReturn(null);
        ResponseEntity<Product> response = productController.getProduct(productId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void getProductWithException() throws Exception {
        int productId = 99;
        doThrow(new IOException()).when(mockProductDAO).getProduct(productId);
        ResponseEntity<Product> response = productController.getProduct(productId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void createProductSuccessfully() throws IOException {
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        when(mockProductDAO.createProduct(product)).thenReturn(product);
        Product[] matching = new Product[0];
        when(mockProductDAO.findProducts("Fishing rod")).thenReturn(matching);
        ResponseEntity<Product> response = productController.createProduct(product);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    void createProductFail() throws IOException {
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        when(mockProductDAO.createProduct(product)).thenReturn(null);
        Product[] matching = {product};
        when(mockProductDAO.findProducts("Fishing rod")).thenReturn(matching);
        ResponseEntity<Product> response = productController.createProduct(product);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    void createProductWithException() throws IOException { 
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        doThrow(new IOException()).when(mockProductDAO).createProduct(product);
        Product[] matching = new Product[0];
        when(mockProductDAO.findProducts("Fishing rod")).thenReturn(matching);
        ResponseEntity<Product> response = productController.createProduct(product);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void updateProductSuccessfully() throws IOException {
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        Product[] products = new Product[1];
        products[0] = product;
        when(mockProductDAO.updateProduct(product)).thenReturn(product);
        when(mockProductDAO.getProducts()).thenReturn(products);
        ResponseEntity<Product[]> response = productController.updateProduct(product);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        System.out.println(response.getBody());
        assertEquals(products,response.getBody());
    }

    @Test
    void updateProductFailed() throws IOException {
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        when(mockProductDAO.updateProduct(product)).thenReturn(null);
        ResponseEntity<Product[]> response = productController.updateProduct(product);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void updateProductHandleException() throws IOException {
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        doThrow(new IOException()).when(mockProductDAO).updateProduct(product);
        ResponseEntity<Product[]> response = productController.updateProduct(product);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void getProducts() throws IOException {
        Product[] products = new Product[2];
        products[0] = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        products[1] = new Product(2, "Fishing rod 2", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        when(mockProductDAO.getProducts()).thenReturn(products);
        ResponseEntity<Product[]> response = productController.getProducts(null);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    @Test
    void getProductsWithException() throws IOException {
        doThrow(new IOException()).when(mockProductDAO).getProducts();
        ResponseEntity<Product[]> response = productController.getProducts(null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void getProductsWithSearch() throws IOException {
        String searchString = "camp";
        Product[] products = new Product[3];
        products[0] = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        products[1] = new Product(2, "Camping gear", "Can be used for gear", 5.0, 2,"fish", "http://www.google.com");
        products[2] = new Product(3, "Hiking boots", "Can be used for boots", 13.0, 5,"fish", "http://www.google.com");
        Product[] searched = {products[1]};
        when(mockProductDAO.findProducts(searchString)).thenReturn(searched);
        ResponseEntity<Product[]> response = productController.getProducts(searchString);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(searched,response.getBody());
    }

    @Test
    void testSearchProductsHandleException() throws IOException {
        String searchString = "camp";
        doThrow(new IOException()).when(mockProductDAO).findProducts(searchString);
        ResponseEntity<Product[]> response = productController.getProducts(searchString);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void testDeleteProduct() throws IOException {
        int productId = 99;
        when(mockProductDAO.deleteProduct(productId)).thenReturn(true);
        ResponseEntity<Product> response = productController.deleteProduct(productId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void testDeleteProductNotFound() throws IOException {
        int productId = 99;
        when(mockProductDAO.deleteProduct(productId)).thenReturn(false);
        ResponseEntity<Product> response = productController.deleteProduct(productId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void testDeleteProductHandleException() throws IOException {
        int productId = 99;
        doThrow(new IOException()).when(mockProductDAO).deleteProduct(productId);
        ResponseEntity<Product> response = productController.deleteProduct(productId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
