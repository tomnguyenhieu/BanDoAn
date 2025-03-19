package com.edu.duongdua.core.bandoan.Models;

public class Discount
{
    private int id;
    private double percentage;
    private int dishId;

    public Discount(int id, double percentage, int dishId) {
        this.id = id;
        this.percentage = percentage;
        this.dishId = dishId;
    }

    public int getId() { return id; }
    public double getPercentage() { return percentage; }
    public int getDishId() { return dishId; }

    public void setId(int id) { this.id = id; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
    public void setDishId(int dishId) { this.dishId = dishId; }
}
