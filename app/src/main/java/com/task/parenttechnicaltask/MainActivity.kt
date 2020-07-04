package com.task.parenttechnicaltask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        navigateToFragment(WeatherFragment.newInstance(), false)
    }
}
