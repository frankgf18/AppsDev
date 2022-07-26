package com.example.appsdev.Home.MPAndroidChart

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.View
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.databinding.FragmentMpAndroidChartBinding
import com.github.mikephil.charting.utils.ColorTemplate

class MPAndroidChart : BaseFragment<FragmentMpAndroidChartBinding>(FragmentMpAndroidChartBinding::inflate){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init()= with(binding){
        chart1.setUsePercentValues(true)
        chart1.description.isEnabled = false
        chart1.setExtraOffsets(5f,10f,5f,10f)

        chart1.dragDecelerationFrictionCoef = 0.95f
        //chart1.setCenterTextTypeface(CENTER)
        chart1.centerText = generateCenterSpannableText()
        chart1.isDrawHoleEnabled = true
        chart1.setHoleColor(Color.WHITE)
        chart1.setTransparentCircleColor(Color.WHITE)
        chart1.setTransparentCircleAlpha(110)
        chart1.holeRadius = 58f
        chart1.transparentCircleRadius = 61f
        chart1.setDrawCenterText(true)
        chart1.rotationAngle = 0f
        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        chart1.isRotationEnabled = true
        chart1.isHighlightPerTapEnabled = true
    }

    private fun generateCenterSpannableText(): SpannableString? {
        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }
}