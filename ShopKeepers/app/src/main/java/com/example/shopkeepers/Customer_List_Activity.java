package com.example.shopkeepers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Customer_List_Activity extends AppCompatActivity {
    private RecyclerView customerList_Recycler;
    private Button AddCustomer;
    private ArrayList<Customer_User_Details> customerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove notification Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer__list_);
        // hide action bar
        getSupportActionBar().hide();

        // find customerList_RecyclerView and AddCustomer_btn
        customerList_Recycler = findViewById(R.id.CustomerList_Name_recyclerView);
        AddCustomer = findViewById(R.id.CustomerList_addCustomer_btn);
        customerList_Recycler.setHasFixedSize(true);
        customerList_Recycler.setLayoutManager(new LinearLayoutManager(this));
        customerList = new ArrayList<>();


        MySQL_DataBase_helper mySQL_dataBase_helper = new MySQL_DataBase_helper(this);
        Cursor cursor = mySQL_dataBase_helper.GetCustomerName_TotalAmount();
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            String totalAmount = cursor.getString(2);
            customerList.add(new Customer_User_Details(name,totalAmount,null,null,null));
        }

        CustomerList_adepter customerList_adepter = new CustomerList_adepter(this,customerList);
        customerList_Recycler.setAdapter(customerList_adepter);
        customerList_adepter.notifyDataSetChanged();




        AddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Customer_List_Activity.this, CustomerName_entry_Popup.class));
            }
        });




    }



}