// ConsultationForm.java
package com.example.psychologyconsultationfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.psychologyconsultationfinalproject.Controller.ConsultationAdapter;
import com.example.psychologyconsultationfinalproject.Model.ConsultationModel;

public class ConsultationForm extends AppCompatActivity {
    Button btnBackHome, submitButton;
    private ConsultationAdapter consultationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_form);
        int userId = getIntent().getIntExtra("userId", -1);
        consultationAdapter = new ConsultationAdapter(this);

        submitButton = findViewById(R.id.submitButton);
        btnBackHome = findViewById(R.id.btnBackHome);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createConsultation();
            }
        });
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultationForm.this, HomeActivity.class);
                intent.putExtra("userId", userId); // Pass the userId back to the Consultation activity
                startActivity(intent);
            }
        });
    }


    private void createConsultation() {
        // Retrieve input data from UI elements
        DatePicker datePicker = findViewById(R.id.select_date);
        String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();

        Spinner sessionSpinner = findViewById(R.id.sessionSpinner);
        String session = sessionSpinner.getSelectedItem().toString();

        EditText topicEditText = findViewById(R.id.topicEditText);
        String topic = topicEditText.getText().toString();
        int userId = getIntent().getIntExtra("userId", -1);
        // Check if a consultation with the same date and session already exists
        if (consultationAdapter.isConsultationExists(date, session)) {
            Toast.makeText(ConsultationForm.this, "Sesi sudah terisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create ConsultationModel
        ConsultationModel consultation = new ConsultationModel();
        consultation.setUserId(userId);
        consultation.setDate(date);
        consultation.setSession(session);
        consultation.setTopic(topic);

        // Save consultation to the database
        long consultationId = consultationAdapter.createConsultation(consultation);

        if (consultationId != -1) {
            Intent intent = new Intent(ConsultationForm.this, Consultation.class);
            intent.putExtra("userId", userId); // Pass the userId back to the Consultation activity
            startActivity(intent);
            Toast.makeText(ConsultationForm.this, "Consultation added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
        }
    }
}
