package com.example.appsdev.Home.Tools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentToolsBinding.bind(view)
        events()
    }

    private fun events() = with(binding) {
        findNavController().apply {
            cardAnyChart.setOnClickListener { navigate(R.id.action_tools_to_anyChart) }
            cardCamara.setOnClickListener { navigate(R.id.action_tools_to_camara) }
            cardRecyclerView.setOnClickListener { navigate(R.id.action_tools_to_recyclerView) }
            cardProgressBar.setOnClickListener { navigate(R.id.action_tools_to_progressBar2) }
            cardTableLayout.setOnClickListener { navigate(R.id.action_tools_to_tableLayoutFragment) }
            cardCalendar.setOnClickListener { navigate(R.id.action_tools_to_calendarFragment) }
        }
    }
}