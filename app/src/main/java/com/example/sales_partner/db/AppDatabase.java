package com.example.sales_partner.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.sales_partner.model.ProductCategory;
import com.example.sales_partner.db.dao.ProductCategoryDao;

@Database(entities = {ProductCategory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductCategoryDao productCategoryDao();
}
