package com.example.sensorapp

import android.content.Context
import android.hardware.*
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

const val TAG = "SENSORS"
class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sm: SensorManager
    private lateinit var stepCounterSensor: Sensor
//    var stepCounterSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        Log.d(TAG, sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION).toString())


        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if(p0?.sensor == stepCounterSensor){
            Log.d(TAG, "p0 $p0")
            Log.d(TAG, (p0?.values?.get(0) ?: -1f).toString())
//            Log.d(TAG, p0?.values?.get(0).toString())
            stepContent.text = getString(R.string.sens_val, (p0?.values?.get(0) ?: -1f).toString())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume(){
        super.onResume()
        stepCounterSensor?.also{
            sm.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause(){
        super.onPause()
        sm.unregisterListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
