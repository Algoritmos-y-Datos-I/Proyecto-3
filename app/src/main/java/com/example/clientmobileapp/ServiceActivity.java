package com.example.clientmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * ServiceActivity represents the screen to display the requested service information.
 *
 * <p>This activity receives data (nombre and carnet) from PrincipalScreen and displays the service information.</p>
 *
 * <p>@Author: Diego Elizondo</p>
 * @version 1.2
 */
public class ServiceActivity extends AppCompatActivity {

    // Views
    private TextView textViewService;

    // User data
    private String nombre;
    private String carnet;

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
        setContentView(R.layout.service_screen);

        // Configure the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        textViewService = findViewById(R.id.textViewService);

        // Get data from the intent
        Intent intent = getIntent();
        if (intent.hasExtra("nombre") && intent.hasExtra("carnet")) {
            nombre = intent.getStringExtra("nombre");
            carnet = intent.getStringExtra("carnet");

            // Update the text view with service information
            String serviceText = "Viaje solicitado a nombre de " + nombre + " (" + carnet + "):";
            textViewService.setText(serviceText);
        }
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
}
