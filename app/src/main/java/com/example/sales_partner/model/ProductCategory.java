package com.example.sales_partner.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="product_categories")
public class ProductCategory {
    @PrimaryKey
    public int id;

    public String description;
}