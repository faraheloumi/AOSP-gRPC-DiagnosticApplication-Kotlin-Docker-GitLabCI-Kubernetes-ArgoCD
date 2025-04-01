package com.example.diagnosticapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DrivingTipsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driving_tips)

        val tipsTextView: TextView = findViewById(R.id.tipsTextView)

        val drivingTips = """
            Tips for optimized and economical driving:
            
            1️⃣ 🚦 Adopt smooth driving
            → Avoid sudden accelerations and braking to save fuel and reduce engine wear.
            
            2️⃣ 🔄 Use engine braking
            → When slowing down, release the accelerator and let the car decelerate naturally instead of braking suddenly.
            
            3️⃣ ⚙️ Shift gears properly
            → In a manual transmission, shift gears between 2000 and 2500 rpm (gasoline) and 1500 to 2000 rpm (diesel).
            
            4️⃣ ⛽️ Avoid driving at too low or too high revs
            → A motor running too low or too high in revolutions consumes more and wears out faster.
            
            5️⃣ 🔧 Check tire pressure
            → Under-inflated tires increase fuel consumption by up to 5% and reduce road handling.
            
            6️⃣ 💨 Lighten the vehicle
            → Avoid driving with a heavily loaded trunk or an unused roof rack that increases wind resistance.
            
            7️⃣ 🚘 Turn off the engine during prolonged stops
            → If you are stopped for more than 30 seconds, turn off the engine to avoid unnecessary consumption.
            
            8️⃣ 🌡️ Don't overuse air conditioning
            → Air conditioning can increase fuel consumption by 10 to 15%, use it sparingly.
            
            9️⃣ 🚦 Anticipate traffic
            → Look far ahead to avoid sudden braking and frequent acceleration.
            
            🔟 🏁 Maintain your vehicle regularly
            → A well-maintained engine (oil change, filters, spark plugs) works better and consumes less.
        """.trimIndent()

        // Set the driving tips to the TextView
        tipsTextView.text = drivingTips
    }
}
