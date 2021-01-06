package com.example.dma_finanzrechner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var endSumText : TextView;


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calcBtn = findViewById(R.id.calculateButton) as Button;
        val startFundText = (findViewById(R.id.startFundValue) as EditText)
        val savingsText = (findViewById(R.id.savingsValue) as EditText)
        val savingsPeriodText = (findViewById(R.id.savingPeriodValue) as EditText)
        val yInterestMinText = (findViewById(R.id.minInterestValue) as EditText)
        val yInterestMaxText = (findViewById(R.id.maxInterestValue) as EditText)

        endSumText = (findViewById(R.id.endValue) as TextView);

        calcBtn.setOnClickListener()
        {

            val startFund = startFundText.text.toString();
            val savings = savingsText.text.toString();
            val savingsPeriod = savingsPeriodText.text.toString().toIntOrNull();
            val yInterestMin = yInterestMinText.text.toString().toDoubleOrNull();
            val yInterestMax = yInterestMaxText.text.toString().toDoubleOrNull();

            calculateFormula(startFund, savings, savingsPeriod, yInterestMin, yInterestMax);
        }



    }

    fun calculateFormula(startfund : String, savings : String, period : Int?, interestMin : Double?, interestMax : Double?)
    {
        val tmp = startfund.toDouble() * Math.pow((1 + ((interestMin!! + interestMax!!)/2).toDouble()/100.00), period!!.toDouble());

        println("Ungerundet: $tmp")
        val solution = roundOffDecimal(tmp);
        println("Gerundet: $solution")
        endSumText.text = solution.toString();
    }

    fun roundOffDecimal(number: Double): Double?
    {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

}