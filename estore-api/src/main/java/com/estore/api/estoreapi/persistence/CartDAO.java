package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.CartItem;

/**
 * Defines the interface for Cart persistence
 */
public interface CartDAO {
    /**
     * Retrieves all {@linkplain CartItem objects}
     * 
     * @return An array of {@link CartItem CartItem} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    CartItem[] getCart() throws IOException;

    CartItem[] getCartForUser(int userId) throws IOException;


    /**
     * Adds and saves a {@linkplain CartItem cartItem}
     * 
     * @param cartItem {@linkplain CartItem cartItem} object to be added to the cart
     *                 and saved.
     * @param userId   {@linkplain Integer integer} is the id of the user of the
     *                 cart.
     * @return new {@link CartItem cartItem} if successful, null otherwise.
     * @throws IOException if there is an issue with underlying storage.
     */
    CartItem addToCart(CartItem cartItem) throws IOException;

    /**
     * Increments the quantity of a {@linkplain CartItem cartItem} with the given id
     * for current user
     * 
     * @param productId The id of the {@link CartItem cartItem} to increment
     * @param userId    The id of the current {@link User user}
     * @return An array of {@link CartItem CartItem} objects, may be empty
     * @throws IOException if an issue with underlying storaged
     */
    CartItem increase(int productId, int userId, int maxLimit) throws IOException;

    /**
     * Decrements the quantity of a {@linkplain CartItem cartItem} with the given id
     * for current user
     * 
     * @param productId The id of the {@link CartItem cartItem} to increment
     * @param userId    The id of the current {@link User user}
     * @return An array of {@link CartItem CartItem} objects, may be empty
     * @throws IOException if an issue with underlying storaged
     */
    CartItem decrease(int productId, int userId) throws IOException;

    /**
     * Sets the quantity of a {@linkplain CartItem cartItem} with the given id for
     * current user to zero
     * 
     * @param productId The id of the {@link CartItem cartItem} to increment
     * @param userId    The id of the current {@link User user}
     * @return An array of {@link CartItem CartItem} objects, may be empty
     * @throws IOException if an issue with underlying storaged
     */
    boolean clearItem(int productId, int userId) throws IOException;

    boolean clearUserCart(int userId) throws IOException;

    Integer[] getIdsForClearing(int userId) throws IOException;

    int getQuantity(int userId, int productId) throws IOException;

    public CartItem getProductInUserCart(int productId, int userId) throws IOException;

}
