package com.example.appsdev.Home.Dogedex.Ui.DogDetalle

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentDogDetalleBinding
import com.jjmf.dogedex.Model.Dog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogDetalle : Fragment() {
    private lateinit var binding: FragmentDogDetalleBinding
    private lateinit var dog: Dog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dog_detalle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDogDetalleBinding.bind(view)

        init()
        events()
    }



    private fun init(){
        arguments?.let {
            dog = DogDetalleArgs.fromBundle(it).dog
        }
        setear()
    }

    private fun events() {
        binding.btnClose.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setear() = with(binding){
        dog.apply {
            tvId.text = "#"+id.toString()
            tvName.text = name.toString()
            tvAge.text = lifeExpectancy.toString()+" Años"
            tvTemperatura.text = temperament.toString()
            tvPesoHembra.text = weigthFemale.toString()
            tvAlturaHembra.text = heightFemale.toString()
            tvPesoMacho.text = weigthMale.toString()
            tvAlturaMacho.text = heightMale.toString()
            Glide.with(requireContext()).load(imageUrl).into(ivDog)
        }
    }
}