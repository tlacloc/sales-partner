package com.example.sales_partner;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.dao.CategoryDao;
import com.example.sales_partner.model.Category;
import com.facebook.stetho.Stetho;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton productsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);

        setContentView(R.layout.activity_main);

        productsButton = findViewById(R.id.mainButtonProducts);
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntProducts = new Intent(getApplicationContext(),ProductsActivity.class);
                IntProducts.putExtra("tag","start");
                startActivity(IntProducts);

            }
        });


        /*
        Category category = new Category();
        category.setDescription("holo");
        dgDao.insertAll(category);
        List<Category> productCategories2 = dgDao.getAll();
        System.out.println(productCategories1);
        System.out.println(productCategories2);
        */





        //List<ProductCategory> categories = db.productCategoryDao().getAll();
    }
}
