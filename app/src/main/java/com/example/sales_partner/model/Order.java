package com.example.sales_partner.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.sales_partner.dao.CustomerDao;
import com.example.sales_partner.db.AppDatabase;

import java.util.Date;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "status_id")
    private int statusId;

    @ColumnInfo(name = "customer_id")
    private int customerId;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "change_log")
    private String changeLog;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChangeLog() {
        if(changeLog==null)
            return "";
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String toString(){
        return this.date+ " S: " + this.statusId + " c: " + this.customerId;
    }


}
