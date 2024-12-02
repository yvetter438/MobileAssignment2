package com.example.MobileAss2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChallengesActivity : AppCompatActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenges)

        val challengeTextView: TextView = findViewById(R.id.challenge_text_view)


        // Example list of challenges (same as before)
        val challenges: Array<String> = arrayOf(
            "Fragmentation: Android devices run on many different versions and hardware",
            "Battery Constraints: Managing power efficiency on mobile devices",
            "Performance Optimization: Ensuring smooth user experience across devices",
            "Security Challenges: Protecting user data and preventing vulnerabilities",
            "Offline Functionality: Supporting app usage without constant internet",
            "Screen Size Diversity: Designing responsive layouts for various screen sizes"
        )

        // Randomly select a challenge
        val randomIndex: Int = (java.lang.Math.random() * challenges.size).toInt()
        challengeTextView.setText(challenges.get(randomIndex))
    }
}