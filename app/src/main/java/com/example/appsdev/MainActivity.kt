package com.example.appsdev

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.appsdev.Core.Utils.LOADER_ACTIVADO
import com.example.appsdev.Core.Utils.MENSAJE_LOADER
import com.example.appsdev.Core.Utils.RETROCEDER
import com.example.appsdev.databinding.ActivityMainBinding
import com.example.appsdev.databinding.AlertLoaderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
}