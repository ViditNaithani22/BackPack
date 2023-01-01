package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
class ProductTest {
Product product = new Product(10,"doesn't matter", "it doesn't matter", 45.78, 7,"fish", "http://www.google.com");
    @Test
    void createProduct(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        assertEquals(1, product.getId());
        assertEquals("Fishing rod", product.getName());
        assertEquals("Can be used for fishing", product.getDescription());
        assertEquals(35.0, product.getPrice());
        assertEquals(10, product.getQuantity());
    }

    @Test
    void equals(){
        Product productOriginal = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        Product productSame = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        Product productNewDiff = new Product(2, "Not a fishing rod", "Can not be used for fishing", 53.0, 1,"fish", "http://www.google.com");

        assertEquals(productOriginal, productSame);
        assertNotEquals(productOriginal, productNewDiff);
    }


    @Test
    void setId() {
        product.setId(15);
        assertEquals(15, product.getId());
    }
    @Test
    void setName(){
        product.setName("something else");
        assertEquals("something else", product.getName());
    }
    @Test
    void setPrice(){
        product.setPrice(25.67);
        assertEquals(25.67, product.getPrice());
    }
    @Test
    void setQuantity() {
        product.setQuantity(2);
        assertEquals(2, product.getQuantity());
    }
    @Test
    void setDescription(){
        product.setDescription("It doesn't matter");
        assertEquals("It doesn't matter", product.getDescription());
    }

    @Test
    void testToString(){
        assertEquals("Product [id=" + product.getId() + ", name=" + product.getName() + ", description=" + product.getDescription() + ", price=" + product.getPrice()
        + ", quantity=" + product.getQuantity() + ", manufacturer=" + product.getManufacturer() + ", imageUrl=" + product.getImageUrl() + "]", product.toString());
    }

    @Test
    void testsetManufacturer(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        assertEquals("fish", product.getManufacturer());
        product.setManufacturer("New Manufacturer");
        assertEquals("New Manufacturer", product.getManufacturer());
    }

    @Test
    void testHashCode(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        assertEquals(1, product.hashCode());    
    }


    @Test
    void testEqualsNull(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        assertNotEquals(null,product);   
    }

    @Test
    void testEqualsDifferentObjects(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        User user = new User(1, "user", false);
        assertNotEquals(product, user);   
    }

    @Test
    void testEqualsProductWithDifferentId(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        Product product2 = new Product(2, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        assertNotEquals(product, product2);
    }

    @Test
    void testEqualsWithSameId(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        Product product2 = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        assertEquals(product, product2);
    }

    @Test
    void testsetImageUrl(){
        Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10,"fish", "http://www.google.com");
        assertEquals("http://www.google.com", product.getImageUrl());
        product.setImageUrl("http://www/yahoo.com");
        assertEquals("http://www/yahoo.com", product.getImageUrl());
    }

}
