package com.example.mobileassignment2

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.mobileassignment2.ui.theme.MobileAssignment2Theme

class MainActivity : ComponentActivity() {
    // Custom permission name
    private val CUSTOM_PERMISSION = "com.example.mobileassignment2.MSE412"

    // Permission request launcher
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launchChallengesActivity()
        } else {
            Toast.makeText(
                this,
                "Permission denied to access challenges",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileAssignment2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        onExplicitIntentClick = { checkAndRequestPermission() },
                        onImplicitIntentClick = { startImplicitIntent() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun checkAndRequestPermission() {
        // Check if permission is already granted
        when {
            ContextCompat.checkSelfPermission(
                this,
                CUSTOM_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, launch activity
                launchChallengesActivity()
            }
            else -> {
                // Request the custom permission
                permissionLauncher.launch(CUSTOM_PERMISSION)
            }
        }
    }

    private fun launchChallengesActivity() {
        val explicitIntent = Intent(this, ChallengesActivity::class.java)
        startActivity(explicitIntent)
    }

    private fun startImplicitIntent() {
        val implicitIntent = Intent("com.example.mobileassignment2.ACTION_SHOW_CHALLENGES")
        startActivity(implicitIntent)
    }
}

@Composable
fun MainScreen(
    onExplicitIntentClick: () -> Unit,
    onImplicitIntentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onExplicitIntentClick) {
            Text("Start Activity Explicitly")
        }

        Button(onClick = onImplicitIntentClick) {
            Text("Start Activity Implicitly")
        }
    }
}