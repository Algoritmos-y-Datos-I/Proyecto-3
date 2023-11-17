package com.example.clientmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The main activity of the mobile app.
 *
 * This activity is responsible for displaying the initial screen of the application
 * and providing navigation options to either register a new account or log in.
 *
 * <p>@Author: Diego Elizondo</p>
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     *
     * This method sets up the initial state of the activity, including the layout
     * and button click listeners.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_first_screen);

        // Initialize buttons
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Set click listeners for buttons
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the RegisterActivity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the LogginActivity
                Intent intent = new Intent(MainActivity.this, LogginActivity.class);
                startActivity(intent);
            }
        });
    }
}
