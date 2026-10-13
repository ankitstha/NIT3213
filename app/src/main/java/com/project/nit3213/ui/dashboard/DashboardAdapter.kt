package com.project.nit3213.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.nit3213.data.model.EntityItem
import com.project.nit3213.databinding.ItemEntityBinding

/**
 * DashboardAdapter efficiently displays a list of entities using RecyclerView, ListAdapter, and DiffUtil (L6).
 */
class DashboardAdapter(
    private val onItemClick: (EntityItem) -> Unit
) : ListAdapter<EntityItem, DashboardAdapter.EntityViewHolder>(EntityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val binding = ItemEntityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EntityViewHolder(private val binding: ItemEntityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EntityItem) {
            binding.tvProperty1.text = item.property1 ?: "N/A"
            binding.tvProperty2.text = item.property2 ?: "N/A"
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class EntityDiffCallback : DiffUtil.ItemCallback<EntityItem>() {
        override fun areItemsTheSame(oldItem: EntityItem, newItem: EntityItem): Boolean {
            return oldItem.property1 == newItem.property1 && oldItem.property2 == newItem.property2
        }

        override fun areContentsTheSame(oldItem: EntityItem, newItem: EntityItem): Boolean {
            return oldItem == newItem
        }
    }
}
