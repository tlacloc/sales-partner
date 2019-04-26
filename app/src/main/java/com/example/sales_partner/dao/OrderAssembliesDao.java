package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.Assembly;

import java.util.List;

@Dao
public interface OrderAssembliesDao {

    @Query("SELECT * FROM order_assemblies")
    List<Assembly> getAll();

    @Query("SELECT order_assemblies.*,\n" +
            "\tassemblies.description\n" +
            "\tFROM order_assemblies\n" +
            "\tINNER JOIN assemblies ON assemblies.id = order_assemblies.assembly_id\n" +
            "\tWHERE order_assemblies.id = :orderId")
    List<Assembly> findByOrderId(int orderId);

    @Insert
    void insertAll(Assembly assembly);

    @Update
    void update(Assembly... assembly);

    @Delete
    void delete(Assembly... assembly);
}
