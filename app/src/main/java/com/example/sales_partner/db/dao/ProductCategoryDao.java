package com.example.sales_partner.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.sales_partner.model.ProductCategory;

import java.util.List;

@Dao
public interface ProductCategoryDao {
    //@Query("SELECT * FROM product_categories")
    //LiveData<List<CommentEntity>> findAll(int productId);

    @Query("SELECT * FROM product_categories")
    List<ProductCategory> getAll();

    @Insert
    void insertAll(ProductCategory productCategory1);
}