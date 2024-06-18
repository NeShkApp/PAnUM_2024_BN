package org.bohdan.panum_lab2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var txtArabian: TextView
    lateinit var txtRoman: TextView
    lateinit var btn0: Button
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var btn3: Button
    lateinit var btn4: Button
    lateinit var btn5: Button
    lateinit var btn6: Button
    lateinit var btn7: Button
    lateinit var btn8: Button
    lateinit var btn9: Button
    lateinit var btnC: Button
    lateinit var btnM: Button

    private val maxDigits = 4;



    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        initViews();

    }

    private fun initViews() {
        txtArabian = findViewById(R.id.txtArabianForUser)
        txtRoman = findViewById(R.id.txtRomanForUser)
        btn0 = findViewById(R.id.btn0)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        btnC = findViewById(R.id.btnClear)
        btnM = findViewById(R.id.btnChangeMode)

        btn0.setOnClickListener(this)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
        btnC.setOnClickListener(this)
        btnM.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var arabTxt = txtArabian.text.toString()
        var arabInt = 0

        if(!arabTxt.equals("")){
            arabInt = arabTxt.toInt();
        }

        when(v?.id){
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9 -> {
                if (arabInt >= 3999 || arabTxt.length >= maxDigits) {
                    Toast.makeText(this, "Maximum number reached (3999).", Toast.LENGTH_SHORT).show()
                    return
                }else{
                    arabTxt += (v as Button).text
                }
            }

            R.id.btn0 -> {
                if(arabTxt.isEmpty()){
                    Toast.makeText(this, "Number can not start with 0.", Toast.LENGTH_SHORT).show()
                    return
                }else if (arabInt >= 3999 || arabTxt.length >= maxDigits){
                    Toast.makeText(this, "Maximum number reached (3999).", Toast.LENGTH_SHORT)
                        .show()
                    return
                }else{
                    arabTxt += '0'
                }
            }

            R.id.btnClear -> {
                if (arabTxt.isNotEmpty()) {
                    arabTxt = arabTxt.dropLast(1)
                }
            }

            R.id.btnChangeMode -> {
                val intent = Intent(this, RomanToArabian::class.java)
                startActivity(intent)
            }

        }
        txtArabian.text = arabTxt
        txtRoman.text = transformArabToRoman(arabTxt)
    }

    private fun transformArabToRoman(arabTxt: String): String {
        if(arabTxt.isEmpty()) return ""

        val num = arabTxt.toIntOrNull()
        if (num == null || num <= 0 || num > 3999) return "Invalid Number"
        val numPairs = listOf(
            Pair(1000, "M"), Pair(900, "CM"), Pair(500, "D"), Pair(400, "CD"),
            Pair(100, "C"), Pair(90, "XC"), Pair(50, "L"), Pair(40, "XL"),
            Pair(10, "X"), Pair(9, "IX"), Pair(5, "V"), Pair(4, "IV"),
            Pair(1, "I")
        )
        var arabicNumber = num
        val romanStringBuilder = StringBuilder()

        for((value, symbol) in numPairs){
            while(arabicNumber >= value){
                romanStringBuilder.append(symbol)
                arabicNumber -= value
            }
        }

        return romanStringBuilder.toString()
    }



}