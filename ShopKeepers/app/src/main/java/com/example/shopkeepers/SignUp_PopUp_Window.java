package com.example.shopkeepers;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class SignUp_PopUp_Window extends Activity implements View.OnClickListener {
    private Button SignUpPage_Cancel_btn, SignUpPage_Done_btn;
    private EditText SignUpPage_Name, SignUpPage_UserName,SignUpPage_Password,SignUpPage_ConfirmPassword,
            SignUpPage_FAQ1,SignUpPage_FAQ2,SignUpPage_FAQ3;
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
        }
    }
}
