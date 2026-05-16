package com.radio.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.radio.app.databinding.ItemStationBinding

class StationAdapter(
    private val onPlay: (RadioStation) -> Unit,
    private val onFavoriteToggle: (RadioStation) -> Unit,
    private val onStartDrag: (RecyclerView.ViewHolder) -> Unit
) : ListAdapter<RadioStation, StationAdapter.ViewHolder>(DiffCallback()) {

    private val favorites = mutableSetOf<String>()
    private val selectedIds = mutableSetOf<String>()
    var selectionMode = false
    var onSelectionChanged: ((Int) -> Unit)? = null
    var onSelectionModeEntered: (() -> Unit)? = null

    fun setFavorites(ids: Set<String>) {
        favorites.clear()
        favorites.addAll(ids)
        notifyItemRangeChanged(0, itemCount)
    }

    fun isSelected(id: String): Boolean = id in selectedIds

    fun toggleSelection(id: String) {
        if (id in selectedIds) selectedIds.remove(id) else selectedIds.add(id)
        onSelectionChanged?.invoke(selectedIds.size)
        notifyItemChanged(currentList.indexOfFirst { it.id == id })
    }

    fun selectAll() {
        selectedIds.addAll(currentList.map { it.id })
        onSelectionChanged?.invoke(selectedIds.size)
        notifyItemRangeChanged(0, itemCount)
    }

    fun clearSelection() {
        selectedIds.clear()
        onSelectionChanged?.invoke(0)
        notifyItemRangeChanged(0, itemCount)
    }

    fun getSelectedStations(): List<RadioStation> =
        currentList.filter { it.id in selectedIds }

    fun enterSelectionMode(id: String) {
        selectionMode = true
        selectedIds.clear()
        selectedIds.add(id)
        onSelectionChanged?.invoke(1)
        onSelectionModeEntered?.invoke()
        notifyItemRangeChanged(0, itemCount)
    }

    fun exitSelectionMode() {
        selectionMode = false
        selectedIds.clear()
        onSelectionChanged?.invoke(0)
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(station: RadioStation) {
            binding.tvName.text = station.name
            binding.tvCategory.text = station.category

            val isSelected = station.id in selectedIds
            binding.checkSelect.isChecked = isSelected
            binding.root.isActivated = isSelected

            if (selectionMode) {
                binding.checkSelect.visibility = android.view.View.VISIBLE
                binding.btnFavorite.visibility = android.view.View.GONE
                binding.dragHandle.visibility = android.view.View.VISIBLE

                binding.root.setOnClickListener { toggleSelection(station.id) }
                binding.dragHandle.setOnTouchListener { _, event ->
                    if (event.action == android.view.MotionEvent.ACTION_DOWN) {
                        onStartDrag(this)
                    }
                    false
                }
            } else {
                binding.checkSelect.visibility = android.view.View.GONE
                binding.btnFavorite.visibility = android.view.View.VISIBLE
                binding.dragHandle.visibility = android.view.View.GONE

                binding.btnFavorite.setImageResource(
                    if (station.id in favorites)
                        android.R.drawable.btn_star_big_on
                    else
                        android.R.drawable.btn_star_big_off
                )

                binding.root.setOnClickListener { onPlay(station) }
                binding.root.setOnLongClickListener {
                    enterSelectionMode(station.id)
                    true
                }
                binding.btnFavorite.setOnClickListener { onFavoriteToggle(station) }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<RadioStation>() {
        override fun areItemsTheSame(a: RadioStation, b: RadioStation) = a.id == b.id
        override fun areContentsTheSame(a: RadioStation, b: RadioStation) = a == b
    }

    companion object {
        fun createItemTouchHelper(
            onMove: (Int, Int) -> Unit
        ): ItemTouchHelper {
            val callback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val from = viewHolder.bindingAdapterPosition
                    val to = target.bindingAdapterPosition
                    if (from != RecyclerView.NO_POSITION && to != RecyclerView.NO_POSITION) {
                        onMove(from, to)
                    }
                    return true
                }

                override fun isLongPressDragEnabled() = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
            }
            return ItemTouchHelper(callback)
        }
    }
}
