package com.example.asm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.asm.databinding.ItemStepBinding
import com.example.asm.model.KeyValue

class KeyValueAdapter(private val items: MutableList<KeyValue>) :
    RecyclerView.Adapter<KeyValueAdapter.ViewHolder>() {
    private var isOff = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStepBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    fun setOff(off: Boolean) {
        isOff = off
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun clickItem(i: KeyValue) {
        items.add(i)
        notifyDataSetChanged()
    }

    fun insertNull() {
        items.clear()
        notifyDataSetChanged()
    }

    fun getListItem(): List<KeyValue> {
        return items
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KeyValue) {
            binding.item = item
            binding.close.setOnClickListener {
                items.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
            binding.isOff = this@KeyValueAdapter.isOff
            binding.executePendingBindings()
        }
    }
}
