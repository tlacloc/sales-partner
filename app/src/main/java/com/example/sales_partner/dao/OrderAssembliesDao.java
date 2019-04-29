package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.Assembly;
import com.example.sales_partner.model.OrderAssemblies;

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
    long[] insertAll(OrderAssemblies... assembly);

    @Update
    void update(OrderAssemblies... assembly);

    @Delete
    void delete(OrderAssemblies... assembly);

    @Query("DELETE FROM order_assemblies WHERE id=:orderId")
    void deleteByOrderId(int orderId);
}
