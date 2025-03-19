package com.edu.duongdua.core.bandoan.Models;

import java.sql.Timestamp;

public class Discount
{
    private int id;
    private String name;
    private int dishId;
    private int discountPrice;
    private Timestamp time;
    private Timestamp createdAt;
    private int status;

    public Discount(int id, String name, int dishId, int discountPrice, Timestamp time, Timestamp createdAt, int status) {
        this.id = id;
        this.name = name;
        this.dishId = dishId;
        this.discountPrice = discountPrice;
        this.time = time;
        this.createdAt = createdAt;
        this.status = status;
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

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
