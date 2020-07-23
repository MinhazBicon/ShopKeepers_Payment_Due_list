package com.example.shopkeepers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

public class Customer_Details_Entry_PopUp extends Activity {
    private EditText CustomerAmount, Item, Date;
    private Button CustomerDetails_Submit;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details_enrty_popup);

        CustomerAmount = findViewById(R.id.Customer_Amount);
        Item = findViewById(R.id.Customer_Item);
        Date = findViewById(R.id.CustomerDue_itemDate);
        CustomerDetails_Submit = findViewById(R.id.Customer_details_Submit_btn);

        //creating object of Customer_User_Details class and MySQL_DataBase_helper

         final MySQL_DataBase_helper mySQL_dataBase_helper = new MySQL_DataBase_helper(this);

        // PopUp Window Work
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        // getWindow().setBackgroundDrawableResource(flag);
        getWindow().setLayout((int) (width*.8),(int) (height*.5));

        //setting the Popup window position
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -60;
        getWindow().setAttributes(params);





        //Onclick Listener event add
        CustomerDetails_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Customer_Amount = CustomerAmount.getText().toString();
                String Customer_Item = Item.getText().toString();
                String Customer_Date = Date.getText().toString();

                if (Customer_Amount.isEmpty() && Customer_Item.isEmpty()&&Customer_Date.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter all the Information",Toast.LENGTH_SHORT).show();
                }
                else {
                  try {
                      mySQL_dataBase_helper.SpecificCustomer_Details_Insertion(Customer_Item,Customer_Amount,Customer_Date);
                      Toast.makeText(getApplicationContext(),"Customer due details Insert successful",Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(Customer_Details_Entry_PopUp.this,CustomerDetails_Show.class);
                      startActivity(intent);
                      finish();
                  }
                  catch (Exception e){
                      e.getStackTrace();
                      Toast.makeText(getApplicationContext(),"Customer due details Insert failed",Toast.LENGTH_SHORT).show();
                  }
                }
            }
        });
    }

}

