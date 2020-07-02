package com.example.shopkeepers;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;

public class SignUp_PopUp_Window extends Activity implements View.OnClickListener {
    //int flag = #ACFAEF;
    private Button cancel, done;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_popup);

        cancel = findViewById(R.id.signUp_cancel_btn);
        done = findViewById(R.id.signUp_done_btn);
        cancel.setOnClickListener(this);

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
        if (view == cancel){
            finish();
        }
        if (view == done){
            finish();
        }
    }
}
