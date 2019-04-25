package com.example.sales_partner.model;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.room.RawQuery;

import java.util.List;

public class OrderCustomer {
   public int id;
   public String customerName;
   public String status;
   public String date;
   //public int assemblies;
   //public int cost;


   public String toString() {
      return this.customerName + " " + this.status + " " + this.date;
   }


}
