package com.example.appsdev.Home.LiveData

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Home.LiveData.Data.UsuarioModel
import com.example.appsdev.databinding.FragmentDosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DosFragment : BaseFragment<FragmentDosBinding>(FragmentDosBinding::inflate) {
    private val viewModel: DosViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGuardar.setOnClickListener {
            val nombre = binding.edtNombre.text.toString()
            val apellido = binding.edtApellido.text.toString()
            val us = UsuarioModel(nombre, apellido)
            viewModel.insertar(us)
            binding.edtNombre.setText("")
            binding.edtApellido.setText("")
        }
    }
}