package com.example.clientmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * GradeActivity represents the screen displaying a student's grade information.
 *
 * <p>This activity retrieves data from the Intent, including the student's name and ID.
 * It creates a JSON object with this information and displays the grade details on the screen.
 * Additionally, it sets an average value and provides a button to return to the PrincipalScreen.</p>
 *
 * <p>Author: Diego Elizondo</p>
 */
public class GradeActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade_screen);

        // Obtain references to TextViews and the button
        TextView averageTextView = findViewById(R.id.averageTextView);
        TextView gradeDetailTextView = findViewById(R.id.gradeDetailTextView);
        Button buttonVolver = findViewById(R.id.buttonVolver);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        final String carnet;
        final String nombre;

        if (intent.hasExtra("carnet") && intent.hasExtra("nombre")) {
            carnet = intent.getStringExtra("carnet");
            nombre = intent.getStringExtra("nombre");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("message", "grade");
                jsonObject.put("nombre", nombre);
                jsonObject.put("carnet", carnet);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(GradeActivity.this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
                return;
            }

            // Set the text for the new field
            String gradeDetailText = "La calificación de " + nombre + " (" + carnet + ") es la siguiente:";
            gradeDetailTextView.setText(gradeDetailText);
        } else {
            // If no data is present, assign default values or handle the situation
            carnet = "";
            nombre = "";
        }

        // Set the value of the average (currently, 5)
        double promedio = 5.0;
        averageTextView.setText(getString(R.string.average_text, String.valueOf(promedio)));

        // Configure the listener for the "Volver" button
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic to return to the PrincipalScreen
                Intent intent = new Intent(GradeActivity.this, PrincipalScreen.class);
                intent.putExtra("carnet", carnet);
                intent.putExtra("nombre", nombre);
                startActivity(intent);
                finish();  // This closes the current activity to prevent it from stacking in the activity stack
            }
        });
    }
}
