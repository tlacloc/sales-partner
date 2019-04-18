package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.OrderAssembly;

import java.util.List;

@Dao
public interface OrderAssemblyDao {
    @Query("SELECT * FROM order_assemblies")
    List<OrderAssembly>getAll();

    @Query("SELECT * FROM order_assemblies WHERE assembly_id LIKE :assId")
    List<OrderAssembly>findByAssembly(int assId);

    @Insert
    void insertAll(OrderAssembly orderAssembly);

    @Update
    void update(OrderAssembly... orderAssemblies);

    @Delete
    void delete(OrderAssembly... orderAssemblies);




}
