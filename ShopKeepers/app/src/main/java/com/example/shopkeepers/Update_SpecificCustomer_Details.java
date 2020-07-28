package com.example.shopkeepers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class Update_SpecificCustomer_Details extends Activity {
    private EditText updateItem, updatePrice;
    private Button Update_submit_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__specific_customer__details);

        updateItem = findViewById(R.id.UpdateCustomer_Item);
        updatePrice = findViewById(R.id.UpdateCustomer_Price);
        Update_submit_btn = findViewById(R.id.UpdateCustomer_details_Submit_btn);

        //creating object of Customer_User_Details class and MySQL_DataBase_helper

        final MySQL_DataBase_helper mySQL_dataBase_helper = new MySQL_DataBase_helper(this);

        // PopUp Window Work
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        // getWindow().setBackgroundDrawableResource(flag);
        getWindow().setLayout((int) (width * .7), (int) (height * .35));

        //setting the Popup window position
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -70;
        getWindow().setAttributes(params);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int CustomerID = bundle.getInt("ID");
            Store_CustomerID(CustomerID);
        }

        Update_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Update_item = updateItem.getText().toString();
                String Update_price = updatePrice.getText().toString();
                //get current date from device
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

                if (Update_item.isEmpty() && Update_price.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter all the Information", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        mySQL_dataBase_helper.SpecificCustomer_Details_Update(Update_item,"+"+Update_price, currentDate, Load_CustomerId());
                        Toast.makeText(getApplicationContext(), "Customer due details Update successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Update_SpecificCustomer_Details.this, CustomerDetails_Show.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.getStackTrace();
                        Toast.makeText(getApplicationContext(), "Customer due details Update failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void Store_CustomerID(int Id) {
        SharedPreferences ID = getSharedPreferences("Id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ID.edit();
        editor.putInt("LastID",Id);
        editor.commit();
    }

    public int Load_CustomerId(){
        SharedPreferences ID = getSharedPreferences("Id",Context.MODE_PRIVATE);
        int CustomerId = ID.getInt("LastID",0);
        return CustomerId;
    }
}