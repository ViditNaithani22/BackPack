package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ProductFileDAO implements ProductDAO {
    Map<Integer, Product> products;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    public ProductFileDAO(@Value("${products.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private boolean load() throws IOException {
        products = new TreeMap<>();
        setNextId(0);
        Product[] productsArray = objectMapper.readValue(new File(filename), Product[].class);
        for (Product product : productsArray) {
            products.put(product.getId(), product);
            if (product.getId() > nextId)
                setNextId(product.getId());
        }
        setNextId(getNextId() + 1);
        return true;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        ProductFileDAO.nextId = nextId;
    }

    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return The next id
     */
    private static synchronized int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private Product[] getProductsArray(String containsText) {
        ArrayList<Product> productsList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().toLowerCase().contains(containsText.toLowerCase())) {
                productsList.add(product);
            }

        }

        Product[] productArray = new Product[productsList.size()];
        productsList.toArray(productArray);
        return productArray;
    }

    private Product[] getProductsArray() {
        ArrayList<Product> productsList = new ArrayList<>();

        for (Product product : products.values()) {
            productsList.add(product);

        }

        Product[] productArray = new Product[productsList.size()];
        productsList.toArray(productArray);
        return productArray;
    }

    @Override
    public Product[] getProducts() {
        synchronized (products) {
            return getProductsArray();
        }
    }

    /**
     * Finds all products with name matching the string in containsText
     * 
     * @param containsText string to be matched against
     * @return Product[] array that matches the search text
     */
    @Override
    public Product[] findProducts(String containsText) {
        synchronized (products) {
            return getProductsArray(containsText);
        }
    }

    /**
     * Returns the product with the specific id
     * 
     * @param id The id of the {@link Product product} to get
     *
     * @return Product with the specific id
     */
    @Override
    public Product getProduct(int id) {
        synchronized (products) {
            return products.getOrDefault(id, null);
        }
    }

    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized (products) {
            if (!products.containsKey(product.getId()))
                return null; // product does not exist

            products.put(product.getId(), product);
            save();
            return product;
        }
    }

    /**
     * Saves the {@linkplain Product products} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Product products} were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();
        objectMapper.writeValue(new File(filename), productArray);
        return true;
    }

    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized (products) {
            Product newProduct = new Product(nextId(), product.getName(), product.getDescription(), product.getPrice(),
                    product.getQuantity(), product.getManufacturer(), product.getImageUrl());
            products.put(newProduct.getId(), newProduct);
            save();
            return newProduct;
        }
    }

    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized (products) {
            if (products.containsKey(id)) {
                products.remove(id);
                return save();
            } else
                return false;
        }
    }

}
