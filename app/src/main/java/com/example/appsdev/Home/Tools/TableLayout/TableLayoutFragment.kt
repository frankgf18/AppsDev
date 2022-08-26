package com.example.appsdev.Home.Tools.TableLayout

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentTableLayoutBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TableLayoutFragment : BaseFragment<FragmentTableLayoutBinding>(FragmentTableLayoutBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tab()
        btnChip()
    }

    private fun btnChip() {

        binding.btnChip.setOnClickListener {
            val chip = Chip(requireContext())
            chip.text = binding.edtChip.text.toString()
            binding.chipGroup.addView(chip)
        }

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val boton = binding.chipGroup.findViewById<Chip>(checkedIds[group.id])
            show(boton.text.toString())
        }
        binding.btnTextView.setOnClickListener {
            show("Hola")
        }
    }

    private fun tab() {
        val tabRow0 = TableRow(requireContext())

        val tv0 = TextView(requireContext())
        tv0.text = "Mes"
        tv0.setTextColor(Color.BLACK)
        tabRow0.addView(tv0)

        val tv1 = TextView(requireContext())
        tv1.text = "Cantidad de Ventas"
        tv1.setTextColor(Color.BLACK)
        tabRow0.addView(tv1)


        val tv2 = TextView(requireContext())
        tv2.text = "Cantidad ropa negra"
        tv2.setTextColor(Color.BLACK)
        tabRow0.addView(tv2)


        val tv3 = TextView(requireContext())
        tv3.text = "Cantidad ropa blanca"
        tv3.setTextColor(Color.BLACK)
        tabRow0.addView(tv3)

        binding.tableLayout.addView(tabRow0)

        for (i in 1..15){
            val tblRow = TableRow(requireContext())
            val tv0 = TextView(requireContext())
            tv0.text = "Mes $i"
            tv0.gravity = Gravity.CENTER
            tblRow.addView(tv0)

            val tv1 = TextView(requireContext())
            tv1.text = "${i*10}"
            tv1.gravity = Gravity.CENTER
            tblRow.addView(tv1)

            val tv2 = TextView(requireContext())
            tv2.text = "${i*15}"
            tv2.gravity = Gravity.CENTER
            tblRow.addView(tv2)

            val tv3 = TextView(requireContext())
            tv3.text = "${i*12}"
            tv3.gravity = Gravity.CENTER
            tblRow.addView(tv3)

            binding.tableLayout.addView(tblRow)
        }
    }
}