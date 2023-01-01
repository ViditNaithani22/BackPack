package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserDAO;

@Tag("Controller-tier")
class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new UserController object and inject
     * a mock User DAO
     */
    @BeforeEach
    void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    void getUser() throws IOException {
        User user = new User(1, "zac", false);
    
        when(mockUserDAO.getUser(user.getId())).thenReturn(user);
        ResponseEntity<User> response = userController.getUser(user.getId());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    void getUserNotFound() throws Exception {
        int userId = 99;
        when(mockUserDAO.getUser(userId)).thenReturn(null);
        ResponseEntity<User> response = userController.getUser(userId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void getUserWithException() throws Exception {
        int userId = 99;
        doThrow(new IOException()).when(mockUserDAO).getUser(userId);
        ResponseEntity<User> response = userController.getUser(userId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void createUserSuccessfully() throws IOException {
        User user = new User(1, "zac", false);
        when(mockUserDAO.createUser(user)).thenReturn(user);
        User[] matching = new User[0];
        when(mockUserDAO.findUsers("zac")).thenReturn(matching);
        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    void createUserFail() throws IOException {
        User user = new User(1, "zac", false);
        when(mockUserDAO.createUser(user)).thenReturn(null);
        User[] matching = {user};
        when(mockUserDAO.findUsers("zac")).thenReturn(matching);
        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    void createUserWithException() throws IOException { 
        User user = new User(1, "zac", false);
        doThrow(new IOException()).when(mockUserDAO).createUser(user);
        User[] matching = new User[0];
        when(mockUserDAO.findUsers("zac")).thenReturn(matching);
        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void updateUserSuccessfully() throws IOException {
        User user = new User(1, "zac", false);
        when(mockUserDAO.updateUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.updateUser(user);
        user.setUsername("New Fishing Rod");
        response = userController.updateUser(user);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(user,response.getBody());
    }

    @Test
    void updateUserFailed() throws IOException {
        User user = new User(1, "zac", false);
        when(mockUserDAO.updateUser(user)).thenReturn(null);
        ResponseEntity<User> response = userController.updateUser(user);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void updateUserHandleException() throws IOException {
        User user = new User(1, "zac", false);
        doThrow(new IOException()).when(mockUserDAO).updateUser(user);
        ResponseEntity<User> response = userController.updateUser(user);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void getUsers() throws IOException {
        User[] users = new User[2];
        users[0] = new User(1, "zac", false);
        users[1] = new User(2, "vin", false);
        when(mockUserDAO.getUsers()).thenReturn(users);
        ResponseEntity<User[]> response = userController.getUsers(null);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(users,response.getBody());
    }

    @Test
    void getUsersWithException() throws IOException {
        doThrow(new IOException()).when(mockUserDAO).getUsers();
        ResponseEntity<User[]> response = userController.getUsers(null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void getUsersWithSearch() throws IOException {
        String searchString = "camp";
        User[] users = new User[3];
        users[0] = new User(1, "zac", false);
        users[1] = new User(2, "vin", false);
        users[2] = new User(3, "merg", false);
        User[] searched = {users[1]};
        when(mockUserDAO.findUsers(searchString)).thenReturn(searched);
        ResponseEntity<User[]> response = userController.getUsers(searchString);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(searched,response.getBody());
    }

    @Test
    void testSearchUsersHandleException() throws IOException {
        String searchString = "camp";
        doThrow(new IOException()).when(mockUserDAO).findUsers(searchString);
        ResponseEntity<User[]> response = userController.getUsers(searchString);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void testDeleteUser() throws IOException {
        int userId = 99;
        when(mockUserDAO.deleteUser(userId)).thenReturn(true);
        ResponseEntity<User> response = userController.deleteUser(userId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void testDeleteUserNotFound() throws IOException {
        int userId = 99;
        when(mockUserDAO.deleteUser(userId)).thenReturn(false);
        ResponseEntity<User> response = userController.deleteUser(userId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void testDeleteUserHandleException() throws IOException {
        int userId = 99;
        doThrow(new IOException()).when(mockUserDAO).deleteUser(userId);
        ResponseEntity<User> response = userController.deleteUser(userId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
