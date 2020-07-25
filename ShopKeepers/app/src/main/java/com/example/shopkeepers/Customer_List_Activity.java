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


import java.util.ArrayList;

public class Customer_List_Activity extends AppCompatActivity {
    private RecyclerView customerList_Recycler;
    private Button AddCustomer;
    private ArrayList<Customer_User_Details> customerList;
    CustomerList_adepter customerList_adepter;
    MySQL_DataBase_helper mySQL_dataBase_helper;
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

        //setting up the RecyclerView
        customerList_Recycler.setHasFixedSize(true);
        customerList_Recycler.setLayoutManager(new LinearLayoutManager(this));

        customerList = new ArrayList<>();

       // get name and initial total amount from CustomerList database table2
        mySQL_dataBase_helper = new MySQL_DataBase_helper(this);
        final Cursor cursor = mySQL_dataBase_helper.GetCustomerName_TotalAmount();
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            String totalAmount = cursor.getString(2);
            customerList.add(new Customer_User_Details(name,totalAmount,null,null,null));
        }

        //pass value of customer array list and set adapter to RecyclerView
        customerList_adepter = new CustomerList_adepter(this,customerList);
        customerList_Recycler.setAdapter(customerList_adepter);


        AddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Customer_List_Activity.this, CustomerName_entry_Popup.class));
            }
        });

        customerList_adepter.SetOnItemClickListener(new CustomerList_adepter.OnClickListener() {
            @Override
            public void OnItemClickListener(int position) {
             // get name and initial total amount in current position
                customerList.get(position);
                Cursor cursor1 = mySQL_dataBase_helper.GetCustomerName_TotalAmount();
                ArrayList<String> nameArr = new ArrayList<>();
                ArrayList<String> AmountArr = new ArrayList<>();
                ArrayList<Integer> CustomerIDArr = new ArrayList<>();
                while (cursor1.moveToNext()){
                    int ID = cursor1.getInt(0);
                    String name = cursor1.getString(1);
                    String Total = cursor1.getString(2);
                    nameArr.add(name);
                    AmountArr.add(Total);
                    CustomerIDArr.add(ID);
                }
                Intent intent  = new Intent(Customer_List_Activity.this,CustomerDetails_Show.class);
                intent.putExtra("Name",nameArr.get(position));
                intent.putExtra("Amount",AmountArr.get(position));
                intent.putExtra("Customer_ID",CustomerIDArr.get(position));
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Customer_List_Activity.this,LoginActivity.class));
        finish();
    }
}