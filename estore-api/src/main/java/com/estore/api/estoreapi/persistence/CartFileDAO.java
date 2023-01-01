package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.CartItem;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CartFileDAO implements CartDAO {
    Map<Integer, ArrayList<CartItem>> cart;
    private ObjectMapper objectMapper;
    private String filename;

    public CartFileDAO(@Value("${cart.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Reads the storage file for this user's cart, and loads it.
     * 
     * @return true if successful, false if not.
     * @throws IOException if underlying storage cannot be accessed
     */
    private boolean load() throws IOException {
        cart = new HashMap<>();
        CartItem[] cartArray = objectMapper.readValue(new File(filename), CartItem[].class);
        for (CartItem item : cartArray) {
            addItemToCart(item);
        }
        return true;
    }

    private CartItem addItemToCart(CartItem item) {
        if (!cart.containsKey(item.getUserId())) {
            cart.put(item.getUserId(), new ArrayList<>());
        }
        cart.get(item.getUserId()).add(item);
        return item;
    }

    @Override
    public CartItem[] getCart() throws IOException {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        for (ArrayList<CartItem> items : cart.values()) {
            cartItems.addAll(items);
        }
        return cartItems.toArray(new CartItem[0]);
    }

    @Override
    public CartItem[] getCartForUser(int userId) throws IOException {
        ArrayList<CartItem> items= cart.get(userId);
        if(items == null){
            return new CartItem[] {};
        }
        return items.toArray(new CartItem[0]);
    }

    @Override
    public CartItem addToCart(CartItem cartItem) throws IOException {
        CartItem createdCartItem = addItemToCart(cartItem);
        save();
        return createdCartItem;
    }

    private boolean save() throws IOException {
        CartItem[] cartItems = getCart();
        objectMapper.writeValue(new File(filename), cartItems);
        return true;
    }

    public CartItem getProductInUserCart(int productId, int userId) throws IOException {
        if (userCartExists(userId)) {
            CartItem[] userCart = getCartForUser(userId);
            for (CartItem cartItem : userCart) {
                if (cartItem.getProductId() == productId) {
                    return cartItem;
                }
            }
        }
        return null;
    }

    private boolean userCartExists(int userId) {
        return cart.containsKey(userId);
    }

    @Override
    public CartItem increase(int productId, int userId, int maxLimit) throws IOException {
        CartItem item = getProductInUserCart(productId, userId);
        if (item == null) {
            return null;
        }
        int newQuantity = item.getQuantity() + 1;
        if(newQuantity > maxLimit){
            return item;
        }
        item.setQuantity(newQuantity);
        save();
        return item;
    }

    @Override
    public CartItem decrease(int productId, int userId) throws IOException {
        CartItem item = getProductInUserCart(productId, userId);
        if (item == null) {
            return null;
        }
        int newQuantity = item.getQuantity() -1 ;
        if(newQuantity<=0){
            clearItem(productId, userId);
            item = null;
        } else {
            item.setQuantity(newQuantity);
        }
        save();
        return item;
    }


    @Override
    public boolean clearItem(int productId, int userId) throws IOException {
        if (userCartExists(userId)) {
            ArrayList<CartItem> items = cart.get(userId);
            for (CartItem item : items) {
                if (item.getProductId() == productId) {
                    items.remove(item);
                    save();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean clearUserCart(int userId) throws IOException {
        if(userCartExists(userId)){
            cart.remove(userId);
            save();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer[] getIdsForClearing(int userId) throws IOException{
        ArrayList<Integer> productIds = new ArrayList<>();
        CartItem[] items = getCartForUser(userId);
        for(CartItem cartItem: items){
            productIds.add(cartItem.getProductId());
        }
        return productIds.toArray(new Integer[0]);
    }

    @Override
    public int getQuantity(int userId, int productId) throws IOException{
        CartItem[] items = getCartForUser(userId);
        for(CartItem cartItem: items){
            if(productId == cartItem.getProductId()){
                return cartItem.getQuantity();
            }
        }
        return 0;
    }

}
