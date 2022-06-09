package com.example.appsdev.Home.Tools.ProgressBar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentProgressBarBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Scope

@AndroidEntryPoint
class ProgressBar : Fragment(), {
    private lateinit var binding: FragmentProgressBarBinding
    private val viewModel: ProgressBarViewModel by viewModels()
    private val scope = MainScope()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProgressBarBinding.bind(view)

        init()
    }


    private fun init() = with(binding){
        val hilo = Thread()

        var x = 0
        while (0 <= 100){
            delay(1000)
        }
    }
}