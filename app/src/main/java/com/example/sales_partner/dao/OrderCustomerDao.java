package com.example.sales_partner.dao;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import com.example.sales_partner.model.OrderCustomer;


import java.util.List;

@Dao
public interface OrderCustomerDao {

    @Query("SELECT orders.id AS id, orders.date AS date,customers.first_name AS customerName, " +
            "order_status.description AS status FROM orders INNER JOIN customers " +
            "ON customers.id = orders.customer_id INNER JOIN order_status " +
            "ON order_status.id = orders.status_id")
    List<OrderCustomer> getAll();

    @RawQuery
    List<OrderCustomer> findByQuery(SimpleSQLiteQuery query);

}
