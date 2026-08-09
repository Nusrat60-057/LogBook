package com.example.logbook2023_3_60_057;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddressBookDetails extends Activity {
    private EditText etUserName, etEmail, etPhone, etDateBirth, etPresentAddress, etPermanentAddress;
    private Button btnCancel, btnSave;
    private EventDB db;
    private String currentId;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new EventDB(this);
        setContentView(R.layout.activity_address_book_details);

        etUserName = findViewById(R.id.etUserName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etDateBirth = findViewById(R.id.etDateBirth);
        etPresentAddress = findViewById(R.id.etPresentAddress);
        etPermanentAddress = findViewById(R.id.etPermanentAddress);

        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        // Check if we are in edit mode
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("ID")) {
            isEditMode = true;
            currentId = extras.getString("ID");
            etUserName.setText(extras.getString("NAME"));
            etEmail.setText(extras.getString("EMAIL"));
            etPhone.setText(extras.getString("PHONE"));
            etDateBirth.setText(extras.getString("DOB"));
            etPresentAddress.setText(extras.getString("PRESENT"));
            etPermanentAddress.setText(extras.getString("PERMANENT"));
            btnSave.setText("Update");
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditMode) {
                    updateFieldsData();
                } else {
                    accessFieldsData();
                }
            }
        });
    }

    private void accessFieldsData() {
        String userName = etUserName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dateBirth = etDateBirth.getText().toString().trim();
        String presentAddress = etPresentAddress.getText().toString().trim();
        String permanentAddress = etPermanentAddress.getText().toString().trim();

        if (!validateFields(userName, email, phone, dateBirth, presentAddress, permanentAddress)) {
            return;
        }

        // Save data to SQLite Database
        String id = userName + System.currentTimeMillis();
        String currentUserId = getSharedPreferences("UserPrefs", MODE_PRIVATE).getString("USER-ID", "");
        db.insertEvent(id, currentUserId, userName, email, phone, dateBirth, presentAddress, permanentAddress);
        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateFieldsData() {
        String userName = etUserName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dateBirth = etDateBirth.getText().toString().trim();
        String presentAddress = etPresentAddress.getText().toString().trim();
        String permanentAddress = etPermanentAddress.getText().toString().trim();

        if (!validateFields(userName, email, phone, dateBirth, presentAddress, permanentAddress)) {
            return;
        }

        // Update data in SQLite Database
        db.updateEvent(currentId, userName, email, phone, dateBirth, presentAddress, permanentAddress);
        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean validateFields(String userName, String email, String phone, String dateBirth,
                                   String presentAddress, String permanentAddress) {
        if (userName.length() < 4) {
            Toast.makeText(this, "Name must have 4-20 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isValidEmailAddress(email)) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_LONG).show();
            return false;
        }

        if (phone.length() < 11 || phone.length() > 14) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_LONG).show();
            return false;
        }

        if (dateBirth.length() < 8) {
            Toast.makeText(this, "Date of Birth must be in DD/MM/YYYY format", Toast.LENGTH_LONG).show();
            return false;
        }

        if (presentAddress.length() < 4) {
            Toast.makeText(this, "Present Address is too short", Toast.LENGTH_LONG).show();
            return false;
        }

        if (permanentAddress.length() < 4) {
            Toast.makeText(this, "Permanent Address is too short", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}