package com.example.psychologyconsultationfinalproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.psychologyconsultationfinalproject.Controller.ConsultationAdapter;
import com.example.psychologyconsultationfinalproject.Model.ConsultationModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Consultation extends AppCompatActivity {

    Button btnConsultationForm, btnBackHome;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        btnConsultationForm = findViewById(R.id.btnConsultationForm);
        btnBackHome = findViewById(R.id.btnBackHome);
        tableLayout = findViewById(R.id.tableLayout);

        // Replace this with the actual userId
        int userId = getIntent().getIntExtra("userId", -1);

        btnConsultationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Consultation.this, ConsultationForm.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Consultation.this, HomeActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        // Populate the data rows dynamically
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

            // Create TextView for No
            TextView tvNo = createTextView(String.valueOf(i + 1), TableRow.LayoutParams.WRAP_CONTENT);
            setTextViewAttributes(tvNo);

            // Create TextView for Date and Session
            String dateAndSession = formatDate(consultation.getDate()) + " - " + consultation.getSession();
            TextView tvDateAndSession = createTextView(dateAndSession, TableRow.LayoutParams.WRAP_CONTENT);
            setTextViewAttributes(tvDateAndSession);
            tvDateAndSession.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3));

            // Create TextView for Details
            TextView tvDetails = createTextView(consultation.getTopic(), TableRow.LayoutParams.WRAP_CONTENT);
            setTextViewAttributes(tvDetails);
            tvDetails.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3)); // Set weight to 2

            // Create TextView for Approved
            TextView tvApproved = createTextView("✔", TableRow.LayoutParams.WRAP_CONTENT);
            setTextViewAttributes(tvApproved);


            // Add TextViews to the TableRow
            row.addView(tvNo);
            row.addView(tvDateAndSession);
            row.addView(tvDetails);
            row.addView(tvApproved);

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
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
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
