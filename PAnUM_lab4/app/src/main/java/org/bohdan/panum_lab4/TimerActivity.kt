package org.bohdan.panum_lab4

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TimerActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var stopwatchButton: Button
    private lateinit var minutesPicker: NumberPicker
    private lateinit var secondsPicker: NumberPicker

    private var timer: CountDownTimer? = null
    private var isRunning: Boolean = false
    private var timeInMillis: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)
        resetButton = findViewById(R.id.resetButton)
        stopwatchButton = findViewById(R.id.stopwatchButton)
        minutesPicker = findViewById(R.id.minutesPicker)
        secondsPicker = findViewById(R.id.secondsPicker)

        minutesPicker.minValue = 0
        minutesPicker.maxValue = 59
        secondsPicker.minValue = 0
        secondsPicker.maxValue = 59

        startButton.setOnClickListener {
            val minutes = minutesPicker.value
            val seconds = secondsPicker.value
            startTimer(minutes, seconds)
        }

        stopButton.setOnClickListener {
            stopTimer()
        }

        resetButton.setOnClickListener {
            resetTimer()
        }

        stopwatchButton.setOnClickListener {
            navigateToStopwatch()
        }

        updateTimerText()
    }

    private fun startTimer(minutes: Int, seconds: Int) {
        if (!isRunning) {
            val totalTimeInMillis = (minutes * 60 + seconds) * 1000L
            timer = object : CountDownTimer(totalTimeInMillis, 10) { // 10 ms = 0.01 s
                override fun onTick(millisUntilFinished: Long) {
                    timeInMillis = millisUntilFinished
                    updateTimerText()
                }

                override fun onFinish() {
                    isRunning = false
                    updateTimerText()
                }
            }.start()

            isRunning = true
        }
    }

    private fun stopTimer() {
        timer?.cancel()
        isRunning = false
    }

    private fun resetTimer() {
        timer?.cancel()
        timeInMillis = 0L
        updateTimerText()
        isRunning = false
    }

    private fun updateTimerText() {
        val minutes = (timeInMillis / 1000) / 60
        val seconds = (timeInMillis / 1000) % 60
        val milliseconds = (timeInMillis % 1000) / 10

        val timerText = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds)
        timerTextView.text = timerText
    }

    private fun navigateToStopwatch() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
