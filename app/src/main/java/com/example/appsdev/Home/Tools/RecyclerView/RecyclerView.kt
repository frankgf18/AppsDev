package com.example.appsdev.Home.Tools.RecyclerView

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.example.appsdev.Core.BaseAdapter
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.EstadosResult
import com.example.appsdev.Core.Permisos
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentRecyclerViewBinding
import com.example.appsdev.databinding.ImagenesBinding
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


@AndroidEntryPoint
class RecyclerView : BaseFragment<FragmentRecyclerViewBinding>(FragmentRecyclerViewBinding::inflate) {
    private val viewModel : RecyclerViewModel by viewModels()
    private val adaptador = object :BaseAdapter<Foto>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<Foto> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.imagenes,parent,false)
            val bind2 = ImagenesBinding.bind(view)
            return object :BaseAdapterViewHolder<Foto>(view){
                override fun bind(entity: Foto) {
                    Glide.with(requireContext()).load(entity.foto).into(bind2.icono)
                    bind2.texto.text = entity.uri
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        events()
    }

    private fun init() {
        binding.recycler.adapter = adaptador
    }

    private var imagen:Uri? = null

    private fun events() {
        binding.btnAlerta.setOnClickListener {
            val alert = SweetAlertDialog(requireContext(),SweetAlertDialog.SUCCESS_TYPE)
            alert.titleText = "Imagen"
            alert.contentText = "Seleccione una imagen"
            alert.setCancelButton("Camara"){
                alert.dismiss()
                Permisos(this).camara(){uri, intent ->
                    imagen = uri
                    res.launch(intent)
                }
            }
            alert.setConfirmButton("Galeria"){
                alert.dismiss()
                Permisos(this).galeria {
                    res.launch(it)
                }
            }
            alert.show()
        }
        binding.btnFirebase.setOnClickListener {
            viewModel.multiple(lista){re->
                when(re){
                    EstadosResult.Cargando -> {binding.progressBar.isGone = false}
                    is EstadosResult.Correcto -> {
                        binding.progressBar.isGone = true
                        val mu = mutableListOf<Foto>()
                        re.datos?.forEach {ur->
                            mu.add(Foto(ur.toString(),ur))
                        }
                        adaptador.update(mu)
                    }
                    is EstadosResult.Error -> {binding.progressBar.isGone = true}
                }
            }
        }
    }


    private val lista = mutableListOf<Foto>()

    private val res = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val d = it.data?.data

            if (d!=null) lista.add(Foto(d.toString(),d))
            else lista.add(Foto(imagen.toString(),imagen!!))

            adaptador.update(lista)
        }
    }

    data class Foto(
        val uri:String,
        val foto:Uri
    )
}