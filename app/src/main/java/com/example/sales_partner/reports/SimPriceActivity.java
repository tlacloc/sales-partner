package com.example.sales_partner.reports;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sales_partner.R;

public class SimPriceActivity extends AppCompatActivity {
    //TAG
    private static final String TAG = "SimPriceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_price);
        Log.d(TAG, "onCreate: ");
    }
}
