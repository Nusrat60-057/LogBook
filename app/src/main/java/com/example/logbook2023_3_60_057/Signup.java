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

public class Signup extends Activity {
    private EditText etUserId, etUserName, etEmail, etPhone, etPassword, etReenterPass;
    private CheckBox cbRememberUserId, cbRememberUserLogin;
    private Button btnAlreadyHaveAccount, btnExit, btnGo;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Use a shared file so Login.java can also access it
        sp = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        boolean fromLogin = getIntent().getBooleanExtra("FROM_LOGIN", false);
        
        // Check if user is already logged in
        if (!fromLogin && sp.getBoolean("REM-LOGIN", false)) {
            startActivity(new Intent(this, AddressBookList.class));
            finish();
            return;
        }
        
        // If an account exists but not logged in, go to Login
        if (!fromLogin && sp.contains("USER-ID")) {
            startActivity(new Intent(this, Login.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_signup);

        etUserId = findViewById(R.id.etUserId);
        etUserName = findViewById(R.id.etUserName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etReenterPass = findViewById(R.id.etReenterPass);
        cbRememberUserId = findViewById(R.id.cbRememberUserId);
        cbRememberUserLogin = findViewById(R.id.cbRememberUserLogin);
        btnAlreadyHaveAccount = findViewById(R.id.btnAlreadyHaveAccount);
        btnExit = findViewById(R.id.btnExit);
        btnGo = findViewById(R.id.btnGo);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accessFieldsData();
            }
        });

        btnAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });
    }

    private void accessFieldsData() {
        String userId = etUserId.getText().toString().trim();
        String userName = etUserName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String reEnterPass = etReenterPass.getText().toString().trim();

        if(userId.length() < 4) {
            Toast.makeText(this, "UserId must have at least 4 digits", Toast.LENGTH_LONG).show();
            return;
        }
        if (userName.length() < 4) {
            Toast.makeText(this, "User name is too short", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(reEnterPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER-ID", userId);
        editor.putString("USER-NAME", userName);
        editor.putString("PASS", password);
        editor.putString("EMAIL", email);
        editor.putString("PHONE", phone);
        editor.putBoolean("REM-LOGIN", cbRememberUserLogin.isChecked());
        editor.putBoolean("REM-USER", cbRememberUserId.isChecked());
        editor.apply();

        startActivity(new Intent(this, AddressBookList.class));
        finish();
    }
}
