package com.example.shopkeepers;

import android.database.Cursor;
import android.icu.text.Transliterator;

import java.util.ArrayList;

public class Customer_User_Details {
    private String Name;
    private String TotalAmount;
    private String CustomerAmount;
    private String Item;
    private String Date;

    public Customer_User_Details(String name, String totalAmount, String customerAmount, String item, String date) {
        Name = name;
        TotalAmount = totalAmount;
        CustomerAmount = customerAmount;
        Item = item;
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getCustomerAmount() {
        return CustomerAmount;
    }

    public void setCustomerAmount(String customerAmount) {
        CustomerAmount = customerAmount;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


}
