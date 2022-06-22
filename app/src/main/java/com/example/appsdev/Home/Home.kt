package com.example.appsdev.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appsdev.Core.BaseAdapter
import com.example.appsdev.Core.Utils.cargarFoto
import com.example.appsdev.Core.Utils.inflarLayout
import com.example.appsdev.R
import com.example.appsdev.databinding.CardHomeItemBinding
import com.example.appsdev.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
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

    var listItemsHome = listOf<ItemsCards>(
        //La posición no debe repetirse
        //0 = a nulo
        ItemsCards(0, "Tools", R.id.action_home_to_tools),
        ItemsCards(1, "Dogedex", R.id.action_home_to_dogedex),
        ItemsCards(2, "MarketFB" , R.id.action_home_to_marketFB),
        ItemsCards(3, "Animacion", R.id.action_home_to_animacion),
        ItemsCards(4,"Mapas",R.id.action_home_to_mapas)
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.rvItemTools.adapter = adaptador
        adaptador.update(listItemsHome)
        //events()
    }

    /*private fun events() = with(binding) {
        findNavController().apply {
            cardTools.setOnClickListener { navigate(R.id.action_home_to_tools) }
            cardDogedex.setOnClickListener { navigate(R.id.action_home_to_dogedex) }
            cardMarketFB.setOnClickListener { navigate(R.id.action_home_to_marketFB) }
        }
    }*/
}

data class ItemsCards(
    var position: Int?,
    var name:String?,
    var navigation: Int?,
    var image: Int? = 0
)