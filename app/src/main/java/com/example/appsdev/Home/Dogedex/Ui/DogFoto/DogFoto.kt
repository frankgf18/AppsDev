package com.example.appsdev.Home.Dogedex.Ui.DogFoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentDogFotoBinding

class DogFoto : Fragment() {
    private lateinit var binding: FragmentDogFotoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dog_foto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDogFotoBinding.bind(view)

        init()
    }

    private fun init() {
        arguments?.let {
            val uri = DogFotoArgs.fromBundle(it).fotoUri
            Glide.with(requireContext()).load(uri).into(binding.ivDog)
            Toast.makeText(requireContext(), "$uri", Toast.LENGTH_SHORT).show()
        }
    }
}