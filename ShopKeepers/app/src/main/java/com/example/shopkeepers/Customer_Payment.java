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

public class Customer_Payment extends Activity {
     private EditText Payment;
     private Button Payment_submit;
     private int PayAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__payment);

        //find edit text and button
         Payment = findViewById(R.id.customer_payment);
         Payment_submit = findViewById(R.id.payment_submit_btn);

        final MySQL_DataBase_helper mySQL_dataBase_helper = new MySQL_DataBase_helper(this);

        // PopUp Window Work
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        // getWindow().setBackgroundDrawableResource(flag);
        getWindow().setLayout((int) (width*.7),(int) (height*.25));

        //setting the Popup window position
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -80;
        getWindow().setAttributes(params);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String CustomerID = bundle.getString("ID");
            Store_CustomerID(CustomerID);
        }

        Payment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pay_amount = Payment.getText().toString();
                int Pay_amount = Integer.parseInt(pay_amount);
                PayAmount = -Pay_amount;
                String PAY_AMOUNT = String.valueOf(PayAmount);
                String payWord = "Payment";
                //get current date from device
                Calendar calendar = Calendar.getInstance();
                String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                if (PAY_AMOUNT.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter Pay Amount",Toast.LENGTH_SHORT).show();
                }
                else {
                   try {
                       mySQL_dataBase_helper.SpecificCustomer_Details_Insertion(payWord, PAY_AMOUNT, currentDate, Load_CustomerId());
                       Toast.makeText(getApplicationContext(), "Customer due details Insert successful", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(Customer_Payment.this, CustomerDetails_Show.class);
                       startActivity(intent);
                       finish();
                   }catch (Exception e){
                       e.getStackTrace();
                       Toast.makeText(getApplicationContext(),"Payment Accept failed",Toast.LENGTH_SHORT).show();
                   }

                }


            }
        });


    }

    public void Store_CustomerID(String Id) {
        SharedPreferences ID = getSharedPreferences("Id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ID.edit();
        editor.putString("LastID",Id);
        editor.commit();
    }

    public String Load_CustomerId(){
        SharedPreferences ID = getSharedPreferences("Id",Context.MODE_PRIVATE);
        String CustomerId = ID.getString("LastID","Id Not found");
        return CustomerId;
    }

}