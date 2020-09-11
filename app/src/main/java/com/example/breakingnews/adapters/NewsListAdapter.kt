package com.example.breakingnews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.breakingnews.R
import com.example.breakingnews.model.Articles
import com.example.breakingnews.interfaces.SetOnNewsClickListener
import com.example.breakingnews.utils.NewsUtils
import kotlinx.android.synthetic.main.list_main_row_card.view.*

class NewsListAdapter(private val allArticles: List<Articles> = ArrayList(), private val newsClickListener: SetOnNewsClickListener): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_main_row_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allArticles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(allArticles[position], newsClickListener)
    }

    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val newsDateTime = NewsUtils()

        private val mSource = itemView.text_source_row
        private val mPublished = itemView.text_published_row
        private val mTitle = itemView.text_title_row
        private val mIcon = itemView.img_icon_row
        private val mCard = itemView.list_card

        fun bind(articles: Articles, action: SetOnNewsClickListener) {

            val date = newsDateTime.setDate(articles.publishedAt)
            val time = newsDateTime.setTime(articles.publishedAt)

            mSource.text = articles.source.name
            mPublished.text = "$date:$time"
            mTitle.text = articles.title
            Glide.with(itemView).load(articles.urlToImage).placeholder(R.mipmap.w).into(mIcon)

            mCard.setOnClickListener {
                action.onNewsClick(articles, adapterPosition)
            }
        }
    }
}