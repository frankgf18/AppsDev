package com.example.appsdev.Home.Animacion.DeslizarRecycleView

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.appsdev.Core.BaseFragment
import com.example.appsdev.Home.Animacion.DeslizarRecycleView.Adaptador.RecyclerViewPersonajesAdapter
import com.example.appsdev.Home.Animacion.DeslizarRecycleView.CallBacks.MyItemTouchHelperCallback
import com.example.appsdev.Home.Animacion.DeslizarRecycleView.Entidad.PersonasDragonBall
import com.example.appsdev.Home.Animacion.DeslizarRecycleView.Interface.CallBackItemTouch
import com.example.appsdev.R
import com.example.appsdev.databinding.FragmentDeslizarRecycleViewBinding
import com.google.android.material.snackbar.Snackbar

class DeslizarRecycleView : BaseFragment<FragmentDeslizarRecycleViewBinding>(FragmentDeslizarRecycleViewBinding::inflate), CallBackItemTouch  {
    private var listaPersonajes = mutableListOf<PersonasDragonBall>()
    private lateinit var adapter : RecyclerViewPersonajesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.apply {
        }
        cargarLista()
        mostrarData()
    }

    private fun cargarLista(){
         listaPersonajes = mutableListOf(
            PersonasDragonBall("Gohan", "Descripcion general", R.drawable.uno_gohan),
            PersonasDragonBall("Goku", "Descripcion general", R.drawable.dos_goku),
            PersonasDragonBall("Goten", "Descripcion general", R.drawable.tres_goten),
            PersonasDragonBall("Krilin", "Descripcion general", R.drawable.cuatro_krilin),
            PersonasDragonBall("Picoro", "Descripcion general", R.drawable.cinco_picoro),
            PersonasDragonBall("Trunks", "Descripcion general", R.drawable.seis_trunks),
            PersonasDragonBall("Vegueta", "Descripcion general", R.drawable.siete_vegueta)
        )
    }

    private fun mostrarData(){
        adapter = RecyclerViewPersonajesAdapter(listaPersonajes)
        binding.rvDragonBall.adapter = adapter
        val callBack = MyItemTouchHelperCallback(this, requireActivity())
        val touchHelper = ItemTouchHelper(callBack)
        touchHelper.attachToRecyclerView(binding.rvDragonBall)
    }

    override fun itemTouchOnMode(oldPosition: Int, newPosition: Int) {
        listaPersonajes.add(newPosition, listaPersonajes.removeAt(oldPosition))
        adapter.notifyItemMoved(oldPosition, newPosition)
    }

    override fun onSwiped(viewHodel: RecyclerView.ViewHolder, position: Int) {
        val nombre = listaPersonajes[viewHodel.adapterPosition].nombres
        val deletedItem = listaPersonajes[viewHodel.adapterPosition]
        val deletedIndex = viewHodel.adapterPosition

        adapter.removeItem(viewHodel.adapterPosition)
        val snackBar = Snackbar.make(binding.layoutDragonBall, "$nombre => Eliminado", Snackbar.LENGTH_LONG)
        snackBar.setAction("Cancel", View.OnClickListener {
            adapter.restoreItem(deletedItem, deletedIndex)
        })

        snackBar.setActionTextColor(Color.GREEN)
        snackBar.show()
    }
}