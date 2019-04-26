package com.example.sales_partner.dao;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders")
    List<Order> getAll();

    @Query("SELECT * FROM orders WHERE status_id LIKE :statId")
    List<Order> findByStatus(int statId);

    @Query("SELECT * FROM orders WHERE customer_id LIKE :custId")
    List<Order> findByCustomer(int custId);

    @Query("SELECT * FROM orders WHERE customer_id LIKE :custId AND status_id LIKE :statId")
    List<Order> findByCustomerAndStatus(int custId, int statId);

    @Query("SELECT * FROM orders WHERE date LIKE :date")
    List<Order> findByDate(String date);

    @Query("SELECT * FROM orders WHERE id LIKE :id")
    Order findById(int id);

    @RawQuery
    List<Order> findByQuery(SimpleSQLiteQuery query);

    @Insert
    void insertAll(Order order);

    @Update
    void update(Order... orders);

    @Delete
    void delete(Order... orders);


}
