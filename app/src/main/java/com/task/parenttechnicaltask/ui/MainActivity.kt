package com.task.parenttechnicaltask.ui

import android.os.Bundle
import com.task.parenttechnicaltask.R
import com.task.parenttechnicaltask.ui.base.BaseAppCompatActivity

class MainActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        navigateToFragment(WeatherFragment.newInstance(), false)
    }
}
