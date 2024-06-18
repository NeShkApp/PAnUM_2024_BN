package org.bohdan.panum_lab2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RomanToArabian : AppCompatActivity(), View.OnClickListener {
    lateinit var txtArabian: TextView
    lateinit var txtRoman: TextView
    lateinit var btnM: Button
    lateinit var btnD: Button
    lateinit var btnC: Button
    lateinit var btnL: Button
    lateinit var btnX: Button
    lateinit var btnV: Button
    lateinit var btnI: Button
    lateinit var btnClear2: Button
    lateinit var btnChangeMode2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roman_to_arabian)
        initViews()
    }

    private fun initViews() {
        txtArabian = findViewById(R.id.txtArabianForUser2)
        txtRoman = findViewById(R.id.txtRomanForUser2)
        btnM = findViewById(R.id.btnM)
        btnD = findViewById(R.id.btnD)
        btnC = findViewById(R.id.btnC)
        btnL = findViewById(R.id.btnL)
        btnX = findViewById(R.id.btnX)
        btnV = findViewById(R.id.btnV)
        btnI = findViewById(R.id.btnI)
        btnClear2 = findViewById(R.id.btnClear2)
        btnChangeMode2 = findViewById(R.id.btnChangeMode2)

        btnM.setOnClickListener(this)
        btnD.setOnClickListener(this)
        btnC.setOnClickListener(this)
        btnL.setOnClickListener(this)
        btnX.setOnClickListener(this)
        btnV.setOnClickListener(this)
        btnI.setOnClickListener(this)
        btnClear2.setOnClickListener(this)
        btnChangeMode2.setOnClickListener(this)

        updateButtonStates()
    }

    override fun onClick(v: View?) {
        var romanTxt = txtRoman.text.toString()
        when (v?.id) {
            R.id.btnChangeMode2 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btnClear2 -> {
                if (romanTxt.isNotEmpty()) {
                    romanTxt = romanTxt.dropLast(1)
                    txtRoman.text = romanTxt
                    txtArabian.text = convertRomanToArabic(romanTxt).toString()
                }
            }
            R.id.btnM -> romanTxt += "M"
            R.id.btnD -> romanTxt += "D"
            R.id.btnC -> romanTxt += "C"
            R.id.btnL -> romanTxt += "L"
            R.id.btnX -> romanTxt += "X"
            R.id.btnV -> romanTxt += "V"
            R.id.btnI -> romanTxt += "I"
        }

        if (isValidRomanNumeral(romanTxt)) {
            if (convertRomanToArabic(romanTxt) <= 3999) {
                txtRoman.text = romanTxt
                txtArabian.text = convertRomanToArabic(romanTxt).toString()
            } else {
                romanTxt = romanTxt.dropLast(1)
                Toast.makeText(this, "Maximum value is 3999", Toast.LENGTH_SHORT).show()
            }
        } else {
            romanTxt = romanTxt.dropLast(1)
            Toast.makeText(this, "Invalid Roman numeral format", Toast.LENGTH_SHORT).show()
        }

        txtRoman.text = romanTxt
        txtArabian.text = convertRomanToArabic(romanTxt).toString()
        updateButtonStates()
    }

    private fun convertRomanToArabic(roman: String): Int {
        val romanNumerals = mapOf(
            'M' to 1000,
            'D' to 500,
            'C' to 100,
            'L' to 50,
            'X' to 10,
            'V' to 5,
            'I' to 1
        )
        var result = 0
        var prevValue = 0
        for (char in roman.reversed()) {
            val value = romanNumerals[char] ?: 0
            if (value < prevValue) {
                result -= value
            } else {
                result += value
            }
            prevValue = value
        }
        return result
    }

    private fun isValidRomanNumeral(roman: String): Boolean {
        val validRomanNumeralRegex = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$".toRegex()
        return validRomanNumeralRegex.matches(roman)
    }

    private fun updateButtonStates() {
        val romanTxt = txtRoman.text.toString()

        btnM.isEnabled = isValidRomanNumeral(romanTxt + "M")
        btnD.isEnabled = isValidRomanNumeral(romanTxt + "D")
        btnC.isEnabled = isValidRomanNumeral(romanTxt + "C")
        btnL.isEnabled = isValidRomanNumeral(romanTxt + "L")
        btnX.isEnabled = isValidRomanNumeral(romanTxt + "X")
        btnV.isEnabled = isValidRomanNumeral(romanTxt + "V")
        btnI.isEnabled = isValidRomanNumeral(romanTxt + "I")
    }
}
