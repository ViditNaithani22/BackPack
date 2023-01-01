package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.estore.api.estoreapi.model.BackPack;
import com.fasterxml.jackson.databind.ObjectMapper;

class BackPackFileDAOTest {
    BackPackFileDAO backpackFileDAO;
    BackPack[] testBackPacks;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    void setupBackPackFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testBackPacks = new BackPack[3];
        testBackPacks[0] = new BackPack(1, 1, "backpack1", "description for backpack1", "location1", "activity1",
                new int[] { 1, 2, 3 });
        testBackPacks[1] = new BackPack(2, 2, "backpack2", "description for backpack2", "location2", "activity2",
                new int[] { 4, 5, 6 });
        testBackPacks[2] = new BackPack(3, 3, "backpack3", "description for backpack3", "location3", "activity3",
                new int[] { 7, 8, 9 });

        when(mockObjectMapper
                .readValue(new File("backpacks.json"), BackPack[].class))
                .thenReturn(testBackPacks);
        backpackFileDAO = new BackPackFileDAO("backpacks.json", mockObjectMapper);
    }

    @Test
    void getBackPacks() {
        BackPack[] backpacks = backpackFileDAO.getBackPacks();
        for (int i = 0; i < testBackPacks.length; i++) {
            assertEquals(testBackPacks[i], backpacks[i]);
        }
    }

    @Test
    void findBackPacks() {
        BackPack[] backpacks = backpackFileDAO.findBackPacks("1");
        assertEquals(1, backpacks.length);
    }

    @Test
    void findBackPackWithoutText() {
        BackPack[] backpacks = backpackFileDAO.findBackPacks(null);
        assertEquals(testBackPacks.length, backpacks.length);
    }

    @Test
    void updateBackPack() {
        BackPack backpack = new BackPack(1, 1, "backpack1", "description for backpack1", "location1", "activity1",
                new int[] { 1, 2, 3 });
        BackPack result = assertDoesNotThrow(() -> backpackFileDAO.updateBackPack(backpack),
                "Unexpected exception thrown");
        assertNotNull(result);
        BackPack realBackPack = backpackFileDAO.getBackPack(backpack.getId());
        assertEquals(realBackPack, backpack);
    }

    @Test
    void testSaveException() throws IOException {
        doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(BackPack[].class));
        BackPack backpack = new BackPack(1, 1, "backpack1", "description for backpack1", "location1", "activity1",
                new int[] { 1, 2, 3 });
        assertThrows(IOException.class, () -> backpackFileDAO.createBackPack(backpack), "IOException not thrown");
    }

    @Test
    void getBackPack() {
        BackPack backpack = backpackFileDAO.getBackPack(1);
        assertEquals(backpack, testBackPacks[0]);
    }

    @Test
    void testCreateBackPack() {
        // Setup
        BackPack backpack = new BackPack(4, 4, "backpack4", "description for backpack4", "location1", "activity1",
                new int[] { 1, 2, 3 });

        // Invoke
        BackPack result = assertDoesNotThrow(() -> backpackFileDAO.createBackPack(backpack),
                "Unexpected exception thrown");

        assertEquals(backpack, result);

    }

    @Test
    void testDeleteBackPack() {
        // new backpack
        BackPack backpack = new BackPack(1, 1, "backpack1", "description for backpack1", "location1", "activity1",
                new int[] { 1, 2, 3 });

        // backpack currently in system with same id
        BackPack realBackPack = backpackFileDAO.getBackPack(backpack.getId());

        // delete backpack
        Boolean result = assertDoesNotThrow(() -> backpackFileDAO.deleteBackPack(realBackPack.getId()),
                "Unexpected exception thrown");

        // check if its there
        assertNotNull(result);

        // delete backpack again
        Boolean result2 = assertDoesNotThrow(() -> backpackFileDAO.deleteBackPack(realBackPack.getId()),
                "Unexpected exception thrown");

        assertTrue(result);
        assertFalse(result2);

    }
}
