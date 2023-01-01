package com.estore.api.estoreapi.helper;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.CartItem;
import com.estore.api.estoreapi.model.Product;

import com.estore.api.estoreapi.persistence.ProductDAO;

public class UserCartHelper {
    ProductDAO productDao;

    public UserCartHelper(ProductDAO productDAO) {
        this.productDao = productDAO;
    }

    public Product[] convertCart(CartItem[] items) throws IOException {
        ArrayList<Product> cart = new ArrayList<>();
        if (items != null) {

            for (CartItem cartItem : items) {
                Product product = productDao.getProduct(cartItem.getProductId());
                Product newProduct = new Product(product.getId(), product.getName(), product.getDescription(),
                        product.getPrice(), product.getQuantity(), product.getManufacturer(), product.getImageUrl());
                newProduct.setQuantity(cartItem.getQuantity());
                cart.add(newProduct);
            }
        }
        return cart.toArray(new Product[0]);
    }

    public Product convertCartItem(CartItem item) throws IOException {
        Product product = productDao.getProduct(item.getProductId());
        Product newProduct = new Product(product.getId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getQuantity(), product.getManufacturer(), product.getImageUrl());
        newProduct.setQuantity(item.getQuantity());
        return newProduct;
    }

}
