package com.example.diagnosticapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        Log.d("ReportActivity", "onCreate triggered")
        updateReportMessage()
    }

    override fun onResume() {
        super.onResume()

        Log.d("ReportActivity", "onResume triggered")
        updateReportMessage()
    }

    private fun updateReportMessage() {
        val sharedPref = getSharedPreferences("DiagnosticPrefs", Context.MODE_PRIVATE)
        val reportMessage = sharedPref.getString("REPORT_MESSAGE", "No data available")

        // Check if the message is null or empty and display a default message if necessary
        if (reportMessage.isNullOrEmpty()) {
            findViewById<TextView>(R.id.reportTextView).text = "No report data available."
        } else {
            findViewById<TextView>(R.id.reportTextView).text = reportMessage
        }

        Log.d("ReportActivity", "Report message: $reportMessage")
    }
}
