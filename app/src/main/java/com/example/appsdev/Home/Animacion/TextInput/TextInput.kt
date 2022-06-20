package com.example.appsdev.Home.Animacion.TextInput

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.Utils.desplegar
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentTextInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TextInput : BaseFragment<FragmentTextInputBinding>(FragmentTextInputBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        desplegar(tilText,idPadre,btn)
    }
}