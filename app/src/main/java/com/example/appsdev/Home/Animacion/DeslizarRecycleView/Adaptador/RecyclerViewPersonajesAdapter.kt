package com.example.appsdev.Home.Animacion.DeslizarRecycleView.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appsdev.Home.Animacion.DeslizarRecycleView.Entidad.PersonasDragonBall
import com.example.appsdev.R
import com.example.appsdev.databinding.ListaPersonajesBinding

class RecyclerViewPersonajesAdapter(
    val listPersonaje : MutableList<PersonasDragonBall>
) : RecyclerView.Adapter<RecyclerViewPersonajesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding : ListaPersonajesBinding = ListaPersonajesBinding.bind(view)
        val viewF = binding.rlLPDB
        val viewB = binding.viewBackgroundLPDB
        fun bind(persona : PersonasDragonBall){
            binding.apply {
                persona.apply {
                    tvDecripcionLPDB.text = descripcion
                    tvNombreLPDB.text =  nombres
                    imgPersonaLPDB.setImageResource(imagenid)
                }
            }
        }
    }

    fun removeItem(position: Int){
        listPersonaje.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: PersonasDragonBall, position: Int){
        listPersonaje.add(position, item)
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_personajes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPersonaje[position])
    }

    override fun getItemCount(): Int = listPersonaje.size
}