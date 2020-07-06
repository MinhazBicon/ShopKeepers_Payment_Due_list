package com.example.shopkeepers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Typeface font;
    private TextView shopkeeperLogo;
    private Button LoginPage_SignUp_btn, Login_btn,ForgetPassword_btn;
    private EditText LoginPage_UserName,LoginPage_password;
    private MySQL_DataBase_helper mySQL_dataBase_helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove notification Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        // hide action bar
        getSupportActionBar().hide();
        // always open in Landscape mode
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //finding all button and Edit text
        shopkeeperLogo = findViewById(R.id.ShopKeeperLogo);
        LoginPage_SignUp_btn = findViewById(R.id.LoginPage_signUp_btn);
        Login_btn =  findViewById(R.id.Login_btn);
        LoginPage_UserName = findViewById(R.id.loginPage_name_id);
        LoginPage_password = findViewById(R.id.loginPage_password_id);
        ForgetPassword_btn = findViewById(R.id.LoginPage_forgetPass_btn);

        mySQL_dataBase_helper = new MySQL_DataBase_helper(this);
        SQLiteDatabase database = mySQL_dataBase_helper.getWritableDatabase();

        //Shopkeepers text font change
        font = Typeface.createFromAsset(getAssets(),"PermanentMarker_Regular.ttf");
        shopkeeperLogo.setTypeface(font);

        //add onClick Listener
        LoginPage_SignUp_btn.setOnClickListener(this);
        Login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
         String ShopKeeper_UserName = LoginPage_UserName.getText().toString();
         String ShopKeeper_Password = LoginPage_password.getText().toString();

        if (view == LoginPage_SignUp_btn){
            Intent intent = new Intent(LoginActivity.this, SignUp_PopUp_Window.class);
            startActivity(intent);
        }
        if (view == Login_btn){
          if (ShopKeeper_UserName.isEmpty() && ShopKeeper_Password.isEmpty()){
              Toast.makeText(getApplicationContext(),"Please Enter User Name and Password",Toast.LENGTH_LONG).show();
          }
          else {
              boolean result = mySQL_dataBase_helper.ShopKeeper_Identity_Check(ShopKeeper_UserName, ShopKeeper_Password);
              if (result == true) {

                  startActivity(new Intent(LoginActivity.this, Customer_List_Activity.class));

              } else {
                  Toast.makeText(getApplicationContext(), "Incorrect User Name or Password", Toast.LENGTH_LONG).show();
                  LoginPage_UserName.setText("");
                  LoginPage_password.setText("");
              }
          }
        }
    }
}