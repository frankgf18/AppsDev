package com.example.appsdev.Core

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.example.appsdev.Core.Utils.activarProgressBar

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate:Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private fun navigation(): NavController?{
        return view?.let {
            Navigation.findNavController(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflate.invoke(inflater,container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun gotoActivity(activityClass:Class<*>, bundle: Bundle? = null, destroy:Boolean = true){
        val intent = Intent(requireContext(),activityClass)
        bundle?.let {
            intent.putExtras(it)
        }
        requireContext().startActivity(intent)
        if (destroy){
            activity?.finish()
        }
    }

    protected fun <T>result(result:EstadosResult<T>, evento: (T?)->Unit){
        when(result){
            EstadosResult.Cargando -> requireActivity().activarProgressBar(true)
            is EstadosResult.Correcto -> {
                requireActivity().activarProgressBar(false)
                evento(result.datos)
            }
            is EstadosResult.Error -> {
                requireActivity().activarProgressBar(false)
                show("${result.codigoError} - ${result.mensajeError}")
            }
        }
    }

    protected fun navigateToAction(action:Int){
        navigation()?.navigate(action)
    }

    protected fun navigateToDirections(directions: NavDirections){
        navigation()?.navigate(directions)
    }

    protected fun show(text:String, duration: Int = Toast.LENGTH_SHORT){
        Toast.makeText(requireContext(), text, duration).show()
    }

    protected fun delay(seconds:Long, event:(Boolean)->Unit){
        Handler(Looper.getMainLooper()).postDelayed({event(true) },seconds)
    }

    @MainThread
    protected inline fun <T> LiveData<T>.observe(crossinline onChanged: (T)->Unit): Observer<T> {
        val wrappedObserver = Observer<T>{value->
            onChanged.invoke(value)
        }
        observe(viewLifecycleOwner,wrappedObserver)
        return wrappedObserver
    }

}