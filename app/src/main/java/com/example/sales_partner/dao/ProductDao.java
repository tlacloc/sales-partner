package com.example.sales_partner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sales_partner.model.NeededProducts;
import com.example.sales_partner.model.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products ORDER BY description ASC")
    List<Product> getAll();

    @Query("SELECT * FROM products WHERE category_id = :catId ORDER BY description ASC")
    List<Product> findByCategory(int catId);

    @Query("SELECT * FROM products WHERE description LIKE :desc ORDER BY description ASC")
    List<Product> findByDescription(String desc);

    @Query("SELECT * FROM products WHERE description LIKE :desc AND category_id = :catId ORDER BY description ASC")
    List<Product> findByCategoryAndDescription(int catId, String desc);

    @Query("SELECT\n" +
            "orders.id as orderId,\n" +
            "orders.status_id as statusId,\n" +
            "orders.customer_id as customerId,\n" +
            "order_assemblies.assembly_id as assembly_id,\n" +
            "order_assemblies.qty as assembliesQty,\n" +
            "assembly_products.product_id as productId,\n" +
            "assembly_products.qty as productQtyPerAssembly,\n" +
            "SUM(order_assemblies.qty*assembly_products.qty) as pendingProductsQty,\n" +
            "SUM(products.qty - order_assemblies.qty*assembly_products.qty) as diffProductsQty," +
            "products.qty as stockProductsQty,\n" +
            "products.description as productDescription\n" +
            "FROM orders\n" +
            "INNER JOIN order_assemblies ON order_assemblies.id = orders.id\n" +
            "INNER JOIN assembly_products ON assembly_products.id = order_assemblies.assembly_id\n" +
            "INNER JOIN products ON products.id = product_id\n" +
            "GROUP BY products.id \n" +
            "HAVING diffProductsQty < 0")
    List<NeededProducts> findNeededProducts();


    @Insert
    void insertAll(Product product);

    @Update
    void update(Product... product);

    @Delete
    void delete(Product... product);
}