package com.example.appsdev.Home.MultiHilos

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsdev.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
/**
 * Ing. Joel Maldonado Fernandez
 * joelmaldonadodev@gmail.com
 * */
class MultiHilos : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_multi_hilos, container, false)
    }

}

fun main(){
    hilos()
}
fun hilos(){
    val thread1 = Thread(Runnable {
        println("HOla Se inicio una descarga")
        Thread.sleep(4000)
        println("Se termino la descarga")
    })

    thread1.start()

    val thread2 = Thread(Runnable {
        println("HOla Se inicio una descarga2")
        Thread.sleep(6000)
        println("Se termino la descarga3")
    })

    thread2.start()
}

