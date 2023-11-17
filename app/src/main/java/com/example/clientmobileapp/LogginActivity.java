package com.example.clientmobileapp;

import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * LogginActivity represents the user login screen.
 *
 * <p>This activity allows users to enter their student ID (carnet) and name.
 * Upon clicking the "Iniciar Sesión" button, it validates the input, creates a JSON object with
 * the provided information, and displays a toast message with the student ID and name.</p>
 *
 * <p>Author: Jose Barquero</p>
 */
public class LogginActivity extends AppCompatActivity {

    private EditText editTextCarnet;
    private EditText editTextNombre;
    private Button buttonIniciarSesion;

    /**
     * Called when the activity is starting.
     *
     * <p>This is where most initialization should go: calling {@code setContentView(int)}
     * to inflate the activity's UI, using {@code findViewById(int)} to programmatically interact with widgets
     * in the UI, and configuring the activity's toolbar.</p>
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loggin_screen);

        // Configure the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarLoggin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        editTextCarnet = findViewById(R.id.editTextCarnet);
        editTextNombre = findViewById(R.id.editTextNombre);
        buttonIniciarSesion = findViewById(R.id.buttonIniciarSesion);

        // Set up click listener for "Iniciar Sesión" button
        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the input fields
                String carnet = editTextCarnet.getText().toString();
                String nombre = editTextNombre.getText().toString();

                // Check if any of the fields is empty
                if (carnet.isEmpty() || nombre.isEmpty()){
                    Toast.makeText(LogginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate the student ID
                if (!isValidCarnet(carnet)) {
                    Toast.makeText(LogginActivity.this, "ID should only contain numbers", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate the name
                if (!isValidName(nombre)) {
                    Toast.makeText(LogginActivity.this, "Name should only contain letters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a JSON object with the name and student ID
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message","loggin");
                    jsonObject.put("nombre", nombre);
                    jsonObject.put("carnet", carnet);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LogginActivity.this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create an Intent to start PrincipalScreen
                Intent intent = new Intent(LogginActivity.this, PrincipalScreen.class);

                // Put additional data in the Intent
                intent.putExtra("carnet", carnet);
                intent.putExtra("nombre", nombre);

                // Start the PrincipalScreen activity
                startActivity(intent);

                // Optional: Close the current activity
                finish();
            }
        });
    }

    /**
     * Called when the user selects an item from the options menu.
     *
     * <p>Handle the action of the up arrow, usually back to the parent activity.</p>
     *
     * @param item The menu item selected.
     * @return True if the item was handled successfully, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the action of the up arrow
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Validate that the student ID contains only numbers.
     *
     * @param carnet The entered student ID.
     * @return True if the student ID is valid, false otherwise.
     */
    private boolean isValidCarnet(String carnet) {
        return carnet.matches("[0-9]+");
    }

    /**
     * Validate that the name contains only letters.
     *
     * @param name The entered name.
     * @return True if the name is valid, false otherwise.
     */
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }
}
