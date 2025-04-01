package com.example.diagnosticapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity created")

        // Initialize buttons
        val btnSensorData: Button = findViewById(R.id.btnSensorData)
        val btnDrivingTips: Button = findViewById(R.id.btnDrivingTips)
        val btnReport: Button = findViewById(R.id.btnReport)

        // Set click listener for "Real-Time Sensor Data"
        btnSensorData.setOnClickListener {
            Log.d(TAG, "Sensor Data button clicked")
            startActivity(Intent(this, SensorDataActivity::class.java))
        }

        // Set click listener for "Driving Tips" (previously "Maintenance Tracker")
        btnDrivingTips.setOnClickListener {
            Log.d(TAG, "Driving Tips button clicked")  // Updated log tag
            startActivity(Intent(this, DrivingTipsActivity::class.java)) // Updated class
        }

        // Set click listener for "View Report"
        btnReport.setOnClickListener {
            Log.d(TAG, "Report button clicked")
            startActivity(Intent(this, ReportActivity::class.java))
        }
    }
}
