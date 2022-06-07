package com.example.appsdev.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        events()
    }

    private fun events() = with(binding) {
        findNavController().apply {
            cardTools.setOnClickListener { navigate(R.id.action_home_to_tools) }
            cardDogedex.setOnClickListener { navigate(R.id.action_home_to_dogedex) }
            cardMarketFB.setOnClickListener { navigate(R.id.action_home_to_marketFB) }
        }
    }
}