package com.example.sales_partner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AssembliesActivity extends AppCompatActivity {
    
    //TAG
    private static final String TAG = "AssembliesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemblies);
        Log.d(TAG, "onCreate: ");
    }
}
