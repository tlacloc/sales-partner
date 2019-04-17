package com.example.sales_partner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sales_partner.dao.AssemblyDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.Assembly;
import com.example.sales_partner.model.Category;
import com.example.sales_partner.model.Product;

import java.util.ArrayList;
import java.util.List;

public class AssembliesActivity extends AppCompatActivity {
    
    //TAG
    private static final String TAG = "AssembliesActivity";

    //DATA OBJECTS
    private AssemblyDao assemblyDao;

    // VIEW COMPONENTS
    private EditText searchEditText;

    // Lista de todos los ensambles
    private List<Assembly> assemblies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemblies);
        Log.d(TAG, "onCreate: ");

        //DB INIT
        assemblyDao = AppDatabase.getAppDatabase(getApplicationContext()).assemblyDao();

        // VIEW COMPONENTS INIT
        searchEditText = findViewById(R.id.assembliesTxt);
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

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

}
