package com.example.appsdev.Home.Swipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsdev.Core.BaseAdapter
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Core.Utils.inflarLayout
import com.example.appsdev.R
import com.example.appsdev.databinding.CardRecyclerBinding
import com.example.appsdev.databinding.FragmentSwipeBinding
/**
 * Ing. Joel Maldonado Fernandez
 * joelmaldonadodev@gmail.com
 * */
class SwipeFragment : BaseFragment<FragmentSwipeBinding>(FragmentSwipeBinding::inflate) {
    private val adaptador : BaseAdapter<String> = object :BaseAdapter<String>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<String> {
            val view = requireContext().inflarLayout(R.layout.card_recycler,parent)
            return object :BaseAdapterViewHolder<String>(view){
                override fun bind(entity: String) {
                    CardRecyclerBinding.bind(view).apply {
                        tvNombre.text = entity
                    }
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.recycler.adapter = adaptador
        adaptador.update(listOf("Joel","Andres","Frank","Pedro","Maria"))
    }
}