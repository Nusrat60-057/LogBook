package com.example.logbook2023_3_60_057;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
    private EditText etUserId, etPassword;
    private CheckBox cbRememberUserId, cbRememberUserLogin;
    private Button btnNoAccount, btnExit, btnGo;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        sp = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        
        etUserId = findViewById(R.id.etUserId);
        etPassword = findViewById(R.id.etPassword);
        cbRememberUserId = findViewById(R.id.cbRememberUserId);
        cbRememberUserLogin = findViewById(R.id.cbRememberUserLogin);
        btnExit = findViewById(R.id.btnExit);
        btnGo = findViewById(R.id.btnGo);
        btnNoAccount = findViewById(R.id.btnNoAccount);

        // Pre-fill if UserID was remembered
        if (sp.getBoolean("REM-USER", false)) {
            etUserId.setText(sp.getString("USER-ID", ""));
            cbRememberUserId.setChecked(true);
        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputId = etUserId.getText().toString().trim();
                String inputPass = etPassword.getText().toString().trim();
                
                String savedId = sp.getString("USER-ID", "");
                String savedPass = sp.getString("PASS", "");

                if (inputId.equals(savedId) && inputPass.equals(savedPass)) {
                    // Update login preferences
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("REM-LOGIN", cbRememberUserLogin.isChecked());
                    editor.putBoolean("REM-USER", cbRememberUserId.isChecked());
                    editor.apply();
                    
                    startActivity(new Intent(Login.this, AddressBookList.class));
                    finish();
                } else {
                    Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        btnNoAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                intent.putExtra("FROM_LOGIN", true);
                startActivity(intent);
            }
        });
    }
}
