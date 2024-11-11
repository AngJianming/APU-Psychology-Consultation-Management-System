package com.example.psychologyconsultationfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.psychologyconsultationfinalproject.Controller.AuthenticationAdapter;
import com.example.psychologyconsultationfinalproject.Controller.ConsultationAdapter;
import com.example.psychologyconsultationfinalproject.Model.ConsultationModel;
import com.example.psychologyconsultationfinalproject.Model.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    TextView welcomeText, consultationUserText, listConsultationAll;
    Button consultationBtn;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcomeText = findViewById(R.id.welcomeTextUser);
        consultationUserText = findViewById(R.id.consultationTableUser);
        consultationBtn = findViewById(R.id.buttonConsultation);
        tableLayout = findViewById(R.id.tableLayout);
        listConsultationAll = findViewById(R.id.listConsultationAll);
        // Get user ID from intent
        int userId = getIntent().getIntExtra("userId", -1);

        // Use AuthenticationAdapter to get user data
        AuthenticationAdapter authenticationAdapter = new AuthenticationAdapter(this);
        UserModel user = authenticationAdapter.getUserData(userId);

        // Display user data in your UI
        if (user != null) {
            welcomeText.setText("Welcome to " + user.getFullName());
            consultationUserText.setText("Consultation for " + user.getFullName());

        }
        consultationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ConsultationForm.class);
                intent.putExtra("userId", userId); // Pass the userId back to the Consultation activity
                startActivity(intent);
            }
        });
        listConsultationAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Consultation.class);
                intent.putExtra("userId", userId); // Pass the userId back to the Consultation activity
                startActivity(intent);
            }
        });
        List<ConsultationModel> consultations = getConsultationsByUserId(userId);
        populateDataRows(consultations);
    }

    private void populateDataRows(List<ConsultationModel> consultations) {
        for (int i = 0; i < consultations.size(); i++) {
            ConsultationModel consultation = consultations.get(i);

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            // Create TextView for Date
            TextView tvDate = createTextView(formatDate(consultation.getDate()), TableRow.LayoutParams.WRAP_CONTENT);
            setTextViewAttributes(tvDate);

            // Create TextView for Date
            TextView tvSession = createTextView(consultation.getSession(), TableRow.LayoutParams.WRAP_CONTENT);
            setTextViewAttributes(tvSession);

            // Create TextView for Details
            TextView tvDetails = createTextView(consultation.getTopic(), TableRow.LayoutParams.WRAP_CONTENT);
            setTextViewAttributes(tvDetails);

            // Add TextViews to the TableRow
            row.addView(tvDate);
            row.addView(tvSession);
            row.addView(tvDetails);

            // Add TableRow to the TableLayout
            tableLayout.addView(row);
        }
    }

    // Helper method to create TextView with common properties
    private TextView createTextView(String text, int width) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setLayoutParams(new TableRow.LayoutParams(width, TableRow.LayoutParams.WRAP_CONTENT));
        textView.setPadding(8, 8, 8, 8); // Adjust padding as needed
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTypeface(null, Typeface.BOLD);
        return textView;
    }

    // Helper method to set common attributes for TextView
    private void setTextViewAttributes(TextView textView) {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1
        );
        layoutParams.setMargins(0, 0, 16, 0); // Adjust margin as needed
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_VERTICAL);
    }

    private List<ConsultationModel> getConsultationsByUserId(int userId) {
        // Implement your logic to retrieve consultations from the database
        // Use ConsultationAdapter to get the list of consultations based on userId
        ConsultationAdapter consultationAdapter = new ConsultationAdapter(this);
        return consultationAdapter.getConsultationsByUserId(userId);
    }

    private String formatDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(inputDate);
            if (date != null) {
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Return the original date if parsing fails
        return inputDate;
    }
}
