package com.example.appsdev.Core.Utils

import android.app.Activity
import android.content.Context
import android.widget.Toast


fun Activity.activarProgressBar(activar: Boolean, text: String = "Cargando...") {
    LOADER_ACTIVADO = if (activar) {
        MENSAJE_LOADER = text; true
    } else false
    RETROCEDER = false
    this.onBackPressed()
}

fun Context.toast(string: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, string, duration).show()