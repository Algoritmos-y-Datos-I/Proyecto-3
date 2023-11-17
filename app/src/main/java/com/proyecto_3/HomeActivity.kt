package com.proyecto_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.proyecto_3.ui.theme.Proyecto_3Theme


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val button = findViewById<View>(R.id.Butt_Regis)
        button.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)

        }
    }
}