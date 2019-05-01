package com.example.sales_partner;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sales_partner.dao.CategoryDao;
import com.example.sales_partner.dao.ProductDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.Category;
import com.example.sales_partner.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "ProductsActivity";

    // DATA OBJECTS
    private ProductDao productDao;
    private CategoryDao categoryDao;


    // VIEW COMPONENTS
    private Spinner spinner;
    private EditText searchEditText;

    // Lista de todos los productos
    private List<Product> products;

    // Adapters
    private ArrayAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Log.d(TAG, "onCreate: ");

        //AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        productDao = AppDatabase.getAppDatabase(getApplicationContext()).productDao();
        categoryDao = AppDatabase.getAppDatabase(getApplicationContext()).categoryDao();

        // VIEW COMPONENTS INIT
        spinner = findViewById(R.id.Spnrproducts);
        searchEditText = findViewById(R.id.productSearchTxt);
        products = new ArrayList<Product>();

        // ADAPTERS
        productsAdapter = new ArrayAdapter(this, R.layout.text_list, products);

        // Categoria todas
        Category todas = new Category();
        todas.setDescription("Todas");
        todas.setId(-1);

        // Lista de Categorias
        List<Category> categories = new ArrayList<Category>();
        categories.add(todas); // se agrega categoria todas
        categories.addAll(categoryDao.getAll()); // se agregan categorias de bd


        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(categoryAdapter);

        ListView productList = (ListView) findViewById(R.id.productsList);
        productList.setAdapter(productsAdapter);
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = (Product) productsAdapter.getItem(position);
                AlertDialog alertDialog = new AlertDialog.Builder(parent.getContext())
                        .setTitle("Producto")
                        .setMessage(
                            "Producto: " + selectedProduct.getDescription() + " " + "\n\n" +
                            "Precio: $" + selectedProduct.getPrice() + " " + "\n\n" +
                            "Cantidad: " + selectedProduct.getQuantity() + " " + "\n"
                        )
                        .show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // SPINNER SELECTION ACTIONS
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get category id
                Category selectedCategory = (Category) parent.getItemAtPosition(position);
                int catid = selectedCategory.getId();

                // text to search
                String query = searchEditText.getText().toString();

                getProductsByCategoryAndDescription(catid, query);

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


    // SEARCH BUTTON ACTION
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu_item:
                // text to search
                String query = searchEditText.getText().toString();

                // current selected category if any
                Category selectedCat = (Category)spinner.getSelectedItem();
                int catId = selectedCat.getId();

                // get products by cat and description
                getProductsByCategoryAndDescription(catId, query);

                Toast.makeText(this, query, Toast.LENGTH_SHORT).show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.products_menu_toolbar, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu_item:
                Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Gets Products and updates view
     * @param catId category ID
     * @param description the description to search
     */
    private void getProductsByCategoryAndDescription( int catId, String description){
        description = "%" + description + "%";
        List<Product> p = new ArrayList<Product>();

        if(catId == -1){ // Categoria TODAS
            p = productDao.findByDescription(description);
        } else{
            p = productDao.findByCategoryAndDescription(catId, description);
        }

        // add products to model
        this.products.clear();
        this.products.addAll(p);

        // notify adapter to update view
        productsAdapter.notifyDataSetChanged();
    }

}
