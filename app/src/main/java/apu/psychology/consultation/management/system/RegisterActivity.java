package com.example.psychologyconsultationfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.psychologyconsultationfinalproject.Controller.AuthenticationAdapter;
import com.example.psychologyconsultationfinalproject.Model.UserModel;

// RegisterActivity
public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextBatch;
    private Spinner spinnerGender;
    private Button buttonRegister, buttonLogin;

    private AuthenticationAdapter authenticationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        editTextName = findViewById(R.id.editTextNameRegister);
        editTextEmail = findViewById(R.id.editTextEmailRegister);
        editTextPassword = findViewById(R.id.editTextPasswordRegister);
        editTextBatch = findViewById(R.id.editTextBatchRegister);
        spinnerGender = findViewById(R.id.spinnerGender);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Initialize AuthenticationAdapter
        authenticationAdapter = new AuthenticationAdapter(this);

        // Set up gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        // Set up click listener for register button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        // Set click listener for register button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser() {
        String fullName = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String batch = editTextBatch.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();

        // Validate input (add your validation logic)

        // Create UserModel
        UserModel user = new UserModel();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setBatch(batch); // You may set the batch as needed
        user.setGender(gender);

        // Register user
        long userId = authenticationAdapter.registerUser(user);

        if (userId != -1) {
            // Registration successful, handle success (e.g., show a message)
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // close the current activity to prevent going back to registration
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
        } else {
            // Registration failed, handle failure (e.g., show an error message)
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}
