package com.edu.duongdua.core.bandoan.Models;

public class Dishlist
{
    private int id;
    private String name;

    public Dishlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
