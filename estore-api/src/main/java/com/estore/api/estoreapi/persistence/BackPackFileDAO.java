package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.BackPack;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BackPackFileDAO implements BackPackDAO {
    Map<Integer, BackPack> backpacks;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    public BackPackFileDAO(@Value("${backpack.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private boolean load() throws IOException {
        backpacks = new TreeMap<>();
        setNextId(0);
        BackPack[] backpacksArray = objectMapper.readValue(new File(filename), BackPack[].class);
        for (BackPack backpack : backpacksArray) {
            backpacks.put(backpack.getId(), backpack);
            if (backpack.getId() > getNextId())
                setNextId(backpack.getId());
        }
        setNextId(getNextId()+1);
        return true;
    }

    

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        BackPackFileDAO.nextId = nextId;
    }

    /**
     * Generates the next id for a new {@linkplain BackPack backpack}
     *
     * @return The next id
     */
    private static synchronized int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private BackPack[] getBackPacksArray(String containsText) {
        ArrayList<BackPack> backpacksList = new ArrayList<>();

        for (BackPack backpack : backpacks.values()) {
            if (containsText == null || backpack.getName().toLowerCase().contains(containsText.toLowerCase()) || backpack.getActivity().toLowerCase().contains(containsText.toLowerCase()) || backpack.getLocation().toLowerCase().contains(containsText.toLowerCase())) {
                backpacksList.add(backpack);
            }

        }

        BackPack[] backpackArray = new BackPack[backpacksList.size()];
        backpacksList.toArray(backpackArray);
        return backpackArray;
    }

    private BackPack[] getBackPacksArray() {
        ArrayList<BackPack> backpacksList = new ArrayList<>();

        for (BackPack backpack : backpacks.values()) {
            backpacksList.add(backpack);

        }

        BackPack[] backpackArray = new BackPack[backpacksList.size()];
        backpacksList.toArray(backpackArray);
        return backpackArray;
    }

    @Override
    public BackPack[] getBackPacks() {
        synchronized (backpacks) {
            return getBackPacksArray();
        }
    }

    /**
     * Finds all users with name matching the string in containsText
     *
     * @param containsText string to be matched against
     * @return BackPack[] array that matches the search text
     */
    @Override
    public BackPack[] findBackPacks(String containsText) {
        synchronized (backpacks) {
            return getBackPacksArray(containsText);
        }
    }

    /**
     * Returns the user with the specific id
     *
     * @param id The id of the {@link BackPack backpack} to get
     *
     * @return User with the specific id
     */
    @Override
    public BackPack getBackPack(int id) {
        synchronized (backpacks) {
            return backpacks.getOrDefault(id, null);
        }
    }

    @Override
    public BackPack updateBackPack(BackPack backpack) throws IOException {
        synchronized (backpacks) {
            if (!backpacks.containsKey(backpack.getId()))
                return null; // user does not exist

            backpacks.put(backpack.getId(), backpack);
            save();
            return backpack;
        }
    }

    /**
     * Saves the {@linkplain BackPack backpacks} from the map into the file as an
     * array of
     * JSON objects
     *
     * @return true if the {@link BackPack backpacks} were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        BackPack[] backpackArray = getBackPacksArray();
        objectMapper.writeValue(new File(filename), backpackArray);
        return true;
    }

    @Override
    public BackPack createBackPack(BackPack backpack) throws IOException {
        synchronized (backpacks) {

            BackPack newBackPack = new BackPack(nextId(),
                    backpack.getUserId(), backpack.getName(), backpack.getDescription(),
                    backpack.getLocation(), backpack.getActivity(), backpack.getProductId());
            backpacks.put(newBackPack.getId(), newBackPack);
            save();
            return newBackPack;
        }
    }

    @Override
    public boolean deleteBackPack(int id) throws IOException {
        synchronized (backpacks) {
            if (backpacks.containsKey(id)) {
                backpacks.remove(id);
                return save();
            } else
                return false;
        }
    }

}