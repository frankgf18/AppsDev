package com.example.appsdev.Home.Animacion.DeslizarRecycleView.CallBacks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.appsdev.Home.Animacion.DeslizarRecycleView.Adaptador.RecyclerViewPersonajesAdapter
import com.example.appsdev.Home.Animacion.DeslizarRecycleView.Interface.CallBackItemTouch
import com.example.appsdev.R

class MyItemTouchHelperCallback(
    val callback: CallBackItemTouch,
    val context: Context
) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END

        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
    ): Boolean {
        callback.itemTouchOnMode(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        callback.onSwiped(viewHolder, viewHolder.adapterPosition)
    }

    //Sobre escribimos estas 2 funciones
    override fun isLongPressDragEnabled(): Boolean = true
    override fun isItemViewSwipeEnabled(): Boolean = true

    //Se implementará 5 metodos mas
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }else{
            val viewListaPersonajes = LayoutInflater.from(context).inflate(R.layout.lista_personajes, null)
            val forengroundView : View = RecyclerViewPersonajesAdapter.ViewHolder(viewListaPersonajes).viewF
            getDefaultUIUtil().onDrawOver(
                c, recyclerView, forengroundView, dX, dY, actionState, isCurrentlyActive
            )
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if(actionState != ItemTouchHelper.ACTION_STATE_DRAG){
            val viewListaPersonajes = LayoutInflater.from(context).inflate(R.layout.lista_personajes, null)
            val forengroundView : View = RecyclerViewPersonajesAdapter.ViewHolder(viewListaPersonajes).viewF
            getDefaultUIUtil().onDraw(
                c, recyclerView, forengroundView, dX, dY, actionState, isCurrentlyActive
            )
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val viewListaPersonajes = LayoutInflater.from(context).inflate(R.layout.lista_personajes, null)
        val forengroundView : View = RecyclerViewPersonajesAdapter.ViewHolder(viewListaPersonajes).viewF
        forengroundView.setBackgroundColor(ContextCompat.getColor(
                RecyclerViewPersonajesAdapter.ViewHolder(viewListaPersonajes).viewF.context,
                R.color.white
            )
        )
        getDefaultUIUtil().clearView(forengroundView)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (viewHolder!=null){
            val viewListaPersonajes = LayoutInflater.from(context).inflate(R.layout.lista_personajes, null)
            val forengroundView : View = RecyclerViewPersonajesAdapter.ViewHolder(viewListaPersonajes).viewF
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG){
                forengroundView.setBackgroundColor(Color.LTGRAY)
            }
            getDefaultUIUtil().onSelected(forengroundView)
        }
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }
}