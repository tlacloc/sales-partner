package com.example.sales_partner.reports;

import android.databinding.adapters.CardViewBindingAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sales_partner.R;

public class ReportsSalesActivity extends AppCompatActivity {

    CardViewBindingAdapter cardViewBindingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_card);
    }
}
