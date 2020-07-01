package com.example.shopkeepers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private Typeface font;
    private TextView shopkeeperLogo;
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

        font = Typeface.createFromAsset(getAssets(),"PermanentMarker_Regular.ttf");
        shopkeeperLogo.setTypeface(font);
    }
}