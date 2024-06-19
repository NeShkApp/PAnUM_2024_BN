package org.bohdan.panum3

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtPaceMinutes: EditText
    private lateinit var edtPaceKilometers: EditText
    private lateinit var edtDistance: EditText
    private lateinit var edtTargetMinutes: EditText
    private lateinit var edtTargetDistance: EditText
    private lateinit var txtResult: TextView
    private lateinit var btnCalculatePace: Button
    private lateinit var btnCalculateTarget: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        edtPaceMinutes = findViewById(R.id.edtPaceMinutes)
        edtPaceKilometers = findViewById(R.id.edtPaceKilometers)
        edtDistance = findViewById(R.id.edtDistance)
        edtTargetDistance = findViewById(R.id.edtTargetDistance)
        edtTargetMinutes = findViewById(R.id.edtTargetMinutes)
        txtResult = findViewById(R.id.txtResult)
        btnCalculatePace = findViewById(R.id.btnCalculatePace)
        btnCalculateTarget = findViewById(R.id.btnCalculateTarget)

        btnCalculatePace.setOnClickListener(this)
        btnCalculateTarget.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCalculatePace -> calculateFromPace()
            R.id.btnCalculateTarget -> calculateTarget()
        }
    }

    private fun calculateFromPace() {
        val paceMinutes = edtPaceMinutes.text.toString().toIntOrNull()
        val paceKilometers = edtPaceKilometers.text.toString().toIntOrNull()

        if (paceMinutes == null || paceKilometers == null) {
            Toast.makeText(this, "Please enter valid pace values", Toast.LENGTH_SHORT).show()
            return
        }

        val speed = (paceKilometers * 60.0) / paceMinutes
        val marathonTime = calculateTime(42.195, speed)
        val halfMarathonTime = calculateTime(21.0975, speed)
        val distance = edtDistance.text.toString().toDoubleOrNull() ?: 0.0
        val distanceTime = calculateTime(distance, speed)

        txtResult.text = "Speed: %.2f km/h\nMarathon Time: %s\nHalf Marathon Time: %s\nTime for %.2f km: %s".format(
            speed, formatTime(marathonTime), formatTime(halfMarathonTime), distance, formatTime(distanceTime))
    }

    private fun calculateTarget() {
        val distance = edtTargetDistance.text.toString().toDoubleOrNull() ?: 0.0
        val minutes = edtTargetMinutes.text.toString().toIntOrNull() ?: 0

        if (distance == 0.0 || minutes == 0) {
            Toast.makeText(this, "Please enter valid distance and time", Toast.LENGTH_SHORT).show()
            return
        }

        val paceInMinutes = minutes / distance
        val paceMinutes = paceInMinutes.toInt()
        val paceSeconds = ((paceInMinutes - paceMinutes) * 60).toInt()
        val speed = 60 * distance / minutes

        txtResult.text = "Pace: %d:%02d min/km\nSpeed: %.2f km/h".format(paceMinutes, paceSeconds, speed)
    }

    private fun calculateTime(distance: Double, speed: Double): Double {
        return distance / speed
    }

    private fun formatTime(time: Double): String {
        val totalSeconds = (time * 3600).toInt()
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return "%d:%02d:%02d".format(hours, minutes, seconds)
    }
}
