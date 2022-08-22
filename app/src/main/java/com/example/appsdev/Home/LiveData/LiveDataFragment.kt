package com.example.appsdev.Home.LiveData

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentLiveDataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LiveDataFragment : BaseFragment<FragmentLiveDataBinding>(FragmentLiveDataBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}