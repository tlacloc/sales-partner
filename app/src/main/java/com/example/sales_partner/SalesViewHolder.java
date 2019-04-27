package com.example.sales_partner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SalesViewHolder extends RecyclerView.ViewHolder {
    protected TextView month;
    protected TextView income;

    public SalesViewHolder(@NonNull View v) {
        super(v);
        month =  (TextView) v.findViewById(R.id.infoMesSales);
        income = (TextView)  v.findViewById(R.id.infoMoneySales);
    }
}
