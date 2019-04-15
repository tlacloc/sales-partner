package com.example.sales_partner.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "category_id")
    private int categoryId;

    @ColumnInfo(name = "description")
    private String description;

    private int price;

    @ColumnInfo(name = "qty")
    private int quanity;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // categoryID getter setter
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // description getter setter
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    // price getter setter
    public int getPrice() { return price; }
    public void setPrice(int categoryId) {
        this.price= price;
    }

    // quantity getter setter
    public int getQuanity() { return quanity; }
    public void setQuanity(int categoryId) {
        this.quanity= quanity;
    }
}