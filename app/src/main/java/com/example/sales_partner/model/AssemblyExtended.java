package com.example.sales_partner.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

public class AssemblyExtended extends Assembly{

    private int numProducts;
    private int price;

    public int getNumProducts() {
        return numProducts;
    }

    public void setNumProducts(int numProducts) {
        this.numProducts = numProducts;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String toString() {
        int descriptionLength =  20;
        String desc = "";
        if(this.getDescription().length() > descriptionLength)
            desc = this.getDescription().substring(0,descriptionLength);
        else desc = this.getDescription();

        return desc + " " + this.getNumProducts() + " " + this.getPrice();
    }



}

