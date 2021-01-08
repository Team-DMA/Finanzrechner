package com.example.dma_finanzrechner

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.BigDecimal.ONE
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    lateinit var endSumText : TextView


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calcBtn = findViewById(R.id.calculateButton) as Button
        val startFundText = (findViewById(R.id.startFundValue) as EditText)
        val savingsText = (findViewById(R.id.savingsValue) as EditText)
        val savingsPeriodText = (findViewById(R.id.savingPeriodValue) as EditText)
        val yInterestMinText = (findViewById(R.id.minInterestValue) as EditText)
        val yInterestMaxText = (findViewById(R.id.maxInterestValue) as EditText)

        endSumText = (findViewById(R.id.endValue) as TextView)

        calcBtn.setOnClickListener()
        {
            val startFund = startFundText.text.toString()
            val savings = savingsText.text.toString()
            val savingsPeriod = savingsPeriodText.text.toString().toIntOrNull()
            val yInterestMin = yInterestMinText.text.toString().toDoubleOrNull()
            val yInterestMax = yInterestMaxText.text.toString().toDoubleOrNull()

            calculateFormula(startFund, savings, savingsPeriod, yInterestMin, yInterestMax)

        }



    }

    fun calculateFormula(startfund: String, savings: String, period: Int?, interestMin: Double?, interestMax: Double?)
    {
        //val compNumberOfYear: Double = period!!.toDouble() * 12; //monthly
        //val tmpValue: Double = savings.toDouble() * Math.pow(1 + ((interestMax!! + interestMin!!) / 2) / 12 / 100, compNumberOfYear)

        /*var tmpValue = 0.00;
        for(i in 1..period!!)
        {
            println("Jahr ${i.toString()}:");
            for(j in 1..12)
            {
                if(j == 1)
                {
                    tmpValue = tmpValue + (startfund.toDouble() * (1 + ((((interestMin!! + interestMax!!)/2)/100)/12)));
                }
                else
                {
                    tmpValue = (tmpValue + savings.toDouble()) * (1+ ((((interestMin!! + interestMax!!)/2)/100)/12));
                }
                println("Monat: " + j.toString() + ": " + tmpValue)
            }
        }*/

        //val tmpValue = fv((interestMax!! / 12 / 100).toBigDecimal(), 1, -(savings.toBigDecimal()), 0.toBigDecimal(), 1) //monatlich
        val tmpValue = fv((interestMax!! / 12 / 100).toBigDecimal(), 12, -(savings.toBigDecimal()), 0.toBigDecimal(), 0) //jährlich
        val dTmpValue: Double = tmpValue!!.toDouble()

        println("Ungerundet: $tmpValue oder $dTmpValue")
        val solution = roundOffDecimal(dTmpValue)
        println("Gerundet: $solution")
        endSumText.text = solution.toString()
    }

    fun fv(intRate: BigDecimal, nper: Int, pmt: BigDecimal, pv: BigDecimal, type: Int): BigDecimal?
    {
        /*
            rate - The interest rate per period.
            nper - The total number of payment periods.
            pmt - The payment made each period. Must be entered as a negative number.
            pv - [optional] The present value of future payments. If omitted, assumed to be zero. Must be entered as a negative number.
            type - [optional] When payments are due. 0 = end of period, 1 = beginning of period. Default is 0.
         */
        val fvPositive = pv.multiply(ONE.add(intRate).pow(nper)).add(pmt.multiply(ONE.add(intRate.multiply(BigDecimal.valueOf(type.toLong())))).multiply(ONE.add(intRate).pow(nper).subtract(ONE)).divide(intRate, 2, RoundingMode.CEILING))
        return fvPositive.negate()
    }

    private fun roundOffDecimal(number: Double): Double?
    {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}