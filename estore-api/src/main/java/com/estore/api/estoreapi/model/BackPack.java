package com.estore.api.estoreapi.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BackPack {
    @JsonProperty("id")
    private int id;
    @JsonProperty("userId")
    private int userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("location")
    private String location;
    @JsonProperty("activity")
    private String activity;
    @JsonProperty("productId")
    private int[] productId;

    public BackPack(@JsonProperty("id") int id,
            @JsonProperty("userId") int userId, @JsonProperty("name") String name,
            @JsonProperty("description") String description, @JsonProperty("location") String location,
            @JsonProperty("activity") String activity, @JsonProperty("productId") int[] productId) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.activity = activity;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int[] getProductId() {
        return productId;
    }

    public void setProductId(int[] productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "BackPack [id = " + id + ", userId = "
                + userId + ", name = " + name + ", description = " + description + ", location = "
                + location + ", activity = " + activity + ",  productId = " + Arrays.toString(productId) + "]";
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BackPack other = (BackPack) obj;
        return id == other.id;
    }

}
