package com.example.appsdev.Home.Animacion.CardDesplegable

import android.animation.LayoutTransition
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentCardDesplegableBinding


class CardDesplegable : BaseFragment<FragmentCardDesplegableBinding>(FragmentCardDesplegableBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding){
        contenedorData.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        btnAparecer.setOnClickListener {
            TransitionManager.beginDelayedTransition(contenedorData, AutoTransition())
            tvDetalles.isGone = !tvDetalles.isGone
            btnAparecer.text =  if (!tvDetalles.isGone) getString(R.string.tvDesaparecer)
            else getString(R.string.tvAparecer)
        }
    }
}