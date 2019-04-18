package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.sales_partner.model.OrderStatus;

import java.util.List;

@Dao
public interface OrderStatusDao {
    @Query("SELECT * FROM order_status")
    List<OrderStatus> getAll();
    @Query("SELECT * FROM order_status WHERE description LIKE :desc")
    List<OrderStatus> findByDescription(String desc);

}
