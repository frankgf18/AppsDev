package com.example.appsdev.Home.Animacion.AnimacionesHIjos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentHijo1Binding

class Hijo1 : Fragment() {

    private lateinit var binding : FragmentHijo1Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_hijo1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHijo1Binding.bind(view)
        init()
    }

    private fun init()= with(binding){
        btnSubir.setOnClickListener { findNavController().navigate(R.id.action_hijo1_to_hijo2) }
    }

}