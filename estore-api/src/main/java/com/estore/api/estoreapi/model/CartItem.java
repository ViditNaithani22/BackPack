package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent a cart item
 */
public class CartItem {
    @JsonProperty("userId") private int userId;
    @JsonProperty("productId") private int productId;
    @JsonProperty("quantity") private int quantity;

    public CartItem( @JsonProperty("userId") int userId, @JsonProperty("productId") int productId, @JsonProperty("quantity") int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }



    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem [userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + "]";
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + userId;
        result = prime * result + productId;
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CartItem other = (CartItem) obj;
        if (userId != other.userId)
            return false;
        return productId == other.productId;
    }

}
