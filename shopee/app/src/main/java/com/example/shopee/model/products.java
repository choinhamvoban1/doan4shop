package com.example.shopee.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class products {

    @SerializedName("Id")
    @Expose
    private Integer Id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("type")
    @Expose
    private String type;

    public products(Integer id, String name, Integer price, Integer quantity, String size, String description, String image, String type) {
        Id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.description = description;
        this.image = image;
        this.type = type;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}