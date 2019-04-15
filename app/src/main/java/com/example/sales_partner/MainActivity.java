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
import com.example.sales_partner.db.dao.ProductCategoryDao;
import com.example.sales_partner.model.ProductCategory;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "inventory.db")
                    .allowMainThreadQueries()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            db.execSQL("INSERT INTO product_categories (id, description) VALUES (0, 'Disco duro')");
                        }
                    })
                    .build();



        //List<ProductCategory> categories = db.productCategoryDao().getAll();
    }
}
