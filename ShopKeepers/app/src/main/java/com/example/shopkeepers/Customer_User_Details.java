package com.example.shopkeepers;

import android.database.Cursor;
import android.icu.text.Transliterator;

import java.util.ArrayList;

public class Customer_User_Details {
    private String CustomerName;
    private String CustomerAmount;
    private String Item;
    private String Date;

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
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
