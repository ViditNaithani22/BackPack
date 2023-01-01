package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserFileDAO implements UserDAO {
    Map<Integer, User> users;
    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private boolean load() throws IOException {
        users = new TreeMap<>();
        setNextId(0);
        User[] usersArray = objectMapper.readValue(new File(filename), User[].class);
        for (User user : usersArray) {
            users.put(user.getId(), user);
            if (user.getId() > nextId)
                setNextId(user.getId());
        }
        setNextId(getNextId()+1);
        return true;
    }

    

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        UserFileDAO.nextId = nextId;
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    private static synchronized int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private User[] getUsersArray(String containsText) {
        ArrayList<User> usersList = new ArrayList<>();

        for (User user : users.values()) {
            if (containsText == null || user.getUsername().toLowerCase().contains(containsText.toLowerCase())) {
                usersList.add(user);
            }

        }

        User[] userArray = new User[usersList.size()];
        usersList.toArray(userArray);
        return userArray;
    }

    private User[] getUsersArray() {
        ArrayList<User> usersList = new ArrayList<>();

        for (User user : users.values()) {
            usersList.add(user);

        }

        User[] userArray = new User[usersList.size()];
        usersList.toArray(userArray);
        return userArray;
    }

    @Override
    public User[] getUsers() {
        synchronized (users) {
            return getUsersArray();
        }
    }

    /**
     * Finds all users with name matching the string in containsText
     * 
     * @param containsText string to be matched against
     * @return User[] array that matches the search text
     */
    @Override
    public User[] findUsers(String containsText) {
        synchronized (users) {
            return getUsersArray(containsText);
        }
    }

    /**
     * Returns the user with the specific id
     * 
     * @param id The id of the {@link User user} to get
     *
     * @return User with the specific id
     */
    @Override
    public User getUser(int id) {
        synchronized (users) {
            return users.getOrDefault(id, null);
        }
    }

    @Override
    public User updateUser(User user) throws IOException {
        synchronized (users) {
            if (!users.containsKey(user.getId()))
                return null; // user does not exist

            users.put(user.getId(), user);
            save();
            return user;
        }
    }

    /**
     * Saves the {@linkplain User users} from the map into the file as an array of
     * JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();
        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    @Override
    public User createUser(User user) throws IOException {
        synchronized (users) {

            User newUser = new User(nextId(), user.getUsername(), false);
            users.put(newUser.getId(), newUser);
            save();
            return newUser;
        }
    }

    @Override
    public boolean deleteUser(int id) throws IOException {
        synchronized (users) {
            if (users.containsKey(id)) {
                users.remove(id);
                return save();
            } else
                return false;
        }
    }

}
