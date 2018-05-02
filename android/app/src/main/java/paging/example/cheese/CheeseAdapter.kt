package paging.example.cheese

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup

class CheeseAdapter(private val onClick: (Int) -> Unit) : PagedListAdapter<Cheese, CheeseViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Cheese>() {
            override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                oldItem.id == newItem.id
                    && oldItem.like == newItem.like
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder {
        return CheeseViewHolder(parent, onClick)
    }

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
}