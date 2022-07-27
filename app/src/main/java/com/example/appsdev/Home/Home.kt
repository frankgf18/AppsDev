package com.example.appsdev.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.appsdev.Core.BaseAdapter
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.Utils.cargarFoto
import com.example.appsdev.Core.Utils.inflarLayout
import com.example.appsdev.Core.VerificarInternet
import com.example.appsdev.R
import com.example.appsdev.databinding.CardHomeItemBinding
import com.example.appsdev.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val adaptador: BaseAdapter<ItemsCards> = object : BaseAdapter<ItemsCards>(emptyList()) {
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<ItemsCards> {
            val view = requireContext().inflarLayout(R.layout.card_home_item, parent)
            return object : BaseAdapterViewHolder<ItemsCards>(view) {
                override fun bind(entity: ItemsCards) {
                    CardHomeItemBinding.bind(view).apply {
                        entity.apply {
                            tvName.text = name
                            image?.let { ivIcono.cargarFoto(it) }
                            cardItem.setOnClickListener {
                                findNavController().navigate(navigation)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvItemTools.adapter = adaptador
        adaptador.update(listItemsHome.sortedByDescending { it.id })
        VerificarInternet(requireContext()).observe(viewLifecycleOwner){
            show(it.toString())
        }
    }

    private var listItemsHome = listOf(
        ItemsCards(1, "Tools", R.id.action_home_to_tools),
        ItemsCards(2, "Dogedex", R.id.action_home_to_dogedex, R.drawable.ic_dogedex),
        ItemsCards(3, "MarketFB" , R.id.action_home_to_marketFB, R.drawable.ic_market),
        ItemsCards(4, "Animacion", R.id.action_home_to_animacion, R.drawable.ic_market),
        ItemsCards(5,"Mapas",R.id.action_home_to_mapas, R.drawable.ic_mapa),
        ItemsCards(6,"Alertas",R.id.action_home_to_alertas, R.drawable.ic_alerta),
        ItemsCards(7,"Progress",R.id.action_home_to_loader, R.drawable.img_progress),
        ItemsCards(8,"Permisos",R.id.action_home_to_solicitarPermisos, R.drawable.ic_permisos_usuario),
        ItemsCards(9,"Lottie",R.id.action_home_to_lottie,R.drawable.ic_lottie),
        ItemsCards(10,"DiffUtil",R.id.action_home_to_diffUtil,R.drawable.ic_recycler_view),
        ItemsCards(11,"MultiHilos",R.id.action_home_to_diffUtil,R.drawable.ic_hilos),
        ItemsCards(12,"Puzzle",R.id.action_home_to_puzzleFragment, R.drawable.ic_puzzle),
        ItemsCards(13,"Swipe",R.id.action_home_to_swipeFragment, R.drawable.ic_recycler_view),
        ItemsCards(14, "compressor",R.id.action_home_to_compressorFragment, R.drawable.ic_compressor),
        ItemsCards(15, "Grafico",R.id.action_home_to_graficoFragment, R.drawable.ic_rafico),
        ItemsCards(16, "Drawer",R.id.action_home_to_drawerFragment, R.drawable.ic_drawer),
    )

    data class ItemsCards(
        val id:Int,
        val name:String,
        val navigation: Int,
        val image: Int? = null
    )
}
