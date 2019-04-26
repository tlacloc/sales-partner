package com.example.sales_partner.reports;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.sales_partner.R;


public class ReportsProductsActivity extends AppCompatActivity {
    
    //LOG
    private static final String TAG = "ReportsProductsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_products);
        Log.d(TAG, "onCreate: ");
    }
}
