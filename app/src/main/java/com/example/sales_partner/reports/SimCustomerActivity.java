package com.example.sales_partner.reports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.sales_partner.AssemblyListAdapter;
import com.example.sales_partner.R;
import com.example.sales_partner.dao.AssemblyProductsDao;
import com.example.sales_partner.dao.CustomerDao;
import com.example.sales_partner.dao.OrderAssembliesDao;
import com.example.sales_partner.dao.OrderDao;
import com.example.sales_partner.dao.ProductDao;
import com.example.sales_partner.db.AppDatabase;
import com.example.sales_partner.model.Assembly;
import com.example.sales_partner.model.Customer;
import com.example.sales_partner.model.Order;
import com.example.sales_partner.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SimCustomerActivity extends AppCompatActivity {
    //TAG
    private static final String TAG = "SimCustomerActivity";

    // DATA OBJECTS
    private OrderDao orderDao;
    private OrderAssembliesDao orderAssembliesDao;
    private AssemblyProductsDao assemblyProductsDao;
    private CustomerDao customerDao;
    private ProductDao productDao;

    // DATA
    private List<Product> products;
    private List<Order> orders;
    private List<Customer> customers;

    // GUI COMPONENTS
    Spinner customerSpinner;
    ListView ordersListView;

    // Adapters
    ArrayAdapter ordersAdapter;
    ArrayAdapter customersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_customer);
        //Log.d(TAG, "onCreate: ");

        /// INIT DAOS
        customerDao = AppDatabase.getAppDatabase(getApplicationContext()).customerDao();
        orderDao = AppDatabase.getAppDatabase(getApplicationContext()).orderDao();
        orderAssembliesDao = AppDatabase.getAppDatabase(getApplicationContext()).orderAssembliesDao();
        assemblyProductsDao = AppDatabase.getAppDatabase(getApplicationContext()).assemblyProductsDao();
        productDao = AppDatabase.getAppDatabase(getApplicationContext()).productDao();

        // GET DATA
        customers = customerDao.getAll();
        products = productDao.getAll(); // Get Products
        orders = new ArrayList<Order>();
        //orders = orderDao.findByCustomerAndStatus(4,0); // Get Orders
        //Order o = orders.get(0);

        // Define Adapters
        customersAdapter = new ArrayAdapter(this, R.layout.text_list, customers);
        ordersAdapter = new ArrayAdapter(this, R.layout.text_list, orders);

        // GET UI COMPONENTS
        customerSpinner = findViewById(R.id.sprCustomerSim);
        ordersListView = findViewById(R.id.lvOrdersSim);

        customerSpinner.setOnItemSelectedListener(spinnerListener);

        customerSpinner.setAdapter(customersAdapter);
        ordersListView.setAdapter(ordersAdapter);


        //processOrder(o, orderAssembliesDao);
        //processOrders(orders);
    }

    public void processOrder(Order order, OrderAssembliesDao orderAssembliesDao){
        int possible = 0 ;
        int impossible = 0;

        order.retrieveAssemblies(orderAssembliesDao);

        for (Assembly assembly : order.assemblies) {
            assembly.retrieveProducts(assemblyProductsDao);
            assembly.retrieveProductQuantity(assemblyProductsDao);
            assembly.retrieveQuantity(orderAssembliesDao, order.getId());

            int result = 0;

            while(assembly.quantity > 0){
                 result = order.canDo(products,assembly.products, assembly.quantity);

                if (result == Assembly.STATUS_CAN_DO) {
                    System.out.println("caaaa doooo");
                    // Do it, get product from stock
                    order.doIt(products, assembly.products, assembly.quantity);
                    System.out.println("fulfilled");
                } else {
                    assembly.stockStatus = Assembly.STATUS_OUT_OF_STOCK;
                    break;
                }
                assembly.quantity--;
                System.out.println(assembly);
            }
        }
        System.out.println(order);
    }

    public void processOrders(List<Order> orders){
        for (Order order : orders) {
            processOrder(order, orderAssembliesDao);
        }
    }

    public Boolean getProductFromStock(List<Product> products, int productId, int quantity){
        for (Product product : products) {
            if(product.getId() == productId) {
                if(!product.getProductOut(quantity)){
                    return false;
                }
            }
        }
        return true;
    }

    ///// LISTENERS
    public AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Customer selectedCustomer = (Customer) customerSpinner.getSelectedItem();

            List<Product> p = productDao.getAll(); // Get Products
            products.clear(); products.addAll(p);

            List<Order> o = orderDao.findByCustomerAndStatus(selectedCustomer.getId(),0); // Get Orders
            orders.clear();
            orders.addAll(o);
            processOrders(orders);

            ordersAdapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
