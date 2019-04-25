package com.example.sales_partner;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sales_partner.dao.CustomerDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.Category;
import com.example.sales_partner.model.Customer;
import com.example.sales_partner.model.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientsActivity extends AppCompatActivity {

    //LOG
    private static final String TAG = "ClientsActivity";

    // DATA OBJECTS
    private CustomerDao customerDao;

    // VIEW COMPONENTS
    private Spinner spinner;
    private EditText searchEditText;
    private ListView customerList;

    // Lista de todos los clientes
    private List<Customer> customers;
    private ArrayList<StateVO> listVOs;

    // Adapters
    private ArrayAdapter customersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        Log.d(TAG, "onCreate: ");

        customerDao = AppDatabase.getAppDatabase(getApplicationContext()).customerDao();

        // VIEW COMPONENTS INIT
        spinner = findViewById(R.id.Spnrclients);
        searchEditText = findViewById(R.id.clientSearchTxt);
        customerList = findViewById(R.id.clientList);

        // init customer data model
        customers = new ArrayList<Customer>();

        // ADAPTERS
        customersAdapter = new ArrayAdapter(this, R.layout.text_list, customers);
        customerList.setAdapter(customersAdapter);

        // field selection options
        String[] customerFields = {"Select field","nombre", "apellido", "telefono", "email", "dirección"};
        listVOs = new ArrayList<>();

        for (int i = 0; i < customerFields.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(customerFields[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }

        SpinnerCheckboxAdapter myAdapter = new SpinnerCheckboxAdapter(getApplicationContext(), 0,
                listVOs);
        spinner.setAdapter(myAdapter);

        List<Customer> c = customerDao.getAll();

        // add products to model
        this.customers.clear();
        this.customers.addAll(c);

        // notify adapter to update view
        customersAdapter.notifyDataSetChanged();
    }

    //GENERATE TOOLBAR MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clients_menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // SEARCH BUTTON ACTION
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu_item:

                // text to search
                String searchText = searchEditText.getText().toString();

                String query = "SELECT * FROM customers";
                String orderby = " ORDER BY last_name ASC, first_name ASC";
                String conditions = "";
                ArrayList<String> starray = new ArrayList<String>();

                boolean conditionsb = false;

                for (StateVO i : listVOs){
                    if(i.isSelected()){
                        starray.add("%" + searchText + "%");
                        if(conditionsb) {
                            // Hay mas de una condición, poner OR
                            conditions += " OR";
                        }

                        if(i.getTitle() == "nombre")
                            conditions += " first_name like ?";

                        if(i.getTitle() == "apellido")
                            conditions += " last_name like ?";

                        if(i.getTitle() == "telefono") {
                            conditions += " phone1 like ?";
                            conditions += " OR phone2 like ?";
                            conditions += " OR phone3 like ?";
                        }

                        if(i.getTitle() == "email")
                            conditions += " e_mail like ?";

                        if(i.getTitle() == "dirección")
                            conditions += " address like ?";

                        conditionsb = true;
                    }
                }
                if(conditionsb){
                    conditions = " WHERE" + conditions;
                }

                query = query + conditions + orderby;

                Object[] tmp = starray.toArray();

                SimpleSQLiteQuery q = new SimpleSQLiteQuery(query, tmp);
                List<Customer> cust = customerDao.findByQuery(q);

                // add products to model
                this.customers.clear();
                this.customers.addAll(cust);

                // notify adapter to update view
                customersAdapter.notifyDataSetChanged();

                return true;

            case R.id.add_menu_item:
                Intent IntCustomers = new Intent(getApplicationContext(),ClientsAddActivity.class);
                IntCustomers.putExtra("tag","start");
                startActivity(IntCustomers);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.clients_menu_toolbar, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu_item:

                return true;

            case R.id.add_menu_item:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}