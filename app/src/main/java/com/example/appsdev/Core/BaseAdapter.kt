package com.example.appsdev.Core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(var data:List<T>): RecyclerView.Adapter<BaseAdapter.BaseAdapterViewHolder<T>>() {

    abstract fun getViewHolder(parent: ViewGroup): BaseAdapterViewHolder<T>

    abstract class BaseAdapterViewHolder<T>(view: View): RecyclerView.ViewHolder(view){
        abstract fun bind(entity:T)
    }

    fun update(item:List<T>){
        data = item
        notifyDataSetChanged()
    }

    fun reset(item: List<T>){
        data = emptyList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapterViewHolder<T> {
        return getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BaseAdapterViewHolder<T>, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}