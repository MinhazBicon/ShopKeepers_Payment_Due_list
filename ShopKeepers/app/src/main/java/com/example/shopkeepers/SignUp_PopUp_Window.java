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

import androidx.annotation.Nullable;

public class SignUp_PopUp_Window extends Activity implements View.OnClickListener {
    private Button SignUpPage_Cancel_btn, SignUpPage_Done_btn;
    private EditText SignUpPage_Name, SignUpPage_UserName,SignUpPage_Password,SignUpPage_ConfirmPassword,
            SignUpPage_FAQ1,SignUpPage_FAQ2,SignUpPage_FAQ3;
    private MySQL_DataBase_helper mySQL_dataBase_helper;
    private ShopKeeper_UserDetails shopKeeper_userDetails;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_popup);

        // finding all button and edit text
        SignUpPage_Cancel_btn = findViewById(R.id.signUpPage_Cancel_btn);
        SignUpPage_Done_btn = findViewById(R.id.signUpPage_Done_btn);
        SignUpPage_Name = findViewById(R.id.signUpPage_name_id);
        SignUpPage_UserName = findViewById(R.id.signUpPage_userName_id);
        SignUpPage_Password = findViewById(R.id.signUpPage_Password);
        SignUpPage_ConfirmPassword = findViewById(R.id.signUpPage_Confirm_Password);
        SignUpPage_FAQ1 = findViewById(R.id.SignUpPage_FAQ1);
        SignUpPage_FAQ2 = findViewById(R.id.SignUpPage_FAQ2);
        SignUpPage_FAQ3 = findViewById(R.id.SignUpPage_FAQ3);

        //object declare MYSQLDataBase_helper and ShopKeeper_UserDetails
        mySQL_dataBase_helper = new MySQL_DataBase_helper(this);
        shopKeeper_userDetails = new ShopKeeper_UserDetails();

        //adding onClick Listener
        SignUpPage_Cancel_btn.setOnClickListener(this);
        SignUpPage_Done_btn.setOnClickListener(this);

        //PopUp Window Create
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        // getWindow().setBackgroundDrawableResource(flag);
        getWindow().setLayout((int) (width*.9),(int) (height*.95));
        //setting the Popup window position
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -75;
        getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View view) {
        if (view == SignUpPage_Cancel_btn){
            finish();
        }
        if (view == SignUpPage_Done_btn){
            //convert all SignUp_Page data to string and Store in Variable
            String ShopKeeperName = SignUpPage_Name.getText().toString();
            String ShopKeeper_UserName = SignUpPage_UserName.getText().toString();
            String ShopKeeperPassword = SignUpPage_Password.getText().toString();
            String ShopKeeper_ConfirmPassword = SignUpPage_ConfirmPassword.getText().toString();
            String ShopKeeper_FAQ1 = SignUpPage_FAQ1.getText().toString();
            String ShopKeeper_FAQ2 = SignUpPage_FAQ2.getText().toString();
            String ShopKeeper_FAQ3 = SignUpPage_FAQ3.getText().toString();

            //set all Value to ShopKeeper_UserDetails class
            shopKeeper_userDetails.setShopkeeper_Name(ShopKeeperName);
            shopKeeper_userDetails.setShopKeeper_UserName(ShopKeeper_UserName);
            shopKeeper_userDetails.setShopKeeper_Password(ShopKeeperPassword);
            shopKeeper_userDetails.setFAQ1(ShopKeeper_FAQ1);
            shopKeeper_userDetails.setFAQ2(ShopKeeper_FAQ2);
            shopKeeper_userDetails.setFAQ3(ShopKeeper_FAQ3);

            if (ShopKeeperPassword.equals(ShopKeeper_ConfirmPassword)){

               if (ShopKeeperName.isEmpty() && ShopKeeper_UserName.isEmpty() && ShopKeeper_FAQ1.isEmpty() &&
                   ShopKeeper_FAQ2.isEmpty() && ShopKeeper_FAQ3.isEmpty() && ShopKeeperPassword.isEmpty() && ShopKeeper_ConfirmPassword.isEmpty()){

                   Toast.makeText(getApplicationContext()," Please Insert all the information",Toast.LENGTH_SHORT).show();

               }else {
                   long rowID = mySQL_dataBase_helper.ShopkeeperDetails_InsertData(shopKeeper_userDetails);

                   if (rowID>0){
                       Toast.makeText(getApplicationContext(),"Sign Up successful",Toast.LENGTH_SHORT).show();
                       finish();
                   }else {
                       Toast.makeText(getApplicationContext(),"Sign Up Failed",Toast.LENGTH_SHORT).show();
                       SignUpPage_Name.setText("");
                       SignUpPage_UserName.setText("");
                       SignUpPage_FAQ1.setText("");
                       SignUpPage_FAQ2.setText("");
                       SignUpPage_FAQ3.setText("");
                       SignUpPage_Password.setText("");
                       SignUpPage_ConfirmPassword.setText("");
                   }
               }

            }else {
                Toast.makeText(getApplicationContext(),"Password could not match.\n Please Re-Type",Toast.LENGTH_SHORT).show();
                SignUpPage_Password.setText("");
                SignUpPage_ConfirmPassword.setText("");

            }

        }
    }
}
