package com.example.appsdev.Home.Tools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentToolsBinding

class Tools : Fragment() {
    private lateinit var binding: FragmentToolsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tools, container, false)
    }
}