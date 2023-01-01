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
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.helper.UserCartHelper;
import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.CartDAO;

@RestController
@RequestMapping("cart")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private CartDAO cartDao;
    private ProductDAO productDAO;

    public CartController(CartDAO cartdao, ProductDAO productDao) {
        this.cartDao = cartdao;
        this.productDAO = productDao;
    }

    /**
     * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart.
     * 
     * @return ResponseEntity with array of {@link CartItem CartItem} objects (may be
     *         empty) and HTTP status of OK.
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */
    @GetMapping("")
    public ResponseEntity<CartItem[]> getCart() {
        LOG.info("GET /cart/");
        try {
            CartItem[] cart = cartDao.getCart();
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart for a user
     * 
     * @param userId The userId parameter which contains the id used to find the
     *             {@link User user}.
     * 
     * @return ResponseEntity with array of {@link Product products} objects (may be
     *         empty) and HTTP status of OK.
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */
    @GetMapping("{userId}")
    public ResponseEntity<Product[]> getCartForUser(@PathVariable int userId) {
        LOG.log(Level.INFO, "GET /cart/{}",userId);  
        try {
            CartItem[] cart = cartDao.getCartForUser(userId);
            UserCartHelper cartHelper = new UserCartHelper(productDAO);
            Product[] userCart = cartHelper.convertCart(cart);
            return new ResponseEntity<>(userCart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


   /** 
    * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart for a user
    * after the quantity of a {@linkplain Product product} object has been decremented
    * @param productId The productId parameter which contains the id used to find the
    *             {@link Product product}.
    * 
    * @return ResponseEntity with array of {@link Product products} objects (may be
    *         empty) and HTTP status of OK.
    *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
    */
    @PutMapping("decrease")
    public ResponseEntity<Product> decrease(@RequestBody CartItem cartItem) {
        LOG.log(Level.INFO, "GET /cart/decrease: {}",cartItem);

        try {
            CartItem decreasedCart = cartDao.decrease(cartItem.getProductId(), cartItem.getUserId());
            if(decreasedCart!=null){
                UserCartHelper cartHelper = new UserCartHelper(productDAO);
                Product product = cartHelper.convertCartItem(decreasedCart);
                return new ResponseEntity<>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Product(0,"","",0,0,"",""), HttpStatus.OK);
            }
            
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /** 
    * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart for a user
    * after the quantity of a {@linkplain Product product} object has been incremented
    * @param productId The productId parameter which contains the id used to find the
    *             {@link Product product}.
    * 
    * @return ResponseEntity with array of {@link Product products} objects (may be
    *         empty) and HTTP status of OK.
    *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
    */
    @PutMapping("increase")
    public ResponseEntity<Product> increase(@RequestBody CartItem cartItem) {
        LOG.log(Level.INFO, "GET /cart/increase: {}",cartItem);

        try {
            Product product = productDAO.getProduct(cartItem.getProductId());
            int maxLimit = product.getQuantity();
            CartItem increasedCart = cartDao.increase(cartItem.getProductId(), cartItem.getUserId(), maxLimit);
            UserCartHelper cartHelper = new UserCartHelper(productDAO);
            product = cartHelper.convertCartItem(increasedCart);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Responds to the POST request for a {@linkplain CartItem cartItem} to cart
     *
     * @param product is a {@link CartItem cartItem}
     *
     * @return ResponseEntity with {@link CartItem cartItem} object and HTTP status of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Product> addToCart(@RequestBody CartItem cartItem) {
        LOG.log(Level.INFO, "POST /cart : {}",cartItem);
        try {
            CartItem existingItem = cartDao.getProductInUserCart(cartItem.getProductId(), cartItem.getUserId());
            CartItem createdCartItem;
            if(existingItem == null){
                createdCartItem = cartDao.addToCart(cartItem);
            } else {
                Product product = productDAO.getProduct(cartItem.getProductId());
                int maxLimit = product.getQuantity();
                createdCartItem = cartDao.increase(cartItem.getProductId(), cartItem.getUserId(), maxLimit);
            }
        
            UserCartHelper cartHelper = new UserCartHelper(productDAO);
            Product product = cartHelper.convertCartItem(createdCartItem);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /** 
    * Responds to the GET request for all {@linkplain CartItem CartItem} objects in cart for a user
    * after the quantity of a {@linkplain Product product} object has been set to zero
    * @param productId The productId parameter which contains the id used to find the
    *             {@link Product product}.
    * 
    * @return ResponseEntity with array of {@link Product products} objects (may be
    *         empty) and HTTP status of OK.
    *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
    */
    @PutMapping("clear")
    public ResponseEntity<Product[]> clearItem(@RequestBody CartItem cartItem) {
        LOG.log(Level.INFO, "PUT /clear: {}",cartItem);
        try {
            cartDao.clearItem(cartItem.getProductId(), cartItem.getUserId());
            CartItem[] cart = cartDao.getCartForUser(cartItem.getUserId());
            UserCartHelper cartHelper = new UserCartHelper(productDAO);      
            Product[] userCart = cartHelper.convertCart(cart);
            return new ResponseEntity<>(userCart, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("clear/{userId}")
    public ResponseEntity<Boolean> deleteUserCart(@PathVariable int userId) {
        try {
            cartDao.clearUserCart(userId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("checkout/{userId}")
    public ResponseEntity<Boolean> checkout(@PathVariable int userId) {
        LOG.log(Level.INFO, "GET /cart/checkout/{}",userId);
        try {
            Integer[] cartIds = cartDao.getIdsForClearing(userId);
            for(int i=0;i<cartIds.length;i++){
                Product thisProduct = productDAO.getProduct(cartIds[i]);
                thisProduct.setQuantity(thisProduct.getQuantity()-cartDao.getQuantity(userId, thisProduct.getId()));
                productDAO.updateProduct(thisProduct);
            }
            boolean isSuccessful = cartDao.clearUserCart(userId);
            if(isSuccessful){
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
