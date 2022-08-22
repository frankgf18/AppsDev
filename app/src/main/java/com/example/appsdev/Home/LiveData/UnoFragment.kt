package com.example.appsdev.Home.LiveData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.appsdev.Core.BaseAdapter
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Home.LiveData.Data.UsuarioModel
import com.example.appsdev.R
import com.example.appsdev.databinding.CardLivedataBinding
import com.example.appsdev.databinding.FragmentUnoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnoFragment : BaseFragment<FragmentUnoBinding>(FragmentUnoBinding::inflate) {
    private val adaptador: BaseAdapter<UsuarioModel> =
        object : BaseAdapter<UsuarioModel>(emptyList()) {
            override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<UsuarioModel> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_livedata, parent, false)
                return object : BaseAdapterViewHolder<UsuarioModel>(view) {
                    override fun bind(entity: UsuarioModel) {
                        CardLivedataBinding.bind(view).apply {
                            tvNombre.text = entity.nombre
                            tvApellido.text = entity.apellido
                        }
                    }
                }
            }
        }
    private val viewModel: UnoViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.adapter = adaptador

        viewModel.obtener().observe(viewLifecycleOwner) { res ->
            result(res) { lis ->
                lis?.let {
                    it.observe(viewLifecycleOwner) {lista->
                        adaptador.update(lista)
                    }
                }
            }
        }
    }
}