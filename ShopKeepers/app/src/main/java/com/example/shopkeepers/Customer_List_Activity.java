package com.example.shopkeepers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class Customer_List_Activity extends AppCompatActivity {
    private RecyclerView customerList_Recycler;
    private Button AddCustomer;
    private MySQL_DataBase_helper mySQL_dataBase_helper;
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
        Customer_User_Details customer_user_details = new Customer_User_Details();

        AddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Customer_List_Activity.this, Customer_Details_Entry_PopUp.class));

            }
        });


    }



}