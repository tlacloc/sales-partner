package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.AssemblyProduct;

import java.util.List;

@Dao
public interface AssemblyProductDao {

    @Query("SELECT * FROM assembly_products")
    List<AssemblyProduct> getAll();

    @Insert
    void insertAll(AssemblyProduct assemblyProduct);

    @Update
    void update(AssemblyProduct... assemblyProducts);

    @Delete
    void delete(AssemblyProduct... assemblyProducts);

}
