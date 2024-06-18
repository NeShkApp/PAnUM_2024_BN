package org.bohdan.panum1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var editText1: EditText? = null
    private var editText2: EditText? = null
    private var spinner: Spinner? = null
    private var btnEquals: Button? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()


        btnEquals!!.setOnClickListener {
            try {
                val num1 = editText1!!.text.toString().toInt()
                val num2 = editText2!!.text.toString().toInt()
                val result: String

                when (spinner!!.selectedItem.toString()) {
                    "-" -> {
                        result = (num1 - num2).toString()
                        textView!!.text = result
                    }

                    "+" -> {
                        result = (num1 + num2).toString()
                        textView!!.text = result
                    }

                    "*" -> {
                        result = (num1 * num2).toString()
                        textView!!.text = result
                    }

                    "/" -> {
                        if (num2 == 0) {
                            textView!!.text = "Divided number can not be 0!"

                        }
                        result = (num1 / num2).toString()
                        textView!!.text = result
                    }

                    else -> {}
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                textView!!.text = "Please provide valid numbers!"
            }
        }
    }

    private fun initViews() {
        editText1 = findViewById<EditText>(R.id.txtNumber1)
        editText2 = findViewById<EditText>(R.id.txtNumber2)
        spinner = findViewById<Spinner>(R.id.spinner)
        textView = findViewById<TextView>(R.id.textView)
        btnEquals = findViewById<Button>(R.id.btnEquals)
    }
}