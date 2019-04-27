package com.example.sales_partner.model;

public class SalesData {
    protected String monthYear;
    protected int sales;
    protected int income;

    public String toString() {
        return monthYear + " \n" +
            "ventas: " + sales + "\n" +
            "Ingresos: $" + income ;
    }



}

