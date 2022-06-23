package com.example.appsdev.Home.Alertas

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.EstadosResult
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentAlertasBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Alertas : BaseFragment<FragmentAlertasBinding>(FragmentAlertasBinding::inflate) {
    private val viewModel: AlertViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        events()
    }

    private fun events() = with(binding) {

        btnExito.setOnClickListener { exitoAlert() }
        btnAdvertencia.setOnClickListener { advertenciaAlert() }
        btnError.setOnClickListener { errorAlert() }
        btnInformacion.setOnClickListener { informacionAlert() }
        btnCargando.setOnClickListener { cargandoAlert() }
        btnConfirmacion.setOnClickListener { confirmacionAlert() }
        btnImagen.setOnClickListener { imagenAlert() }

    }

    private fun exitoAlert() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.NORMAL_TYPE).apply {
            titleText = "Here's a message!"
            show()
        }
    }

    private fun advertenciaAlert() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE).apply {
            titleText = "Are you sure?"
            contentText = "You won't be able to recover this file!"
            confirmText = "Delete!"
            confirmButtonBackgroundColor = Color.GREEN
            setConfirmClickListener { sDialog -> sDialog.dismissWithAnimation() }
            setCancelButton("Cancel") { sDialog -> sDialog.dismissWithAnimation() }
            show()
        }
    }

    private fun errorAlert() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE).apply {
            titleText = "Oops..."
            contentText = "Something went wrong!"
            show()
        }
    }

    private fun informacionAlert() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE).apply {
            titleText = "Oops..."
            contentText = "Something went wrong!"
            show()
        }
    }

    private fun cargandoAlert() {

        /*val pDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading ..."
        pDialog.setCancelable(false)*/

        viewModel.cargando().observe(viewLifecycleOwner) {
            when (it) {
                is EstadosResult.Cargando -> {
                    //TODO en mantenimiento
                    //requireContext().cargandoAlerta(it.bool)
                }
                is EstadosResult.Correcto -> {
                    Toast.makeText(requireContext(), it.datos, Toast.LENGTH_SHORT).show()
                }
                is EstadosResult.Error -> {

                }
            }
        }
    }

    private fun confirmacionAlert() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE).apply {
            titleText = "Are you sure?"
            contentText = "Won't be able to recover this file!"
            confirmText = "Yes,delete it!"
            setConfirmClickListener { sDialog ->
                sDialog.apply {
                    titleText = "Deleted!"
                    contentText = "Your imaginary file has been deleted!"
                    confirmText = "OK"
                    setConfirmClickListener(null)
                    changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                }
            }
            show()
        }
    }

    private fun imagenAlert() {
        SweetAlertDialog(requireContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE).apply {
            titleText = "Sweet!"
            contentText = "Here's a custom image."
            setCustomImage(R.drawable.perro)
            this.contentTextSize = 40
            show()
        }
    }

}