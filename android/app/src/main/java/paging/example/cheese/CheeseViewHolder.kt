package paging.example.cheese

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class CheeseViewHolder(parent: ViewGroup, private val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.cheese, parent, false)) {

    private val titleView = itemView.findViewById<TextView>(R.id.title)
    private val descriptionView = itemView.findViewById<TextView>(R.id.description)
    private val extrasView = itemView.findViewById<TextView>(R.id.extras)

    fun bindTo(cheese : Cheese?) {
        titleView.text = cheese?.name ?: "Loading..."
        descriptionView.text = cheese?.description ?: ""
        extrasView.text = "Id ${cheese?.id ?: ""} · Like ${cheese?.like ?: ""}"
        itemView.setOnClickListener {
            cheese?.let {
                onClick(cheese.id)
            }
        }
    }
}