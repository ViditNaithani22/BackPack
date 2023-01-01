package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.estore.api.estoreapi.model.BackPack;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.BackPackDAO;
import com.estore.api.estoreapi.persistence.ProductDAO;

@RestController
@RequestMapping("backpack")
public class BackPackController {
    private static final Logger LOG = Logger.getLogger(BackPackController.class.getName());
    private BackPackDAO backpackDao;
    private ProductDAO productDao;

    public BackPackController(BackPackDAO backpackDao, ProductDAO productDAO) {
        this.backpackDao = backpackDao;
        this.productDao = productDAO;
    }

    /**
     * Responds to the GET request for all {@linkplain BackPack backpacks} whose
     * name
     * contains the text in name. If text is empty, returns all products.
     *
     *
     *
     * @return ResponseEntity with array of {@link BackPack backpack} objects (may
     *         be
     *         empty) and HTTP status of OK.
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */

    @GetMapping("")
    public ResponseEntity<BackPack[]> getBackPacks(@RequestParam(required = false) String search) {

        try {
            BackPack[] backpacks;
            if (search == null) {
                backpacks = backpackDao.getBackPacks();
            } else {
                backpacks = backpackDao.findBackPacks(search);
            }
            return new ResponseEntity<>(backpacks, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for a {@linkplain BackPack backpack} for the
     * given id
     *
     * @param id The id used to locate the {@link BackPack backpack}
     *
     * @return ResponseEntity with {@link BackPack backpack} object and HTTP status
     *         of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @GetMapping("/{id}")
    public ResponseEntity<BackPack> getBackPack(@PathVariable int id) {
        LOG.log(Level.INFO, "GET /backpacks/{}",id);
        try {
            BackPack backpack = backpackDao.getBackPack(id);
            if (backpack != null)
                return new ResponseEntity<>(backpack, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Product[]> getProductsInBackPack(@PathVariable int id){
        LOG.log(Level.INFO, "GET /backpack/products/{}",id);
        try {
            BackPack backpack = backpackDao.getBackPack(id);
            if (backpack != null){
                ArrayList<Product> products = new ArrayList<>();
                for (int productId : backpack.getProductId()) {
                    Product product = productDao.getProduct(productId);
                    products.add(product);
                }
                Product[] productsArray = products.toArray(new Product[0]);
                return new ResponseEntity<>(productsArray, HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<BackPack> updateBackPack(@RequestBody BackPack backpack) {
        LOG.log(Level.INFO, "PUT /backpack :{}",backpack);       

        try {
            BackPack newBackPack = backpackDao.updateBackPack(backpack);
            if (newBackPack != null)
                return new ResponseEntity<>(newBackPack, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<BackPack> createBackPack(@RequestBody BackPack backpack) {
        LOG.log(Level.INFO, "POST /backpack :{}",backpack);  
        try {
            BackPack[] existingBackPacks = backpackDao.findBackPacks(backpack.getName());
            if (existingBackPacks.length > 0) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                BackPack createdBackPack = backpackDao.createBackPack(backpack);
                return new ResponseEntity<>(createdBackPack, HttpStatus.CREATED);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param id The id of the {@link BackPack backpack} to deleted
     *
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<BackPack> deleteBackPack(@PathVariable int id) {
        LOG.log(Level.INFO, "DELETE /backpacks/{}",id);  

        try {
            if (backpackDao.deleteBackPack(id)) {
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
