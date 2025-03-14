package com.eonoohx.mituxtlaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eonoohx.mituxtlaapp.ui.MiTuxtlaAppScreen
import com.eonoohx.mituxtlaapp.ui.theme.MiTuxtlaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiTuxtlaAppTheme {
                MiTuxtlaAppScreen()
            }
        }
    }
}