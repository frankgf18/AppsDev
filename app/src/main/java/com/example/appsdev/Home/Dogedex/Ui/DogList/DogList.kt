package com.example.appsdev.Home.Dogedex.Ui.DogList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.appsdev.Core.BaseAdapter
import com.example.appsdev.Core.Utils.activarProgressBar
import com.example.appsdev.Core.Utils.toast
import com.example.appsdev.Home.Dogedex.Di.Api.ApiResponseStatus
import com.example.appsdev.R
import com.example.appsdev.databinding.DogListItemBinding
import com.example.appsdev.databinding.FragmentDogListBinding
import com.example.appsdev.databinding.FragmentDogedexBinding
import com.jjmf.dogedex.Model.Dog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogList : Fragment() {
    private lateinit var binding: FragmentDogListBinding
    private val viewModel: DogListViewModel by viewModels()
    private val adaptador : BaseAdapter<Dog> = object : BaseAdapter<Dog>(emptyList()){
        override fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<Dog> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_list_item,parent,false)
            return object : BaseAdapterViewHolder<Dog>(view){
                override fun bind(entity: Dog) {
                    DogListItemBinding.bind(view).apply {
                        dogName.text = entity.name
                        Glide.with(this@DogList).load(entity.imageUrl).into(ivDog)
                        card.setOnClickListener {

                            val dir = DogListDirections.actionDogListToDogDetalle(entity)
                            findNavController().navigate(dir)
                        }
                    }
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dog_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDogListBinding.bind(view)

        init()
    }

    private fun init() {
        val recycler = binding.dogRecycler
        recycler.adapter = adaptador
        viewModel.dogList.observe(viewLifecycleOwner){
            adaptador.update(it)
        }
        viewModel.status.observe(viewLifecycleOwner){
            requireActivity().apply {
                when(it){
                    is ApiResponseStatus.Loading -> {
                        activarProgressBar(true)
                    }
                    is ApiResponseStatus.Succes -> {
                        activarProgressBar(false)
                    }
                    is ApiResponseStatus.Error -> {
                        toast(it.message)
                        activarProgressBar(false)
                    }
                }
            }
        }
    }
}