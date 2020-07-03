package com.example.shopkeepers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Typeface font;
    private TextView shopkeeperLogo;
    private Button SignUp, Login;
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

        shopkeeperLogo = findViewById(R.id.ShopKeeperLogo);
        SignUp = findViewById(R.id.Login_signUp_btn);
        Login =  findViewById(R.id.Login_btn);
        font = Typeface.createFromAsset(getAssets(),"PermanentMarker_Regular.ttf");
        shopkeeperLogo.setTypeface(font);

        SignUp.setOnClickListener(this);
        Login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == SignUp){
            Intent intent = new Intent(LoginActivity.this, SignUp_PopUp_Window.class);
            startActivity(intent);
        }
        if (view == Login){
            Intent intent = new Intent(LoginActivity.this, Customer_Details_Edit_PopUp.class);
            startActivity(intent);
        }
    }
}