package com.example.appsdev.Home.Animacion.DeslizarRecycleView.Interface

import androidx.recyclerview.widget.RecyclerView

interface CallBackItemTouch {
    fun itemTouchOnMode(oldPosition:Int, newPosition:Int)
    fun onSwiped(viewHodel : RecyclerView.ViewHolder, position:Int)
}