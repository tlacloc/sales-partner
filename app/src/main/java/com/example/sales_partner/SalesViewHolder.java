package com.example.sales_partner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SalesViewHolder extends RecyclerView.ViewHolder {
    protected TextView month;
    protected TextView income;
    protected TextView sales;

    public SalesViewHolder(@NonNull View v) {
        super(v);
        month =  (TextView) v.findViewById(R.id.infoMesSales);
        income = (TextView)  v.findViewById(R.id.infoMoneySales);
        sales = (TextView) v.findViewById(R.id.infoSales);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"test",Toast.LENGTH_SHORT);
            }
        });
    }

}
