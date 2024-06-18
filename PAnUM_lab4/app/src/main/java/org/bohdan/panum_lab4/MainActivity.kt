package org.bohdan.panum_lab4

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var timerButton: Button

    private var isRunning: Boolean = false
    private var baseTime: Long = 0L
    private var elapsedTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chronometer = findViewById(R.id.chronometer)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        resetButton = findViewById(R.id.resetButton)
        timerButton = findViewById(R.id.timerButton)

        startButton.setOnClickListener {
            startChronometer()
        }

        stopButton.setOnClickListener {
            stopChronometer()
        }

        resetButton.setOnClickListener {
            resetChronometer()
        }

        timerButton.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }

        chronometer.format = "3s:%s:%3s"
    }

    private fun startChronometer() {
        if (!isRunning) {
            chronometer.base = SystemClock.elapsedRealtime() - elapsedTime
            chronometer.start()
            isRunning = true
            baseTime = SystemClock.elapsedRealtime()
        }
    }

    private fun stopChronometer() {
        if (isRunning) {
            chronometer.stop()
            elapsedTime = SystemClock.elapsedRealtime() - chronometer.base
            isRunning = false
        }
    }

    private fun resetChronometer() {
        chronometer.base = SystemClock.elapsedRealtime()
        elapsedTime = 0L
        chronometer.stop()
        isRunning = false
    }

    private fun navigateToS() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
