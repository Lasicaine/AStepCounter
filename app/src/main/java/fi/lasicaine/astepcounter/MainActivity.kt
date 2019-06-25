package fi.lasicaine.astepcounter

import android.app.Activity
import android.content.Context
import android.hardware.*
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var count: TextView? = null
    private var activityRunning: Boolean = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        count = findViewById<View>(R.id.count) as TextView

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        activityRunning = true
        val countSensor = sensorManager!!
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (countSensor != null) {
            sensorManager!!.registerListener(this, countSensor,
                    SensorManager.SENSOR_DELAY_UI)
        } else {
            Toast.makeText(this, "Step counter sensor not available!",
                    Toast.LENGTH_LONG).show()
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
            count!!.text = event.values[0].toString()
        }

    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}
