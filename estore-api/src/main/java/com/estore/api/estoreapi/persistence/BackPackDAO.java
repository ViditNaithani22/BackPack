package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.BackPack;

/**
 * Defines the interface for {@linkplain BackPack backpack} object persistence
 */
public interface BackPackDAO {
    /**
     * Retrieves all {@linkplain BackPack backpack}
     * 
     * @return An array of {@link BackPack backpack} objects, may be empty
     * @throws IOException if an issue with underlying storage
     */

    BackPack[] getBackPacks() throws IOException;

    /**
     * Updates and saves a {@linkplain BackPack backpack}
     * 
     * @param {@link BackPack backpack} object to be updated and saved
     * 
     * @return updated {@link BackPack backpack} if successful, null if
     *         {@link BackPack backpack} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */

    BackPack updateBackPack(BackPack backpack) throws IOException;

    /**
     * Creates and saves a {@linkplain BackPack backpack}
     * 
     * @param  {@linkplain BackPack backpack} object to be created and saved.
     *             The id of the user object is ignored and a new unique id is
     *             assigned.
     * @return new {@link BackPack backpack} if successful, false otherwise.
     * @throws IOException if there is an issue with underlying storage.
     */
    BackPack createBackPack(BackPack backpack) throws IOException;

    /**
     * Finds all {@linkplain BackPack backpack} whose name contains the given text
     * 
     * @param containsText The text to match against
     * @return An array of {@link BackPack backpack} whose names contain the given
     *         text,
     *         may be empty
     * @throws IOException if an issue with underlying storage
     */
    BackPack[] findBackPacks(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain BackPack backpack} with the given id
     *
     * @param id The id of the {@link BackPack backpack} to get
     *
     * @return a {@link BackPack backpack} object with the matching id
     *         <br>
     *         null if no {@link BackPack backpack} with a matching id is found
     *
     * @throws IOException if an issue with underlying storage
     */
    BackPack getBackPack(int id) throws IOException;

    /**
     * Deletes a {@linkplain BackPack backpack} with the given id
     * 
     * @param id The id of the {@link BackPack backpack}
     * @return true if the {@link BackPack backpack} was deleted
     *         false if user with the given id does not exist
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteBackPack(int id) throws IOException;
}
