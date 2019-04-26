package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.Assembly;
import com.example.sales_partner.model.Product;

import java.util.List;

@Dao
public interface AssemblyProductsDao {

    @Query("SELECT * FROM assembly_products")
    List<Assembly> getAll();

    @Query("SELECT \n" +
            "products.id, \n" +
            "products.category_id,\n" +
            "products.description, \n" +
            "products.price, \n" +
            "assembly_products.qty \n" +
            "FROM products \n" +
            "INNER JOIN assembly_products ON assembly_products.product_id = products.id\n" +
            "WHERE assembly_products.id = :assemblyId\n" +
            "ORDER BY description ASC")
    List<Product> findByAssemblyId(int assemblyId);

    @Insert
    void insertAll(Assembly assembly);

    @Update
    void update(Assembly... assembly);

    @Delete
    void delete(Assembly... assembly);
}
