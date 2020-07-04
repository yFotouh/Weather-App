package com.task.parenttechnicaltask.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

open class BaseAppCompatActivity : AppCompatActivity() {
    protected fun getFrameResource(): Int {
        return 0
//        R.id.main_frame
    }

    fun navigateToFragment(
        fragment: Fragment,
        addToBackStack: Boolean
    ) {
        val ft: FragmentTransaction =
            this.getSupportFragmentManager().beginTransaction()
        ft.replace(getFrameResource(), fragment, fragment.javaClass.simpleName)
        if (addToBackStack) ft.addToBackStack(fragment.javaClass.simpleName)
        ft.commitAllowingStateLoss()
    }

    fun navigateToFragment(
        fragment: Fragment,
        resourceId: Int,
        addToBackStack: Boolean
    ) {
        val ft: FragmentTransaction =
            this.getSupportFragmentManager().beginTransaction()
        ft.replace(resourceId, fragment, fragment.javaClass.simpleName)
        if (addToBackStack) ft.addToBackStack(fragment.javaClass.simpleName)
        ft.commitAllowingStateLoss()
    }

    protected fun hideActionBar(): Boolean {
        return true
    }
}