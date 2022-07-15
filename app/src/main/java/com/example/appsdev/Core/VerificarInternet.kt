package com.example.appsdev.Core

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import androidx.lifecycle.LiveData

class VerificarInternet(context: Context) : LiveData<Boolean>() {
    private lateinit var networkCallback: NetworkCallback
    private var connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //Funciona cuando la aplicacion esta activa
    override fun onActive() {
        super.onActive()
        verify()
        if (SDK_INT >= N) {
            //Detecta si se activo los datos o el WIFI
            connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallBack())
        }
    }

    //Funciona cuando la aplicación esta en modo resumen
    override fun onInactive() {
        super.onInactive()
        verify()
        connectivityManagerCallBack()
    }

    //Este metodo verifica si esta o no los datos prendidos del celular
    private fun connectivityManagerCallBack(): NetworkCallback {
        networkCallback = object : NetworkCallback() {

            //Verifica si la red esta desconectada
            override fun onLost(n: Network) {
                super.onLost(n)
                //Envia el valor booleano
                postValue(verify())
            }

            //Verifica si la red esta desconectada
            override fun onAvailable(n: Network) {
                super.onAvailable(n)
                //Envia el valor booleano
                postValue(verify())
            }
        }
        return networkCallback
    }

    fun verify(): Boolean = try {
        Runtime.getRuntime().exec("ping -c 1 www.google.com").waitFor() == 0
    } catch (e: Exception) {
        false
    }
}