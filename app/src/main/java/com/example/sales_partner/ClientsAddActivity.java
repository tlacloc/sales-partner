package com.example.sales_partner;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.sales_partner.dao.CustomerDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class ClientsAddActivity extends AppCompatActivity {

    //LOG
    private static final String TAG = "ClientsActivity";

    // DATA OBJECTS
    private CustomerDao customerDao;

    // VIEW COMPONENTS
    EditText txtEditFirstName;
    EditText txtEditLastName;
    Button btnCustomerSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_add);
        Log.d(TAG, "onCreate: ");

        customerDao = AppDatabase.getAppDatabase(getApplicationContext()).customerDao();

        // VIEW COMPONENTS INIT
        txtEditFirstName= findViewById(R.id.txtEditFirstName);
        txtEditLastName = findViewById(R.id.txtEditLastName);
        btnCustomerSave = findViewById(R.id.btnCustomerSave);
        btnCustomerSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = txtEditFirstName.getText().toString();
                String lastName = txtEditLastName.getText().toString();
                Customer newCustomer = new Customer();
                newCustomer.setFirstName(firstName);
                newCustomer.setLastName(lastName);
                customerDao.insertAll(newCustomer);

                Intent IntCustomers = new Intent(getApplicationContext(),ClientsActivity.class);
                IntCustomers.putExtra("tag","start");
                startActivity(IntCustomers);

            }
        });
    }

}