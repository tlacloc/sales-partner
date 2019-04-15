package com.example.sales_partner;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.dao.CategoryDao;
import com.example.sales_partner.model.Category;
import com.facebook.stetho.Stetho;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);

        setContentView(R.layout.activity_main);



        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        CategoryDao dgDao = db.productCategoryDao();
        List<Category> productCategories1 = dgDao.getAll();


        Category category = new Category();
        category.setDescription("holo");
        dgDao.insertAll(category);
        List<Category> productCategories2 = dgDao.getAll();
        System.out.println(productCategories1);
        System.out.println(productCategories2);





        //List<ProductCategory> categories = db.productCategoryDao().getAll();
    }
}
