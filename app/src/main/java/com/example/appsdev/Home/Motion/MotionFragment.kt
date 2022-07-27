package com.example.appsdev.Home.Motion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentMotionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MotionFragment : BaseFragment<FragmentMotionBinding>(FragmentMotionBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}