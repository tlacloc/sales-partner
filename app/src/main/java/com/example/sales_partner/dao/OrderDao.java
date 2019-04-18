package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.Order;

import java.util.Date;
import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders")
    List<Order> getAll();

    @Query("SELECT * FROM orders LIKE :statId")
    List<Order> findByStatus(int statId);

    @Query("SELECT * FROM orders LIKE :custId")
    List<Order> findByCustomer(int custId);

    @Query("SELECT * FROM orders LIKE :date")
    List<Order> findByDate(Date date);

    @Insert
    void insertAll(Order order);

    @Update
    void update(Order... orders);

    @Delete
    void delete(Order... orders);


}
