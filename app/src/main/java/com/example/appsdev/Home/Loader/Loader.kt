package com.example.appsdev.Home.Loader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.EstadosResult
import com.example.appsdev.Core.EstadosResult2
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentLoaderBinding
import dagger.hilt.android.AndroidEntryPoint
/**
 * Ing. Joel Maldonado Fernandez
 * joelmaldonadodev@gmail.com
 * */
@AndroidEntryPoint
class Loader : BaseFragment<FragmentLoaderBinding>(FragmentLoaderBinding::inflate) {
    private val viewModel : LoaderViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

    }

    private fun init() = with(binding){
        btnCargar.setOnClickListener {
            cargar()
        }
    }

    private fun cargar() = with(binding) {
        viewModel.cargando().observe(viewLifecycleOwner){
            when(it){
                is EstadosResult2.Cargando -> {
                    loader1.progress = it.bool
                    tvLoader1.text = "${it.bool}%"
                }
                is EstadosResult2.Correcto -> {

                }
                is EstadosResult2.Error -> {

                }
            }
        }
    }
}