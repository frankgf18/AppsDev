package com.example.appsdev.Home.Puzzle

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.databinding.FragmentPuzzleBinding
import dagger.hilt.android.AndroidEntryPoint
/**
 * Ing. Joel Maldonado Fernandez
 * joelmaldonadodev@gmail.com
 * */
@AndroidEntryPoint
class PuzzleFragment : BaseFragment<FragmentPuzzleBinding>(FragmentPuzzleBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

    }

    private fun init() {
        binding.iv1Drag.setOnLongClickListener(longClick)
        binding.iv2Drag.setOnLongClickListener(longClick)
        binding.iv3Drag.setOnLongClickListener(longClick)

        binding.iv1Target.setOnDragListener(dragListener)
        binding.iv2Target.setOnDragListener(dragListener)
        binding.iv3Target.setOnDragListener(dragListener)

    }

    private val dragListener = View.OnDragListener { v, event ->

        val reciever = v as ImageView

        when(event.action){
            DragEvent.ACTION_DRAG_STARTED->{
                binding.tvTittle.text = "Empezaste a arrastrar imagen"
                true
            }
            DragEvent.ACTION_DRAG_ENTERED->{
                if (reciever.tag as String == event.clipDescription.label){
                    binding.tvTittle.text = "Imagen Correcta"
                    reciever.setColorFilter(Color.GREEN)
                }else{
                    binding.tvTittle.text = "ERROR"
                    reciever.setColorFilter(Color.RED)
                }
                true
            }
            DragEvent.ACTION_DRAG_EXITED->{
                reciever.setColorFilter(Color.GRAY)
                binding.tvTittle.text = "Saliste de la imagen"
                true
            }
            DragEvent.ACTION_DROP->{
                binding.tvTittle.text = "Soltaste la imagen"
                true
            }
            DragEvent.ACTION_DRAG_ENDED->{
                binding.tvTittle.text = "Dejaste de arrastrar la imagen"
                true
            }else->{
                false
            }
        }
    }

    private val longClick = View.OnLongClickListener {
        val item = ClipData.Item(it.tag as? CharSequence)

        val dragData = ClipData(
            it.tag as CharSequence,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item
        )

        val shadow = MyDrag(it)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            it.startDrag(dragData, shadow, null, 0)
        } else {
            it.startDrag(dragData, shadow, null, 0)
        }
        true
    }

    private class MyDrag(val v: View) : View.DragShadowBuilder(v) {
        override fun onProvideShadowMetrics(outShadowSize: Point, outShadowTouchPoint: Point) {
            outShadowSize.set(view.width, view.height)
            outShadowTouchPoint.set(view.width / 2, view.height / 2)
        }

        override fun onDrawShadow(canvas: Canvas?) {
            v.draw(canvas)
        }
    }
}