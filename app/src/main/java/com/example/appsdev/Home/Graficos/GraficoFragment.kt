package com.example.appsdev.Home.Graficos

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.databinding.FragmentGraficoBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GraficoFragment : BaseFragment<FragmentGraficoBinding>(FragmentGraficoBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        pie()
        load()
    }

    private fun pie() = with(binding.chart) {
        setDrawSlicesUnderHole(true)
        setUsePercentValues(true)
        setEntryLabelTextSize(12f)
        setEntryLabelColor(Color.BLACK)
        centerText = "Categoriaaas"
        contentDescription = "FRANK"
        setCenterTextSize(24f)

        val l = binding.chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    private fun load() {
        val entries = listOf(
            PieEntry(0.2f, "Azul"),
            PieEntry(0.5f, "Rojo"),
            PieEntry(0.5f, "Amarillo"),
            PieEntry(0.3f, "Verde")
        )

        val dataSet = PieDataSet(entries, "Categorias")

        val d = PieData(dataSet)

        d.setDrawValues(true)
        d.setValueFormatter(PercentFormatter(binding.chart))
        d.setValueTextSize(12f)
        d.setValueTextColor(Color.BLACK)

        binding.chart.data = d
    }
}