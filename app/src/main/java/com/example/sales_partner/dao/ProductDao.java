package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    List<Product> getAll();

    @Query("SELECT * FROM products WHERE category_id = :catId")
    List<Product> findByCategory(int catId);

    @Query("SELECT * FROM products WHERE description LIKE :desc")
    List<Product> findByDescription(int desc);

    @Insert
    void insertAll(Product product);

    @Update
    void update(Product... product);

    @Delete
    void delete(Product... product);
}