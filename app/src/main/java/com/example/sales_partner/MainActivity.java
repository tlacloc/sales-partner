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

    //TAG
    private static final String TAG = "MainActivity";

    //VIEW COMPONENTS
    private ImageButton productsButton;
    private ImageButton assembliesButton;
    private ImageButton customersButton;
    private ImageButton ordersButton;
    private ImageButton reportsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);

        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        // VIEW COMPONENTS INIT
        productsButton = findViewById(R.id.mainButtonProducts);
        assembliesButton = findViewById(R.id.mainButtonAssembly);
        customersButton = findViewById(R.id.mainButtonCustomers);
        ordersButton = findViewById(R.id.mainButtonOrders);
        reportsButton = findViewById(R.id.mainButtonReports);


        //OnClick Events
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: productsButton");
                Intent IntProducts = new Intent(getApplicationContext(),ProductsActivity.class);
                IntProducts.putExtra("tag","start");
                startActivity(IntProducts);

            }
        });
        assembliesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: assembliesButton");
                Intent IntAssemblies = new Intent(getApplicationContext(),AssembliesActivity.class);
                IntAssemblies.putExtra("tag","start");
                startActivity(IntAssemblies);

            }
        });
        customersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: customersButton");
                Intent IntCustomers = new Intent(getApplicationContext(),ClientsActivity.class);
                IntCustomers.putExtra("tag","start");
                startActivity(IntCustomers);

            }
        });
        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ordersButton");
                Intent IntOrders = new Intent(getApplicationContext(),OrdersActivity.class);
                IntOrders.putExtra("tag","start");
                startActivity(IntOrders);
            }
        });
        reportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: reportsButton");
                Intent IntReports = new Intent(getApplicationContext(),ReportsActivity.class);
                IntReports.putExtra("tag","start");
                startActivity(IntReports);
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
