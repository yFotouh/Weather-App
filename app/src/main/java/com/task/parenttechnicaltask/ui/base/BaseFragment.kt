package com.task.parenttechnicaltask.ui.base

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.task.parenttechnicaltask.R

open class BaseFragment : Fragment() {
    lateinit var currentView: View
    lateinit var progressBar: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentView = inflater.inflate(getLayoutId(), container, false)
//        mainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
//        mainBinding.viewmodel = weatherViewModel
//        mainBinding.viewmodel1 = cityViewModel
//        mainBinding.lifecycleOwner = this
//        return mainBinding.root
        return currentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = currentView.findViewById<ProgressBar>(R.id.pBar).apply { }
        doOnViewCreated(view, savedInstanceState)
    }

    open protected fun doOnViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    open protected fun getLayoutId(): Int {
        return 0
    }

    open protected fun toggleProgressBarState(state: Boolean) {
        if (state)
            progressBar.visibility = View.VISIBLE
        else
            progressBar.visibility = View.GONE
    }

}