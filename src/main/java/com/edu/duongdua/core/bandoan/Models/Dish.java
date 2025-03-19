package com.edu.duongdua.core.bandoan.Models;

public class Dish
{
    private int id;
    private String name;
    private int price;
    private String description;
    private int dishListId;
    private String size;
    private int status;
    private int mustTry;
    private int quantity;
    private int totalSold;

    public Dish(int id, String name, int price, String description, int dishListId, String size, int status, int mustTry, int quantity, int totalSold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.dishListId = dishListId;
        this.size = size;
        this.status = status;
        this.mustTry = mustTry;
        this.quantity = quantity;
        this.totalSold = totalSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDishListId() {
        return dishListId;
    }

    public void setDishListId(int dishListId) {
        this.dishListId = dishListId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMustTry() {
        return mustTry;
    }

    public void setMustTry(int mustTry) {
        this.mustTry = mustTry;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }
}
