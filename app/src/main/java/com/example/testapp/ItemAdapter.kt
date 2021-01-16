package com.example.testapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ItemBinding

class ItemAdapter(private val listener : Listener): ListAdapter<DataItem, ItemsViewHolder>(DiffCallback) {

    interface Listener{
        fun onClick(pos: Int)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
class ItemsViewHolder(private val binding: ItemBinding, private val listener: ItemAdapter.Listener):
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.deleteBtn.setOnClickListener {
            listener.onClick(adapterPosition)
        }
    }

    fun bind(item: DataItem) {
        binding.txtNum.text = item.id.toString()
    }
}