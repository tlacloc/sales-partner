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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.sales_partner.dao.CustomerDao;
import com.example.sales_partner.dao.OrderDao;
import com.example.sales_partner.dao.OrderStatusDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.Customer;
import com.example.sales_partner.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "OrdersActivity";

    // DATA OBJECTS
    private OrderDao orderDao;
    private CustomerDao customerDao;
    private OrderStatusDao orderStatusDao;

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
    private ArrayAdapter orderAdapter;

    //Ints (?)
    private int custId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Log.d(TAG, "onCreate: ");

        orderDao = AppDatabase.getAppDatabase(getApplicationContext()).orderDao();
        customerDao = AppDatabase.getAppDatabase(getApplicationContext()).customerDao();
        orderStatusDao = AppDatabase.getAppDatabase(getApplicationContext()).orderStatusDao();

        // VIEW COMPONENTS INIT
        statusSpnr = findViewById(R.id.statusOrdersSpnrSelect);
        customerSpnr = findViewById(R.id.customerOrdersSpnrSelect);
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
        String[] customerFields = {"Select field", "Pendiente", "Cancelado", "Confirmado", "En transito", "Finalizado"};
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


        //ADDING TODOS AS A OPTION
        Customer todos = new Customer();
        todos.setFirstName("Seleccionar");
        todos.setLastName("todos");
        todos.setId(-1);

        //LISTA DE CLIENTES
        List<Customer> customers = new ArrayList<Customer>();
        customers.add(todos);
        customers.addAll(customerDao.getAll());

        ArrayAdapter customerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, customers);
        customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        customerSpnr.setAdapter(customerAdapter);

        // ADDING ORDERS TO LISTVIEW
        List<Order> o = orderDao.getAll();
        this.orders.clear();
        this.orders.addAll(o);

        // notify adapter to update view
        orderAdapter.notifyDataSetChanged();

        //CUSTOMER SPINNER
        customerSpnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Customer selectedCustomer = (Customer) parent.getItemAtPosition(position);

                custId = selectedCustomer.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

                onSearchMenuClicked();

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

    private void onSearchMenuClicked() {

        List<Order> o = new ArrayList<Order>();

        String query = "SELECT * FROM orders";
        String statusQuery = statusStringQueryMaker();
        String customerQuery = customerAndStatusQueryMaker(custId, statusQuery);
        query = query + customerQuery;


        ArrayList<String> starray = new ArrayList<String>();

        Object[] tmp = starray.toArray();
        SimpleSQLiteQuery q = new SimpleSQLiteQuery(query, tmp);
        List<Order> ord = orderDao.findByQuery(q);

        updateOrders(ord);


    }


    private String customerAndStatusQueryMaker(int customerId, String statusString) {
        String query = "";
        if (customerId == -1) { //ALL CUSTOMERS SELECTED
            //FIND JUST BY STATUS
            if (statusString.equals("")) { //NO STATUS SELECTED
                //DO NOTHING
            } else { //STATUS SELECTED
                query = statusString;
            }
        } else {

            if (statusString == "") {//FIND BY CUSTOMER

                query = " WHERE customer_id = " + customerId;

            } else {//FIND BY CUSTOMER AND STATUS

                query = " WHERE customers like " + customerId + statusString;

            }
        }

        return query;
    }

    private String statusStringQueryMaker() {

        String selectedStatus = "";

        boolean previousConditions = false;

        for (StateVO i : listVOs) {
            if (i.isSelected()) {
                if (previousConditions == false) {
                    if (i.getTitle().equals("Pendiente")) {
                        selectedStatus = " status_id like 0";
                    }
                    if (i.getTitle().equals("Cancelado")) {
                        selectedStatus = " status_id like 1";
                    }
                    if (i.getTitle().equals("Confirmado")) {
                        selectedStatus = " status_id like 2";
                    }
                    if (i.getTitle().equals("En transito")) {
                        selectedStatus = " status_id like 3";
                    }
                    if (i.getTitle().equals("Finalizado")) {
                        selectedStatus = " status_id like 4";
                    }
                    previousConditions = true;
                } else {
                    if (i.getTitle().equals("Pendiente")) {
                        selectedStatus = selectedStatus + " OR status_id like 0";
                    }
                    if (i.getTitle().equals("Cancelado")) {
                        selectedStatus = selectedStatus + " OR status_id like 1";
                    }
                    if (i.getTitle().equals("Confirmado")) {
                        selectedStatus = selectedStatus + " OR status_id like 2";
                    }
                    if (i.getTitle().equals("En transito")) {
                        selectedStatus = selectedStatus + " OR status_id like 3";
                    }
                    if (i.getTitle().equals("Finalizado")) {
                        selectedStatus = selectedStatus + " OR status_id like 4";
                    }
                }
            }
        }

        if (selectedStatus != "") {
            if (custId == -1) {
                selectedStatus = " WHERE" + selectedStatus;
            } else {
                selectedStatus = " AND"+ selectedStatus;
            }

        }

        return selectedStatus;
    }

    private void updateOrders(List<Order> newOrder) {

        //ADD ORDERS TO MODEL
        this.orders.clear();
        this.orders.addAll(newOrder);

        // notify adapter to update view
        orderAdapter.notifyDataSetChanged();

    }
}


