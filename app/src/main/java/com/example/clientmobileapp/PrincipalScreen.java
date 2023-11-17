package com.example.clientmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * PrincipalScreen represents the main screen of the application after successful login.
 *
 * <p>This activity displays a welcome message with the user's name and student ID.
 * It also provides buttons for different actions like viewing friends, requesting services,
 * checking grades, and logging out.</p>
 *
 * <p>Author: Diego Elizondo</p>
 */
public class PrincipalScreen extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     *
     * <p>This is where most initialization should go: calling {@code setContentView(int)}
     * to inflate the activity's UI, using {@code findViewById(int)} to programmatically interact with widgets
     * in the UI, and configuring click listeners for buttons.</p>
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}.
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_screen);

        // Get data from the Inten
        Intent intent = getIntent();
        if (intent.hasExtra("carnet") && intent.hasExtra("nombre")) {
            String carnet = intent.getStringExtra("carnet");
            String nombre = intent.getStringExtra("nombre");

            // Update the welcome message
            TextView welcomeMessage = findViewById(R.id.welcomeMessage);
            String welcomeText = "¡Bienvenid@ " + nombre + " (" + carnet + ")!";
            welcomeMessage.setText(welcomeText);
        }

        // Initialize buttons and set click listeners

        Button buttonAmigos = findViewById(R.id.buttonAmigos);
        Button buttonSolicitarServicio = findViewById(R.id.buttonSolicitarServicio);
        Button buttonCalificacion = findViewById(R.id.buttonCalificacion);
        Button buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion);

        buttonAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic for the "Friends" button
            }
        });

        buttonSolicitarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic for the "Request Service" button
            }
        });

        buttonCalificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic for the "Grades" button
            }
        });

        buttonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic for the "Log Out" button
                Intent intent = new Intent(PrincipalScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
