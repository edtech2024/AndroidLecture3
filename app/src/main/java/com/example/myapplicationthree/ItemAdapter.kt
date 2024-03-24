package com.example.myapplicationthree

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter(
    var itemsList: MutableList<Item>,
    private var onItemClick: (Item) -> Unit,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvItemId: TextView = itemView.findViewById(R.id.tvItemId)
        val tvItemTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
        val tvItemDescription: TextView = itemView.findViewById(R.id.tvItemDescription)
        val tvItemPriority: TextView = itemView.findViewById(R.id.tvItemPriority)
        val tvItemType: TextView = itemView.findViewById(R.id.tvItemType)
        val tvItemCount: TextView = itemView.findViewById(R.id.tvItemCount)
        val tvItemPeriod: TextView = itemView.findViewById(R.id.tvItemPeriod)

        fun bind(item: Item) {
            tvItemId.text = item.id.toString()
            tvItemTitle.text = item.title.toString()
            tvItemDescription.text = item.description.toString()
            tvItemPriority.text = item.priority.toString()
            tvItemType.text = item.type.toString()
            tvItemCount.text = item.count.toString()
            tvItemPeriod.text = item.period.toString()

            itemView.setOnClickListener {
                onItemClick(item)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val itemView = inflater.inflate(R.layout.item_list, parent, false)
        // Return a new holder instance
        return ItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(itemsList[position])
    }

    fun addItem(item: Item) {
        this.itemsList.add(item)
        this.notifyItemInserted(this.getItemCount() - 1)
    }

    fun updateItem(item: Item) {
        this.itemsList[item.id!!.toString().toInt()] = item
        this.notifyItemChanged(item.id!!.toString().toInt())
    }

}
