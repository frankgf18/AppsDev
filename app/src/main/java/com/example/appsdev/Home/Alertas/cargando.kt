package com.example.appsdev.Home.Alertas

import android.content.Context
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog

fun Context.cargandoAlerta(bool: Boolean): SweetAlertDialog{

    val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
    pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
    pDialog.titleText = "Cargando..."
    pDialog.setCancelable(false)
    if (bool){
        pDialog.show()
    }else{
        pDialog.dismissWithAnimation()
    }
    return pDialog
}