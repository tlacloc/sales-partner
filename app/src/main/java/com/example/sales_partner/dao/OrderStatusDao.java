package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.sales_partner.model.OrderStatus;

import java.util.List;

@Dao
public interface OrderStatusDao {
    @Query("SELECT * FROM order_status")
    List<OrderStatus> getAll();

    @Query("SELECT *\n" +
            " FROM order_status\n" +
            " WHERE id IN (:ids)")
    List<OrderStatus> findNextStatus(List<Integer> ids);

    @Query("SELECT * FROM order_status WHERE description LIKE :desc")
    List<OrderStatus> findByDescription(String desc);

}
