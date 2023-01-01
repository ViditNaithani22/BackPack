package com.estore.api.estoreapi.model;

import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

@Tag("Model-tier")
class BackPackTest {

        @Test
        void createBackPack() {
                int[] products = new int[] { 2, 3, 4 };
                BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                                "this backpack is for those who want to go fishing at lake Onterio",
                                "Lake Onterio Rochester NewYork", "fishing", products);

                assertEquals(1, backpack.getId());
                assertEquals("Lake Onterio fishing", backpack.getName());
                assertEquals("this backpack is for those who want to go fishing at lake Onterio",
                                backpack.getDescription());
                assertEquals("Lake Onterio Rochester NewYork", backpack.getLocation());
                assertEquals("fishing", backpack.getActivity());
                assertEquals(true, Arrays.equals(products, backpack.getProductId()));

        }

        @Test
        void testhashCode() {
                int[] products = new int[] { 2, 3, 4 };
                BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                                "this backpack is for those who want to go fishing at lake Onterio",
                                "Lake Onterio Rochester NewYork", "fishing", products);
                assertEquals(1, backpack.hashCode());
        }

        @Test
        void testEqualsNull() {
                int[] products = new int[] { 2, 3, 4 };
                BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                                "this backpack is for those who want to go fishing at lake Onterio",
                                "Lake Onterio Rochester NewYork", "fishing", products);
                assertNotEquals(null, backpack);
        }

        @Test
        void testEqualsDifferentObjects() {
                Product product = new Product(1, "Fishing rod", "Can be used for fishing", 35.0, 10, "fish",
                                "http://www.google.com");

                int[] products = new int[] { 2, 3, 4 };
                BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                                "this backpack is for those who want to go fishing at lake Onterio",
                                "Lake Onterio Rochester NewYork", "fishing", products);
                assertNotEquals(backpack, product);
        }

        @Test
        void testEqualsProductWithDifferentId() {
                int[] products = new int[] { 2, 3, 4 };
                BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                                "this backpack is for those who want to go fishing at lake Onterio",
                                "Lake Onterio Rochester NewYork", "fishing", products);
                BackPack backpack2 = new BackPack(2, 2, "Lake Onterio fishing",
                                "this backpack is for those who want to go fishing at lake Onterio",
                                "Lake Onterio Rochester NewYork", "fishing", products);
                assertNotEquals(backpack, backpack2);
        }

        @Test
        void testEqualsWithSameId() {
                int[] products = new int[] { 2, 3, 4 };
                BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                                "this backpack is for those who want to go fishing at lake Onterio",
                                "Lake Onterio Rochester NewYork", "fishing", products);
                BackPack backpack2 = new BackPack(1, 1, "Lake Onterio fishing",
                                "this backpack is for those who want to go fishing at lake Onterio",
                                "Lake Onterio Rochester NewYork", "fishing", products);
                assertEquals(backpack, backpack2);
        }

        @Test
        void testSetProductId() {
                int[] products = new int[] { 2, 3, 4 };
                int[] products2 = new int[] { 4, 5 };
                BackPack backpack = new BackPack(1, 1, "Lake Onterio fishing",
                                "this backpack is for those who want to go fishing at lake Onterio",
                                "Lake Onterio Rochester NewYork", "fishing", products);
                assertFalse(Arrays.equals(products2, backpack.getProductId()));
                backpack.setProductId(products2);
                assertTrue(Arrays.equals(products2, backpack.getProductId()));
        }
}
