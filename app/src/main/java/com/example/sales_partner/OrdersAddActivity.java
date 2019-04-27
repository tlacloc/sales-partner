package com.example.sales_partner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sales_partner.dao.AssemblyDao;
import com.example.sales_partner.dao.CustomerDao;
import com.example.sales_partner.dao.OrderAssembliesDao;
import com.example.sales_partner.dao.OrderDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.AssemblyExtended;
import com.example.sales_partner.model.Customer;
import com.example.sales_partner.model.Order;
import com.example.sales_partner.model.OrderAssemblies;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersAddActivity extends AppCompatActivity {

    // DATA OBJECTS
    private CustomerDao customerDao;
    private AssemblyDao assemblyDao;
    private OrderAssembliesDao orderAssembliesDao;
    private OrderDao ordersDao;

    // Models
    private List<Customer> customers;
    private List<AssemblyExtended> assemblies;
    private ViewModel model;

    class ViewModel{
        public List<Customer> customers;
    }


    //////////////////////
    // GETTERS AND SETTERS
    /////////////////////


    // VIEW COMPONENTS
    Spinner customerSpinner;
    ListView assemblyListView;
    Button saveButton;

    //
    private ArrayAdapter adapter;


    private boolean saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_add);
        //ActivityOrdersAddBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_orders_add);

        // Get User Dao
        customerDao = AppDatabase.getAppDatabase(getApplicationContext()).customerDao();
        assemblyDao = AppDatabase.getAppDatabase(getApplicationContext()).assemblyDao();
        ordersDao = AppDatabase.getAppDatabase(getApplicationContext()).orderDao();
        orderAssembliesDao = AppDatabase.getAppDatabase(getApplicationContext()).orderAssembliesDao();

        // VIEW COMPONENTS INIT
        customerSpinner = findViewById(R.id.sprOrderAddCustomer);
        assemblyListView = findViewById(R.id.lvOrderAddAssemblies);
        saveButton = findViewById(R.id.btnAddOrder);

        // GET DATA FROM DB
        model = new ViewModel();
        customers = customerDao.getAll();
        model.customers = customers;
        assemblies = assemblyDao.getExtendedByDescription("%%");

        // Define Adapters
        ArrayAdapter customersAdapter = new ArrayAdapter(this, R.layout.text_list, customers);
        adapter = new AssemblyListAdapter(this, android.R.id.text1, assemblies);

        // SAVE Button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(OrdersAddActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirmacion")
                        .setCancelable(false)
                        .setMessage("Revisa tu pedido, esta acción no podrá deshacerse")
                        .setPositiveButton("Agregar Orden", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ////// ADD ORDER TO DATABASE, SAVE DATE AND STATUS TO PENDIENTE
                                try {
                                    saveAll();
                                    Toast.makeText(OrdersAddActivity.this,"Orden Guardada", Toast.LENGTH_LONG).show();
                                } catch (Exception e){
                                    Toast.makeText(OrdersAddActivity.this,"Error al guardar", Toast.LENGTH_LONG).show();
                                };

                                Intent intent = new Intent(getApplicationContext(),OrdersActivity.class);
                                intent.putExtra("tag","start");
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton("Regresar", null)
                        .show();

            }
        });

        // SET ADAPTERS
        customerSpinner.setAdapter(customersAdapter);
        assemblyListView.setAdapter(adapter);


        saved = false;




    }

    private void saveAll() {
        AppDatabase db = AppDatabase.getAppDatabase(getApplicationContext());
        db.beginTransaction();
        try{
            //do multiple database operations here
            long id = saveOrder(); // gets data and saves order
            long[] ids = saveOrderAssemblies((int) id);

            //which throws exceptions on error
            db.setTransactionSuccessful();
            //do not any more database operations between
            //setTransactionSuccessful and endTransaction
        }catch(Exception e){
            //end the transaction on error too when doing exception handling
            db.endTransaction();
            throw e;
        }
        //end the transaction on no error
        db.endTransaction();
    }

    private long[] saveOrderAssemblies(int id) {
        List<OrderAssemblies> assembliesToSave = new ArrayList<OrderAssemblies>();
        for (AssemblyExtended assembly : assemblies) {
            int assemblyId = assembly.getId();
            int qty = assembly.getQty();
            if(qty==0)
                continue;

            OrderAssemblies newOrderAssembly = new OrderAssemblies();
            newOrderAssembly.setQty(qty);
            newOrderAssembly.setId(id);
            newOrderAssembly.setAssemblyId(assemblyId);
            assembliesToSave.add(newOrderAssembly);
        }
        OrderAssemblies[] arr = assembliesToSave.toArray(new OrderAssemblies[0]);

        // SAVE TO DATABASE
        long[] ids = orderAssembliesDao.insertAll(arr);

        return ids;
    }

    private long saveOrder() {
        // Get customer id
        Customer selectedCustomer = (Customer) customerSpinner.getSelectedItem();
        // Get current date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();

        // Vars to save
        int customerId = selectedCustomer.getId();
        int statusId = 0; ///// 0 = Pendiente
        String date = dateFormat.format(d);

        Order newOrder = new Order();
        newOrder.setCustomerId(customerId);
        newOrder.setStatusId(statusId);
        newOrder.setDate(date);

        // SAVE TO DATABASE
        long[] ids  = ordersDao.insertAll(newOrder);

        // get the id of the saved order
        long id = -1;

        if(ids.length>0) // validate
            id = ids[0];

        return id;
    }


    //CONFIRM DELETE UNSAVED DATA
    @Override
    public void onBackPressed() {
        if (!saved) {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Cerrando ventana")
                    .setCancelable(false)
                    .setMessage("¿Está seguro que desea salir sin guardar")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}