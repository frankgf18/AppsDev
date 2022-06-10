package com.example.appsdev.Home.Animacion.AnimacionesHIjos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentHijo3Binding

class Hijo3 : Fragment() {
    private lateinit var binding : FragmentHijo3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hijo3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHijo3Binding.bind(view)
        init()
    }

    private fun init()= with(binding){
        btnBajar.setOnClickListener { requireActivity().onBackPressed() }
    }

}