package com.example.appsdev.Home.Animacion.ProgressBar

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.databinding.FragmentProgressbarBarraBinding


class ProgressbarBarra : BaseFragment<FragmentProgressbarBarraBinding>(FragmentProgressbarBarraBinding::inflate) {
    private var entero = 10
    private var withContainer = 0
    private var heightContainer = 0
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        events()
        //newFunction()
    }

    private fun init(){
        withContainer = binding.containerProgress.layoutParams.height
        heightContainer = binding.containerProgress.layoutParams.height
    }

    private fun events() {
        binding.btnAumentar.setOnClickListener {
            increase()
        }

        binding.btnDisminuir.setOnClickListener {
            decrease()
        }
    }

    private fun increase(){
        val with = binding.pbLunes.layoutParams.width
        val height = binding.pbLunes.layoutParams.height
        if (entero>=heightContainer){
            //show("Progress aumentado al máximo")
        }else{
            entero += 10
            binding.pbLunes.layoutParams = changeSize(entero)
        }
        Log.d("DATA_PROGRESS_PBLUNES","Ancho: $with - Altura: $height")
        Log.d("DATA_PROGRESS_CONTAINER","Ancho: $withContainer - Altura: $heightContainer")
    }

    private fun decrease(){
        val with = binding.pbLunes.layoutParams.width
        val height = binding.pbLunes.layoutParams.height
        if (entero<=10){
            //show("Progress disminuido al máximo")
        }else{
            entero -= 10
            binding.pbLunes.layoutParams = changeSize(entero)
        }
        Log.d("DATA_PROGRESS_PBLUNES","Ancho: $with - Altura: $height")
        Log.d("DATA_PROGRESS_CONTAINER","Ancho: $withContainer - Altura: $heightContainer")
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun newFunction(){
        binding.btnAumentar.setOnTouchListener(object : OnTouchListener {
            private var mHandler: Handler? = null
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (mHandler != null) return true
                        mHandler = Handler()
                        mHandler!!.postDelayed(mAction, 100)
                    }
                    MotionEvent.ACTION_UP -> {
                        if (mHandler == null) return true
                        mHandler!!.removeCallbacks(mAction)
                        mHandler = null
                    }
                }
                return false
            }

            var mAction: Runnable = object : Runnable {
                override fun run() {
                    increase()
                    mHandler!!.postDelayed(this, 100)
                }
            }
        })

        binding.btnDisminuir.setOnTouchListener(object : OnTouchListener {
            private var mHandler: Handler? = null
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (mHandler != null) return true
                        mHandler = Handler()
                        mHandler!!.postDelayed(mAction, 100)
                    }
                    MotionEvent.ACTION_UP -> {
                        if (mHandler == null) return true
                        mHandler!!.removeCallbacks(mAction)
                        mHandler = null
                    }
                }
                return false
            }

            var mAction: Runnable = object : Runnable {
                override fun run() {
                    decrease()
                    mHandler!!.postDelayed(this, 100)
                }
            }
        })
    }

    private fun changeSize(int:Int):LinearLayout.LayoutParams{
        val layoutParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(0, int, 1f)
        layoutParams.gravity = Gravity.BOTTOM
        return layoutParams
    }
}