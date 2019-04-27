package com.example.sales_partner;

import android.app.AlertDialog;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sales_partner.dao.CustomerDao;
import com.example.sales_partner.dao.OrderDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class ClientsActivity extends AppCompatActivity {

    //LOG
    private static final String TAG = "ClientsActivity";

    // DATA OBJECTS
    private CustomerDao customerDao;
    private OrderDao orderDao;


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
        orderDao = AppDatabase.getAppDatabase(getApplicationContext()).orderDao();

        // VIEW COMPONENTS INIT
        spinner = findViewById(R.id.Spnrclients);
        searchEditText = findViewById(R.id.clientSearchTxt);
        customerList = findViewById(R.id.clientList);

        // ON CLICK LIST ITEM CUSTOMER --> EDIT CUSTOMER
        customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer selectedCustomer = (Customer) customersAdapter.getItem(position);
                Intent IntCustomers = new Intent(getApplicationContext(),ClientsAddActivity.class);
                IntCustomers.putExtra("customer",selectedCustomer);
                startActivity(IntCustomers);
                finish();
            }
        });



        // init customer data model
        customers = new ArrayList<Customer>();

        // ADAPTERS
        customersAdapter = new ArrayAdapter(this, R.layout.text_list, customers);
        customerList.setAdapter(customersAdapter);

        //Lista
        //private ListView clientList;

        // field selection options
        String[] customerFields = {"Select field","nombre", "apellido", "telefono", "email", "dirección"};
        listVOs = new ArrayList<>();

        for (int i = 0; i < customerFields.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(customerFields[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }

        SpinnerCheckboxAdapter myAdapter= new SpinnerCheckboxAdapter(getApplicationContext(), 0, listVOs);
        spinner.setAdapter(myAdapter);

        List<Customer> c = customerDao.getAll();

        // add products to model
        this.customers.clear();
        this.customers.addAll(c);

        // notify adapter to update view
        customersAdapter.notifyDataSetChanged();

        // REGISTRAR CONTEXT MENU
        //Registrar los controles para menus contextuales
        registerForContextMenu(customerList);
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
                /// SEARCH
                String searchText = searchEditText.getText().toString(); // text to search
                List<Customer> customersResult = this.search(searchText);

                this.refreshCustomerList(customersResult);

                return true;

            case R.id.add_menu_item:

                Intent IntCustomers = new Intent(getApplicationContext(),ClientsAddActivity.class);
                IntCustomers.putExtra("tag","start");
                startActivity(IntCustomers);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

   // CONTEXT MENU OVERRIDES
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        int id= v.getId();

        MenuInflater inflater= getMenuInflater();

        switch (id){
            case R.id.clientList:

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Customer selectedCustomer = (Customer) customersAdapter.getItem(info.position);
                List orders = orderDao.findByCustomer(selectedCustomer.getId());

                inflater.inflate(R.menu.menu_contextual_clientes , menu);

                // HIDE IF CUSTOMER HAS ORDERS
                if(orders.size()>0){
                    MenuItem menuItem = menu.findItem(R.id.action_delete);
                    menuItem.setVisible(false);
                }

                break;
        }
    }
    //Accion de seleccionar opcion en el menu contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final Customer selectedCustomer = (Customer) customersAdapter.getItem(info.position);

        switch (item.getItemId()) {
            //Elementos del menu contextual clientes
            case R.id.action_details:
                //////////// DETALLES DEL CLIENTE

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Cliente")
                        .setMessage(
                                "Nombre: " + selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName() + "\n\n" +
                                "Telefono(s): " + selectedCustomer.getPhone1() + " " + selectedCustomer.getPhone2() + " " + selectedCustomer.getPhone3() +  "\n\n" +
                                "Direccion: \n" + selectedCustomer.getAddress() + " " + "\n\n" +
                                "Email: " + selectedCustomer.getEmail() + " " + "\n"

                        )
                        .show();
                Toast.makeText(ClientsActivity.this, "Detalles del cliente", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_edit:
                //////////// EDITAR CLIENTE
                Intent IntCustomers = new Intent(getApplicationContext(),ClientsAddActivity.class);
                IntCustomers.putExtra("customer",selectedCustomer);
                startActivity(IntCustomers);
                finish();

                Toast.makeText(ClientsActivity.this, "Editar cliente", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                ///////////// SELECCIONADO CONTEXT MENU BORRAR

                AlertDialog deleteDialog = new AlertDialog.Builder(this)
                        .setTitle("Borrar")
                        .setMessage(
                                "¿Seguro que quieres borrar?" + "\n"
                        )
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //////// ACEPTAR BORRAR /////////
                                customerDao.delete(selectedCustomer);
                                /// SEARCH
                                String searchText = searchEditText.getText().toString(); // text to search
                                List<Customer> customersResult = search(searchText);
                                refreshCustomerList(customersResult);
                                // Desplegar Cliente eliminado
                                Toast.makeText(ClientsActivity.this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void refreshCustomerList(List<Customer> customers){
        // add customers to model
        if(customers!=null) {
            this.customers.clear();
            this.customers.addAll(customers);
        }

        // notify adapter to update view
        this.customersAdapter.notifyDataSetChanged();
    }

    private List<Customer> search(String searchText){
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
        return cust;
    }
}