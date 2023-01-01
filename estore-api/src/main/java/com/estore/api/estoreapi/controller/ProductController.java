package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;

@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private ProductDAO productDao;

    public ProductController(ProductDAO productDAO) {
        this.productDao = productDAO;
    }

    /**
     * Responds to the GET request for all {@linkplain Product products} whose name
     * contains the text in name. If text is empty, returns all products.
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link Product products}
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be
     *         empty) and HTTP status of OK.
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */
    @GetMapping("")
    public ResponseEntity<Product[]> getProducts(@RequestParam(required = false) String name) {
        LOG.log(Level.INFO, "GET /products?name={}",name);
        try {
            Product[] products;
            if (name == null) {
                products = productDao.getProducts();
            } else {
                products = productDao.findProducts(name);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for a {@linkplain Product product} for the given
     * id
     *
     * @param id The id used to locate the {@link Product product}
     *
     * @return ResponseEntity with {@link Product product} object and HTTP status of
     *         OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        LOG.log(Level.INFO, "GET /products/{}",id);
        try {
            Product product = productDao.getProduct(id);
            if (product != null)
                return new ResponseEntity<>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Product[]> updateProduct(@RequestBody Product product) {
        LOG.log(Level.INFO, "PUT /products :{}",product);

        try {
            Product newProduct = productDao.updateProduct(product);
            if (newProduct != null) {
                return new ResponseEntity<>(productDao.getProducts(), HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.log(Level.INFO, "POST /products :{}",product);
        try {
            Product[] existingProducts = productDao.findProducts(product.getName());
            if (existingProducts.length > 0) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                Product createdProduct = productDao.createProduct(product);
                return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id The id of the {@link Product product} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        LOG.log(Level.INFO, "DELETE /products/{}",id);
        try {
            if (productDao.deleteProduct(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
