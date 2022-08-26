package com.example.appsdev.Home.Tools.Calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentNumeroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NumeroFragment : BaseFragment<FragmentNumeroBinding>(FragmentNumeroBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments.let {
            val code = it?.getInt("KEYFRA")!!

            binding.tvNum.text = code.toString()
        }
    }
}