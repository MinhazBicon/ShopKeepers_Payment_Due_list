package com.example.shopkeepers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.jar.Attributes;

import static androidx.recyclerview.widget.RecyclerView.*;
import static java.util.jar.Attributes.*;

public class CustomerDetails_Show extends AppCompatActivity implements View.OnClickListener {
    private Button add,sub;
    private TextView SpecificName, Total_amount;
    private RecyclerView recyclerView;
    MySQL_DataBase_helper mySQL_dataBase_helper;
    CustomerDetails_Adepter customerDetails_adepter;
    private String currentId;
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
        add = findViewById(R.id.details_add_btn1);
        sub = findViewById(R.id.details_add_btn2);
        SpecificName = findViewById(R.id.specific_name);
        Total_amount = findViewById(R.id.specific_amount);
        recyclerView = findViewById(R.id.RecyclerView_id);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set OnclickListener
        add.setOnClickListener(this);
        sub.setOnClickListener(this);

        // getting current click Customer name and initial  total amount from CustomerList activity
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
        String Profile_Name = bundle.getString("Name");
        String Total_Amount = bundle.getString("Amount");
        int CustomerId = bundle.getInt("Customer_ID");
        String ID = String.valueOf(CustomerId);
        //store current customerName
            Store_Profile_data(Profile_Name,Total_Amount,ID);
        }
        //set profile  name and initial total name
        SpecificName.setText(Load_And_Set_Name());
        Total_amount.setText(Load_And_Set_amount());

        specificDetails = new ArrayList<>();
        String ID = Load_And_Set_CustomerID();

        // get data from  SpecificCustomerDetails table3
        mySQL_dataBase_helper = new MySQL_DataBase_helper(this);
        Cursor cursor = mySQL_dataBase_helper.GetSpecific_Customer_Details(ID);
        while (cursor.moveToNext()){
            String itemName = cursor.getString(1);
            String itemAmount = cursor.getString(2);
            String itemDate = cursor.getString(3);
                   currentId = cursor.getString(4);
            specificDetails.add(new Customer_User_Details(null,null,itemAmount,itemName,itemDate));
        }

        // Calculate TotalAmount from Database Table 3
       Cursor cursor1 =mySQL_dataBase_helper.GetTotal_Amount_Specific_CustomerDetails(ID);
        while (cursor1.moveToNext()){
            String Total = cursor1.getString(1);
            int value = Integer.parseInt(Total);
            if (value>0){
                Total_amount.setText("+"+Total);
                String total = "+"+Total;
                Store_Calculated_total_amount(total);
            }
            if(value<0){
                Total_amount.setText(Total);
                Store_Calculated_total_amount(Total);
            }
        }

        // Update Total Amount to database Table 2
         int id = Integer.parseInt(Load_And_Set_CustomerID());
         String total = ReturnTotal_Amount();
         try {
             if (id == Integer.parseInt(currentId)){
             mySQL_dataBase_helper.Update_TotalAmount(id,total);}
         }catch (Exception e){
             e.getStackTrace();
             Toast.makeText(getApplicationContext(),"Nothing to calculate\n please add item",Toast.LENGTH_SHORT).show();
         }

        //setting up the RecyclerView  and set adapter to RecyclerView
        customerDetails_adepter = new CustomerDetails_Adepter(this,specificDetails);
        recyclerView.setAdapter(customerDetails_adepter);
        customerDetails_adepter.notifyDataSetChanged();

        // attach swipe function to RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

   ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
       @Override
       public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, @NonNull ViewHolder target) {
           return false;
       }

       @Override
       public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
           int position = viewHolder.getAdapterPosition();
           Cursor cursor = mySQL_dataBase_helper.GetSpecific_Customer_Details_All();
           ArrayList<Integer> ID = new ArrayList<>();
           while (cursor.moveToNext()){
               ID.add(cursor.getInt(0));}

           switch (direction){
               case ItemTouchHelper.LEFT:

                try {
                    mySQL_dataBase_helper.DeleteData_From_Specific_Customer_table(ID.get(position));
                    Toast.makeText(getApplicationContext(),"Delete successful "+ID.get(position),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CustomerDetails_Show.this,CustomerDetails_Show.class));
                }
                catch (Exception e){
                    e.getStackTrace();
                    Toast.makeText(getApplicationContext(),"Delete Failed: "+e,Toast.LENGTH_LONG).show();
                }
                     break;

               case ItemTouchHelper.RIGHT:
                  int id  = ID.get(position);
                  Intent intent = new Intent(CustomerDetails_Show.this,Update_SpecificCustomer_Details.class);
                  intent.putExtra("ID",id);
                  startActivity(intent);
                   break;
           }

       }
   };

    @Override
    public void onClick(View view) {
       if (view == add){
        String Customer_id = Load_And_Set_CustomerID();
        Intent intent = new Intent(CustomerDetails_Show.this,Customer_Details_Entry_PopUp.class);
        intent.putExtra("ID",Customer_id);
        startActivity(intent);
       }
       if (view == sub){
           String Customer_id = Load_And_Set_CustomerID();
           Intent intent = new Intent(CustomerDetails_Show.this,Customer_Payment.class);
           intent.putExtra("ID",Customer_id);
           startActivity(intent);
       }
    }

    public void Store_Profile_data(String Name, String TotalAmount, String CustomerId){
        SharedPreferences storeName = getSharedPreferences("Name", Context.MODE_PRIVATE);
        SharedPreferences storeAmount = getSharedPreferences("Amount", Context.MODE_PRIVATE);
        SharedPreferences storeCustomerId = getSharedPreferences("Id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = storeName.edit();
        SharedPreferences.Editor editor2 = storeAmount.edit();
        SharedPreferences.Editor editor3 = storeCustomerId.edit();
        editor1.putString("last_profile",Name);
        editor2.putString("Total",TotalAmount);
        editor3.putString("last_Customer_id",CustomerId);
        editor1.commit();
        editor2.commit();
        editor3.commit();
   }

    private String Load_And_Set_Name(){
        SharedPreferences LoadName = getSharedPreferences("Name",Context.MODE_PRIVATE);
        String Profile = LoadName.getString("last_profile","Name not found");
        return Profile;
   }
    private String Load_And_Set_amount(){
        SharedPreferences LoadAmount = getSharedPreferences("Amount",Context.MODE_PRIVATE);
        String Total = LoadAmount.getString("Total","amount");
        return Total;
    }
    private String Load_And_Set_CustomerID(){
        SharedPreferences LoadCustomerId = getSharedPreferences("Id",Context.MODE_PRIVATE);
        String ID = LoadCustomerId.getString("last_Customer_id","ID not found");
        return ID;
    }
    private void Store_Calculated_total_amount(String total){
        SharedPreferences Total = getSharedPreferences("total",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Total.edit();
        editor.putString("LastTotal",total);
        editor.commit();
    }
    private String ReturnTotal_Amount(){
        SharedPreferences LoadTotal = getSharedPreferences("total",Context.MODE_PRIVATE);
        String total = LoadTotal.getString("LastTotal","0");
        return total;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CustomerDetails_Show.this,Customer_List_Activity.class));
        finish();
    }
}