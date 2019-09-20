package fi.lasicaine.astepcounter

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var activityRunning: Boolean = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        activityRunning = true
        val countSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (countSensor != null) {
            sensorManager?.registerListener(
                this, countSensor,
                SensorManager.SENSOR_DELAY_UI
            )
        } else {
            label.text = "Step counter sensor not available!"
            Toast.makeText(
                this, "Step counter sensor not available!",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    override fun onPause() {
        super.onPause()
        activityRunning = false
        // if you unregister the last listener, the hardware will stop detecting
        // step events
        // sensorManager.unregisterListener(this);
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (activityRunning) {
            count.text = event.values[0].toString()
        }

    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}
