package com.example.psychologyconsultationfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.psychologyconsultationfinalproject.Controller.AuthenticationAdapter;
import com.example.psychologyconsultationfinalproject.Model.UserModel;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmailLogin);
        editTextPassword = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Set click listener for login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login logic here
                login();
            }
        });

        // Set click listener for register button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login() {
        // Retrieve email and password from EditText fields
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            // Show a toast message if either email or password is empty
            Toast.makeText(this, "Please fill required input", Toast.LENGTH_SHORT).show();
            return; // Do not proceed with login if fields are empty
        }
        // Call the login method in your AuthenticationAdapter
        AuthenticationAdapter authenticationAdapter = new AuthenticationAdapter(this);
        boolean loginSuccessful = authenticationAdapter.loginUser(email, password);

        if (loginSuccessful) {
            // Login successful, open the HomeActivity and pass user ID
            UserModel user = authenticationAdapter.getUserDataByEmail(email);
            if (user != null) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("userId", user.getId());
                startActivity(intent);
                finish();
                Toast.makeText(this, "Login Succesfull", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show a login error message
            Toast.makeText(this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
        }
    }
}