package com.example.appsdev.Core.Utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isGone
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appsdev.R


fun Activity.activarProgressBar(activar: Boolean, text: String = "Cargando...") {
    LOADER_ACTIVADO = if (activar) {
        MENSAJE_LOADER = text; true
    } else false
    RETROCEDER = false
    this.onBackPressed()
}

fun Context.toast(string: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, string, duration).show()

fun Context.inflarLayout(layout: Int, parent: ViewGroup? = null): View {
    return if (parent != null) LayoutInflater.from(this).inflate(layout, parent,false)
    else LayoutInflater.from(this).inflate(layout, null,false)
}

fun ImageView.cargarFoto(imagen:Int){
    if (imagen == 0 )this.setImageResource(R.drawable.img_por_defecto)
    else this.setImageResource(imagen)
}

fun desplegar(contenido: LinearLayout, padre: LinearLayout, boton: Button) {
    boton.setOnClickListener {
        if (contenido.isGone) {
            TransitionManager.beginDelayedTransition(padre, AutoTransition())
            contenido.visibility = View.VISIBLE
        } else {
            TransitionManager.beginDelayedTransition(padre, AutoTransition())
            contenido.visibility = View.GONE
        }
    }
}