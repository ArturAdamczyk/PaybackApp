package com.art.paybackapp.base

import android.os.Bundle
import androidx.activity.ComponentActivity

abstract class BaseActivity : ComponentActivity() {

    private val viewModels: HashMap<String, BaseViewModel> by lazy { initializeViewModels() }

    abstract fun initializeViewModels(): HashMap<String, BaseViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModels()
    }

    override fun onResume() {
        super.onResume()
        viewModels.forEach {
            it.value.bind()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModels.forEach {
            it.value.unbind()
        }
    }

}