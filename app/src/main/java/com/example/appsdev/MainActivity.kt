package com.example.appsdev

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import com.example.appsdev.Core.Utils.*
import com.example.appsdev.databinding.ActivityMainBinding
import com.example.appsdev.databinding.AlertLoaderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel : ActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingAlertLoaderBinding: AlertLoaderBinding
    private lateinit var alert : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alert = cargando()
    }

    override fun onBackPressed() {
        if (!RETROCEDER){
            //SI ES VERDADERO, MUESTRA LA ALERT, CASO CONTRARIO LO MISMINIZA
            if (LOADER_ACTIVADO){
                bindingAlertLoaderBinding.tvLoader.text = MENSAJE_LOADER
                alert.show()
            } else alert.dismiss()
            RETROCEDER = true
        }else super.onBackPressed()
    }

    //ALERTA LOADER CARGADO
    fun cargando(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.alert_loader, null, false)
        bindingAlertLoaderBinding = AlertLoaderBinding.bind(view)
        return builder.setView(view).create().apply {
            window!!.setBackgroundDrawableResource(R.drawable.custom_dialog_20dp)
            setCancelable(false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            CAMARA ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    viewModel.isCameraAllowed.value = 7000
                    toast("Los pemrisos fueron aceptados. Abriendo Cámara")
                }/*else {
                    toast("Los permisos fueron denegados por primera vez")
                }*/
            }

            GALLERY ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    viewModel.isCameraAllowed.value = 7001
                    toast("Los pemrisos fueron aceptados. Abriendo Galería")
                }else{
                    toast("Los permisos fueron rechazados por primera vez")
                }
            }
        }

    }
}