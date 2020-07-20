package com.example.shopkeepers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerName_entry_Popup extends Activity {
    private EditText name;
    private Button done;
    MySQL_DataBase_helper mySQL_dataBase_helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_name_entry__popup);

        name = findViewById(R.id.Customer_Name);
        done = findViewById(R.id.Name_submit_btn);


        // PopUp Window Work
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        // getWindow().setBackgroundDrawableResource(flag);
        getWindow().setLayout((int) (width*.7),(int) (height*.26));

        //setting the Popup window position
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -70;
        getWindow().setAttributes(params);
        mySQL_dataBase_helper= new MySQL_DataBase_helper(this);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String Name = name.getText().toString();
             if(Name.isEmpty()){
                 Toast.makeText(getApplicationContext(),"Please enter the name",Toast.LENGTH_SHORT).show();
             }else {
                 try {
                     mySQL_dataBase_helper.Customer_Details_Name_Insertion(Name,"0");
                     Toast.makeText(getApplicationContext(),"Customer added successful",Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(CustomerName_entry_Popup.this,Customer_List_Activity.class));
                     finish();
                 }catch (Exception e){
                     e.getStackTrace();
                     Toast.makeText(getApplicationContext(),"Customer added failed",Toast.LENGTH_SHORT).show();
                 }

             }
            }
        });
    }
}