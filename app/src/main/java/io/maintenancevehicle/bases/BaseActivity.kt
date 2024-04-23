package io.maintenancevehicle.bases

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(private val bindingInflater: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    val binding by lazy { bindingInflater(layoutInflater) }
    private var isScroll = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observerData()
        initData()
        initView()
        handleEvent()
    }

    open fun initData() {

    }

    open fun initView() {

    }

    open fun handleEvent() {

    }

    open fun observerData() {

    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {

            MotionEvent.ACTION_MOVE -> {
                isScroll = true
            }

            MotionEvent.ACTION_UP -> {
                if (!isScroll) {
                    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
                    binding.root.clearFocus()
                }
                isScroll = false
            }

            MotionEvent.ACTION_DOWN -> {

            }

            else -> {

            }
        }
        return super.dispatchTouchEvent(event)
    }

}