package com.example.appsdev.Home.Animacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appsdev.Core.BaseAdapter
import com.example.appsdev.Core.Utils.cargarFoto
import com.example.appsdev.Core.Utils.inflarLayout
import com.example.appsdev.Home.ItemsCards
import com.example.appsdev.R
import com.example.appsdev.databinding.CardHomeItemBinding
import com.example.appsdev.databinding.FragmentAnimacionBinding

class Animacion : Fragment() {

    private lateinit var binding : FragmentAnimacionBinding

    private val adaptador: BaseAdapter<ItemsCards> = object : BaseAdapter<ItemsCards>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<ItemsCards> {
            val view = requireContext().inflarLayout(R.layout.card_home_item, parent)
            return object : BaseAdapterViewHolder<ItemsCards>(view) {
                override fun bind(entity: ItemsCards) {
                    CardHomeItemBinding.bind(view).apply {
                        name.text = entity.name
                        image.cargarFoto(entity.image!!)
                        cardItem.setOnClickListener {
                            findNavController().navigate(entity.navigation!!)
                        }
                    }
                }
            }
        }
    }

    var listAnimation = listOf(
        ItemsCards(0, "Transiciones", R.id.action_animacion_to_animacionPadre),
        ItemsCards(1, "Bottom Layout", R.id.action_animacion_to_bottomPadre),
        ItemsCards(2,"Text Input", R.id.action_animacion_to_textInput)
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_animacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnimacionBinding.bind(view)
        init()
    }

    private fun init()= with(binding){
        rvAnimationTools.adapter = adaptador
        adaptador.update(listAnimation)
    }
}