package com.example.shopkeepers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Typeface font;
    private TextView shopkeeperLogo;
    private Button LoginPage_SignUp_btn, Login_btn,ForgetPassword_btn;
    private EditText LoginPage_UserName,LoginPage_password;
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

        //Shopkeepers text font change
        font = Typeface.createFromAsset(getAssets(),"PermanentMarker_Regular.ttf");
        shopkeeperLogo.setTypeface(font);

        //add onClick Listener
        LoginPage_SignUp_btn.setOnClickListener(this);
        Login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == LoginPage_SignUp_btn){
            Intent intent = new Intent(LoginActivity.this, SignUp_PopUp_Window.class);
            startActivity(intent);
        }
        if (view == Login_btn){

        }
    }
}