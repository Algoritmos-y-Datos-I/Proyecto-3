package com.example.clientmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * FriendsActivity represents the screen to display friends of a user.
 *
 * <p>This activity receives data from PrincipalScreen and displays the friend information.</p>
 *
 * <p>Author: Diego Elizondo</p>
 */
public class FriendsActivity extends AppCompatActivity {

    // Views
    private TextView textViewFriends;

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
        setContentView(R.layout.friends_screen);

        // Configure the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarFriends);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views
        textViewFriends = findViewById(R.id.textViewFriends);

        // Get data from the intent
        Intent intent = getIntent();
        if (intent.hasExtra("nombre") && intent.hasExtra("carnet")) {
            nombre = intent.getStringExtra("nombre");
            carnet = intent.getStringExtra("carnet");

            // Update the text view with user information
            String friendsText = "Amigos de " + nombre + " (" + carnet + "):";
            textViewFriends.setText(friendsText);
        }

        // Create a JSON object with the user data
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", "friends");
            jsonObject.put("nombre", nombre);
            jsonObject.put("carnet", carnet);

            // Display a toast message indicating successful data transmission
            Toast.makeText(FriendsActivity.this, "Data sent", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            // Display a toast message in case of an error creating the JSON object
            Toast.makeText(FriendsActivity.this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
            return;
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
