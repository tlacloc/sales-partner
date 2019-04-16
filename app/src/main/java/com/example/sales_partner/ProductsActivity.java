package com.example.sales_partner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sales_partner.dao.CategoryDao;
import com.example.sales_partner.dao.ProductDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.Category;
import com.example.sales_partner.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        CategoryDao dgDao = db.categoryDao();
        final ProductDao productDao = db.productDao();

        // Categoria todas
        Category todas = new Category();
        todas.setDescription("todas");
        todas.setId(-1);

        // Lista de Categorias
        List<Category> categories = new ArrayList<Category>();
        categories.add(todas); // se agrega categoria todas
        categories.addAll(dgDao.getAll()); // se agregan categorias de bd

        // Lista de todos los productos
        final List<Product> products = productDao.getAll();

        final Spinner spinner = findViewById(R.id.Spnrproducts);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(categoryAdapter);

        final ArrayAdapter productsAdapter = new ArrayAdapter(this, R.layout.text_list, products);
        ListView productList = (ListView) findViewById(R.id.productsList);
        productList.setAdapter(productsAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position: " + position + " " + "id: " + id);
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                int catid = selectedCategory.getId();

                //List<Product> p = catid == -1? productDao.getAll() : productDao.findByCategory(catid);
                List<Product> p = catid == -1? productDao.getAll() : productDao.findByCategory(catid);
                System.out.println(p);

                products.clear();
                products.addAll(p);

                // avisa al adaptador que actualice la vista
                productsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("Nothing selected!");
            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.products_menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu_item:
                Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}



