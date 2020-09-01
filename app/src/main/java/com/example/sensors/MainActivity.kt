package com.example.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var stepDetector: Sensor
    private var isDetectorSensorPresent = false
    private var stepDetect: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            txtTitle.text = getString(R.string.available)
            stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
            isDetectorSensorPresent = true
        } else {
            txtTitle.text = getString(R.string.not_available)
        }
    }

    @Override
    override fun onResume() {
        super.onResume()

        if (isDetectorSensorPresent)
            sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL)
    }

    @Override
    override fun onPause() {
        super.onPause()

        if (isDetectorSensorPresent)
            sensorManager.unregisterListener(this, stepDetector)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {

        if (sensorEvent?.sensor == stepDetector) {
            stepDetect = (stepDetect + sensorEvent.values[0]).toInt()
            txtDetector.text = stepDetect.toString()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
}