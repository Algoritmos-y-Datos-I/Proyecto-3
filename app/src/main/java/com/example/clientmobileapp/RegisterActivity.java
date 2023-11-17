package com.example.clientmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * RegisterActivity represents the user registration screen.
 *
 * <p>This activity allows users to enter their name, student ID (carnet), and residence.
 * Upon clicking the "Crear Cuenta" button, it validates the input, creates a JSON object with
 * the provided information, saves the JSON object to a file, and displays a toast message with the
 * registration details.</p>
 *
 * <p>Author: Jose Barquero</p>
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextCarnet;
    private Spinner spinnerResidencia;
    private Boolean isDriver;

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
        setContentView(R.layout.register_screen);

        // Configure the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarRegister);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCarnet = findViewById(R.id.editTextCarnet);
        spinnerResidencia = findViewById(R.id.spinnerResidencia);
        Button buttonCrearCuenta = findViewById(R.id.buttonCrearCuenta);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cantones, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerResidencia.setAdapter(adapter);

        // Set up the listener to handle item selection
        spinnerResidencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selection
                String selectedResidencia = parentView.getItemAtPosition(position).toString();
                Toast.makeText(RegisterActivity.this, "Residencia seleccionada: " + selectedResidencia, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Called when no item is selected
            }
        });

        // Set up click listener for "Crear Cuenta" button
        buttonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from the input fields
                String nombre = editTextNombre.getText().toString();
                String carnet = editTextCarnet.getText().toString();

                if (nombre.isEmpty() || carnet.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate that the name contains only letters
                if (!isValidName(nombre)) {
                    Toast.makeText(RegisterActivity.this, "Name should only contain letters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate that the ID contains only numbers
                if (!isValidCarnet(carnet)) {
                    Toast.makeText(RegisterActivity.this, "ID should only contain numbers", Toast.LENGTH_SHORT).show();
                    return;
                }

                String residenciaSeleccionada = spinnerResidencia.getSelectedItem().toString();
                isDriver=Boolean.FALSE;
                // Create a JSON object with the validated data
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message","register");
                    jsonObject.put("bool",isDriver);
                    jsonObject.put("nombre", nombre);
                    jsonObject.put("carnet", carnet);
                    jsonObject.put("residencia", residenciaSeleccionada);

                    Toast.makeText(RegisterActivity.this, "Data sended", Toast.LENGTH_LONG).show();

                    // Navigate back to the main activity
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
                    return;
                }
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
     * Validate that the name contains only letters.
     *
     * @param name The entered name.
     * @return True if the name is valid, false otherwise.
     */
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
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
     * Validate that the residence contains only letters.
     *
     * @param residencia The entered residence.
     * @return True if the residence is valid, false otherwise.
     */
    private boolean isValidResidencia(String residencia) {
        return residencia.matches("[a-zA-Z]+");
    }
}
