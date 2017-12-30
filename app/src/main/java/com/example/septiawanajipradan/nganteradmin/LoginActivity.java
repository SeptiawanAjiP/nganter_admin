package com.example.septiawanajipradan.nganteradmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by aji on 7/26/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText username,password;
    private Button login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        username = (EditText)findViewById(R.id.user_name);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("nganter")&&password.getText().toString().equals("nganter.com")){
                    Intent intent = new Intent(LoginActivity.this,BukaLayananActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
