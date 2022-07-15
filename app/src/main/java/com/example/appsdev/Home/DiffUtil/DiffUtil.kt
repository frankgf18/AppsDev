package com.example.appsdev.Home.DiffUtil

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsdev.Core.BaseAdapter
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.Utils.inflarLayout
import com.example.appsdev.R
import com.example.appsdev.databinding.CardObjBinding
import com.example.appsdev.databinding.FragmentDiffUtilBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class DiffUtil : BaseFragment<FragmentDiffUtilBinding>(FragmentDiffUtilBinding::inflate) {
    private val adaptador: BaseAdapter<Obj> = object : BaseAdapter<Obj>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<Obj> {
            val view = requireContext().inflarLayout(R.layout.card_obj, parent)
            return object : BaseAdapterViewHolder<Obj>(view) {
                override fun bind(entity: Obj) {
                    CardObjBinding.bind(view).apply {
                        tvNombre.text = entity.nombre
                        Glide.with(requireContext()).load(entity.imagen).into(ivFoto)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun init() {
        binding.recycler.adapter = adaptador

        GlobalScope.launch(Dispatchers.IO) {

            val result = mutableListOf<Obj>()
            for (i in 0..20) {
                result.add(Obj(i.toString(), "https://randomuser.me/api/portraits/men/94.jpg"))
                withContext(Dispatchers.Main){
                    adaptador.update(result)
                }
                delay(1000)
            }
        }
    }

    data class Obj(
        val nombre: String,
        val imagen: String
    )
}