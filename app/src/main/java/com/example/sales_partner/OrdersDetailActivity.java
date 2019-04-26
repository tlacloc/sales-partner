package com.example.sales_partner;

import android.app.DatePickerDialog;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sales_partner.dao.CustomerDao;
import com.example.sales_partner.dao.OrderCustomerDao;
import com.example.sales_partner.dao.OrderDao;
import com.example.sales_partner.dao.OrderStatusDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.Customer;
import com.example.sales_partner.model.Order;
import com.example.sales_partner.model.OrderCustomer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrdersDetailActivity extends AppCompatActivity {

    private static final String CERO = "0";
    private static final String BARRA = "-";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //TAG
    private static final String TAG = "OrdersActivity";

    // DATA OBJECTS
    private OrderDao orderDao;
    private CustomerDao customerDao;
    private OrderStatusDao orderStatusDao;
    private OrderCustomerDao orderCustomerDao;

    // VIEW COMPONENTS
    private Spinner statusSpnr;
    private Spinner customerSpnr;

    private CheckBox dateEndCheckBox;
    private CheckBox dateStartCheckBox;

    private EditText dateStartEditTxt;
    private EditText dateEndEditTxt;

    private Button dateEndBtn;
    private Button dateStartBtn;
    private ListView orderList;

    //List
    private List<OrderCustomer> orders;
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
        orderCustomerDao = AppDatabase.getAppDatabase(getApplicationContext()).orderCustomerDao();

        // VIEW COMPONENTS INIT
        statusSpnr = findViewById(R.id.statusOrdersSpnrSelect);
        customerSpnr = findViewById(R.id.customerOrdersSpnrSelect);

        dateEndCheckBox = findViewById(R.id.checkBoxOrdersEndDate);
        dateStartCheckBox = findViewById(R.id.checkBoxOrdersStartDate);

        dateStartEditTxt = findViewById(R.id.editTextOrdersStartDate);
        dateEndEditTxt = (EditText) findViewById(R.id.editTextOrdersEndDate);

        dateEndBtn = findViewById(R.id.btnOrdersEndDate);
        dateStartBtn = findViewById(R.id.btnOrdersStartDate);

        orderList = findViewById(R.id.orderList);

        // init customer data model
        orders = new ArrayList<OrderCustomer>();

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



    }



    private void updateOrders(List<OrderCustomer> newOrder) {

        //ADD ORDERS TO MODEL
        this.orders.clear();
        this.orders.addAll(newOrder);

        // notify adapter to update view
        orderAdapter.notifyDataSetChanged();

    }
}


