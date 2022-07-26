package com.example.appsdev.Home.Lottie

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentLottieBinding
import dagger.hilt.android.AndroidEntryPoint
/**
 * Ing. Joel Maldonado Fernandez
 * joelmaldonadodev@gmail.com
 * */
@AndroidEntryPoint
class Lottie : BaseFragment<FragmentLottieBinding>(FragmentLottieBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        var like = false
        binding.ivLottie.setOnClickListener {
            like = animation(binding.ivLottie,R.raw.check_list, like)
        }
    }

    private fun animation(iv: LottieAnimationView, animation: Int, like: Boolean): Boolean {
        if (!like){
            iv.setAnimation(animation)
            iv.repeatCount = 10
            iv.playAnimation()
        }else{
            iv.animate().alpha(0f).setDuration(200).setListener(object :AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    iv.setImageResource(R.drawable.img_progress)
                    iv.alpha = 1f
                }
            })
        }
        return !like
    }
}