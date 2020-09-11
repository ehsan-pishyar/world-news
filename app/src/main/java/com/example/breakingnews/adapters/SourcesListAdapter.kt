package com.example.breakingnews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingnews.R
import com.example.breakingnews.model.Sources
import com.example.breakingnews.interfaces.SetOnSourcesClickListener
import kotlinx.android.synthetic.main.list_sources_row_card.view.*

class SourcesListAdapter(private val sources: List<Sources> = ArrayList(), private val listener: SetOnSourcesClickListener): RecyclerView.Adapter<SourcesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourcesListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_sources_row_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return sources.size
    }

    override fun onBindViewHolder(holder: SourcesListAdapter.ViewHolder, position: Int) {
        holder.bind(sources[position], listener)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val mTitle = itemView.text_title_sources

        fun bind(sources: Sources, action: SetOnSourcesClickListener){
            mTitle.text = sources.name

            mTitle.setOnClickListener {
                action.onSourcesTitleClick(sources, adapterPosition)
            }
        }
    }
}