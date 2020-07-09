package com.example.shopkeepers;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Customer_Details_Entry_PopUp extends Activity {
    private EditText CustomerName, CustomerAmount, Item, Date;
    private Button CustomerDetails_Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details_enrty_popup);

        CustomerName = findViewById(R.id.CustomerName_Edit);
        CustomerAmount = findViewById(R.id.Customer_Amount);
        Item = findViewById(R.id.Customer_Item);
        Date = findViewById(R.id.CustomerDue_itemDate);
        CustomerDetails_Submit = findViewById(R.id.Customer_details_Submit_btn);

        //creating object of Customer_User_Details class and MySQL_DataBase_helper
         final Customer_User_Details customer_user_details = new Customer_User_Details();
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
                String Customer_name = CustomerName.getText().toString();
                String Customer_Amount = CustomerAmount.getText().toString();
                String Customer_Item = Item.getText().toString();
                String Customer_Date = Date.getText().toString();

                customer_user_details.setCustomerName(Customer_name);
                customer_user_details.setCustomerAmount(Customer_Amount);
                customer_user_details.setItem(Customer_Item);
                customer_user_details.setDate(Customer_Date);

                if (Customer_name.isEmpty() && Customer_Amount.isEmpty() && Customer_Item.isEmpty()&&Customer_Date.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Enter all the Information",Toast.LENGTH_SHORT).show();
                }
                else {

                  long row = mySQL_dataBase_helper.Customer_Details_Insertion(customer_user_details);

                  if (row >0){
                       Toast.makeText(getApplicationContext(),"Customer details Insert successful",Toast.LENGTH_SHORT).show();
                       finish();
                  }
                  else {
                      Toast.makeText(getApplicationContext(), "Customer details Insert failed", Toast.LENGTH_SHORT).show();
                      CustomerName.setText("");
                      CustomerAmount.setText("");
                      Item.setText("");
                      Date.setText("");
                  }
                }
            }
        });
    }

}