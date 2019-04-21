package com.example.sales_partner;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.sales_partner.dao.CustomerDao;
import com.example.sales_partner.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    
    //TAG
    private static final String TAG = "OrdersActivity";

    // DATA OBJECTS
    private OrderDao orderDao;

    // VIEW COMPONENTS
    private Spinner statusSpnr;
    private Spinner customerSpnr;
    private CheckBox dateStartCheckBox;
    private EditText dateStartEditTxt;
    private CheckBox dateEndCheckBox;
    private EditText dateEndEditTxt;
    private ListView orderList;

    //List
    private List<Order> orders;
    private ArrayList<StateVO> listVOs;

    // Adapters
    private ArrayAdapter customersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Log.d(TAG, "onCreate: ");

        orderDao = AppDatabase.getAppDatabase(getApplicationContext()).orderDao();

        // VIEW COMPONENTS INIT
        statusSpnr = findViewById(R.id.categoryOrdersSpnrSelect);
        customerSpnr = findViewById(R.id.clientOrdersSpnrSelect);
        dateStartCheckBox = findViewById(R.id.checkBoxOrdersStartDate);
        dateStartEditTxt = findViewById(R.id.editTextOrdersStartDate);
        dateEndCheckBox = findViewById(R.id.checkBoxOrdersEndDate);
        dateEndEditTxt = findViewById(R.id.editTextOrdersEndDate);
        orderList = findViewById(R.id.orderList);

        // init customer data model
        orders = new ArrayList<Order>();

        // ADAPTERS
        orderAdapter = new ArrayAdapter(this, R.layout.text_list, orders);
        orderList.setAdapter(orderAdapter);

        // field selection options
        String[] customerFields = {"Select field","Pendiente", "Cancelado", "Confirmado", "En transito", "Finalizado"};
        listVOs = new ArrayList<>();

        for (int i = 0; i < customerFields.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(customerFields[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        SpinnerCheckboxAdapter spinnerCheckboxAdapter = new SpinnerCheckboxAdapter(getApplicationContext(), 0,
                listVOs);
        statusSpnr.setAdapter(spinnerCheckboxAdapter);

        List<Order> o = orderDao.getAll();
        this.orders.clear();
        this.orders.addAll(o);

        // notify adapter to update view
        orderAdapter.notifyDataSetChanged();
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


                return true;

            case R.id.add_menu_item:

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
