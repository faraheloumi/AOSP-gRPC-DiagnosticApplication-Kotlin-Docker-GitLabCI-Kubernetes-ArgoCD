package com.example.diagnosticapp

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ekn.gruzer.gaugelibrary.HalfGauge
import com.ekn.gruzer.gaugelibrary.Range
import android.graphics.Color

class SensorDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_data)

        // Initialisation des jauges
        val fuelGauge = findViewById<HalfGauge>(R.id.halfGauge)
        val rpmGauge = findViewById<HalfGauge>(R.id.rpmGauge)

        // Configuration des couleurs pour la jauge de carburant
        fuelGauge.apply {
            addRange(Range().apply { color = Color.RED; from = 0.0; to = 50.0 })  // Rouge (0-50%)
            addRange(Range().apply { color = Color.YELLOW; from = 50.0; to = 75.0 }) // Jaune (50-75%)
            addRange(Range().apply { color = Color.GREEN; from = 75.0; to = 100.0 }) // Vert (75-100%)
            minValue = 0.0
            maxValue = 100.0
        }

        // Configuration des couleurs pour la jauge RPM
        rpmGauge.apply {
            addRange(Range().apply { color = Color.RED; from = 0.0; to = 2000.0 }) // Rouge (0-2000 RPM)
            addRange(Range().apply { color = Color.YELLOW; from = 2000.0; to = 4000.0 }) // Jaune (2000-4000 RPM)
            addRange(Range().apply { color = Color.GREEN; from = 4000.0; to = 6000.0 }) // Vert (4000-6000 RPM)
            minValue = 0.0
            maxValue = 6000.0
        }

        // Simulation des données du capteur
        val currentFuelLevel = 5.0
        val currentEngineRpm = 4500.0 // RPM > 5000
        val reverseSensorValue = 1// 1 means working, 0 means not working

        // Mise à jour des jauges
        fuelGauge.value = currentFuelLevel
        rpmGauge.value = currentEngineRpm

        // Mise à jour des TextView pour afficher les valeurs
        findViewById<TextView>(R.id.sensorDataTextView).text = "Current Fuel Level: $currentFuelLevel%"
        findViewById<TextView>(R.id.rpmDataTextView).text = "Current Engine RPM: $currentEngineRpm RPM"
        findViewById<TextView>(R.id.reverseSensorStatusTextView).text = "Reverse Sensor Value: $reverseSensorValue"

        // Testing the sensors
        val fuelStatus = checkFuelSensorStatus(currentFuelLevel)
        val rpmStatus = checkRpmSensorStatus(currentEngineRpm)
        val reverseStatus = checkReverseSensorStatus(reverseSensorValue)

        // Generate the full report message
        val reportMessage = """
            Fuel Sensor: $fuelStatus
            RPM Sensor: $rpmStatus
            Reverse Sensor: $reverseStatus
        """.trimIndent()

        // Save the message in SharedPreferences for later display
        val sharedPref = getSharedPreferences("DiagnosticPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("REPORT_MESSAGE", reportMessage)
            commit() // Apply changes immediately
        }

        // Display the saved message in the report page TextView
       // findViewById<TextView>(R.id.reportConclusionTextView).text = reportMessage
    }

    // Check the fuel sensor status
    fun checkFuelSensorStatus(fuelLevel: Double): String {
        return if (fuelLevel < 10) {
            "⚠️ Fuel level is low, sensor is working."
        } else {
            "✅ Fuel sensor is working properly."
        }
    }


    // Check the RPM sensor status
    fun checkRpmSensorStatus(rpm: Double): String {
        return if (rpm > 5000) {
            "⚠️ High RPM detected!"
        } else {
            "✅ Normal RPM detected"
        }
    }

    // Check the reverse sensor status
    fun checkReverseSensorStatus(sensorValue: Int): String {
        return if (sensorValue == 1) {
            "✅ Reverse sensor is working"
        } else {
            "❌ Reverse sensor is not working"
        }
    }
}
