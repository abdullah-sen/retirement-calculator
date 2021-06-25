package com.asen.retirementagecalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCenter.start(
            application, "d482ec03-3ea7-40cf-b1bf-81e8c5bae8be",
            Analytics::class.java, Crashes::class.java
        )

        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val monthlySavingEditText = findViewById<EditText>(R.id.monthlySavingsEditText)
        val interestRateEditText = findViewById<EditText>(R.id.interestEditText)
        val currentAgeEditText = findViewById<EditText>(R.id.ageEditText)
        val retirementAgeEditText = findViewById<EditText>(R.id.retirementEditText)
        val currentSavingEditText = findViewById<EditText>(R.id.currentEditText)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        calculateButton.setOnClickListener {
            try {
                val monthlySaving = monthlySavingEditText.text.toString().toFloat()
                val interestRate = interestRateEditText.text.toString().toFloat()
                val currentAge = currentAgeEditText.text.toString().toInt()
                val retirementAge = retirementAgeEditText.text.toString().toInt()
                val currentSaving = currentSavingEditText.text.toString().toFloat()

                val properties: HashMap<String, String> = HashMap()
                properties["monthly_saving"] = monthlySaving.toString()
                properties["interest_rate"] = interestRate.toString()
                properties["current_age"] = currentAge.toString()
                properties["retirement_age"] = retirementAge.toString()
                properties["current_saving"] = currentSaving.toString()

                if (interestRate <= 0) {
                    Analytics.trackEvent("wrong_interest_rate", properties)
                }
                if (retirementAge <= currentAge) {
                    Analytics.trackEvent("wrong_age", properties)
                }
                resultTextView.text = "At the current rate of $interestRate%, saving \$$monthlySaving a month you will have \$X by $retirementAge."

            } catch (e: Exception) {
                Analytics.trackEvent(e.toString())
            }

        }
    }
}