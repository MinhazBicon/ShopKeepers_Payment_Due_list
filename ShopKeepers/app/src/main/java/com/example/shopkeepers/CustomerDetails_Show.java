package com.example.shopkeepers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.jar.Attributes;

import static androidx.recyclerview.widget.RecyclerView.*;
import static java.util.jar.Attributes.*;

public class CustomerDetails_Show extends AppCompatActivity implements View.OnClickListener {
    private Button add1,add2;
    private TextView SpecificName, Total_amount;
    private RecyclerView recyclerView;
    MySQL_DataBase_helper mySQL_dataBase_helper;
    CustomerDetails_Adepter customerDetails_adepter;
    Customer_User_Details customer_user_details;
    private ArrayList<Customer_User_Details> specificDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove notification Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_details__list);
        // hide action bar
        getSupportActionBar().hide();

        // find all variable
        add1 = findViewById(R.id.details_add_btn1);
        add2 = findViewById(R.id.details_add_btn2);
        SpecificName = findViewById(R.id.specific_name);
        Total_amount = findViewById(R.id.specific_amount);
        recyclerView = findViewById(R.id.RecyclerView_id);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set OnclickListener
        add1.setOnClickListener(this);
        add2.setOnClickListener(this);

        // getting current click Customer name
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
        String Profile_Name = bundle.getString("Name");
        String Total_Amount = bundle.getString("Amount");
        //store current customerName
            Store_Profile_data(Profile_Name,Total_Amount);
        }



        //set profile data
         SpecificName.setText(Load_And_Set_Name());
         Total_amount.setText(Load_And_Set_amount());



        specificDetails = new ArrayList<>();
        // get data from  SpecificCustomerDetails table
        mySQL_dataBase_helper = new MySQL_DataBase_helper(this);
        Cursor cursor = mySQL_dataBase_helper.GetSpecific_Customer_Details();
        while (cursor.moveToNext()){
            String itemName = cursor.getString(1);
            String itemAmount = cursor.getString(2);
            String itemDate = cursor.getString(3);
            specificDetails.add(new Customer_User_Details(null,null,itemAmount,itemName,itemDate));
        }
        customerDetails_adepter = new CustomerDetails_Adepter(this,specificDetails);
        //setting up the RecyclerView
        recyclerView.setAdapter(customerDetails_adepter);
        customerDetails_adepter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(CustomerDetails_Show.this,Customer_Details_Entry_PopUp.class));
    }

    public void Store_Profile_data(String Name, String TotalAmount){
        SharedPreferences storeName = getSharedPreferences("Name", Context.MODE_PRIVATE);
        SharedPreferences storeAmount = getSharedPreferences("Amount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = storeName.edit();
        SharedPreferences.Editor editor2 = storeAmount.edit();
        editor1.putString("last_profile",Name);
        editor2.putString("Total",TotalAmount);
        editor1.commit();
        editor2.commit();

   }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CustomerDetails_Show.this,Customer_List_Activity.class));
    }

    public String Load_And_Set_Name(){
        SharedPreferences LoadName = getSharedPreferences("Name",Context.MODE_PRIVATE);
        String Profile = LoadName.getString("last_profile","Name not found");
        return Profile;
   }
    private String Load_And_Set_amount(){
        SharedPreferences Amount = getSharedPreferences("Amount",Context.MODE_PRIVATE);
        String Total = Amount.getString("Total"," amount0");
        return Total;
    }

}