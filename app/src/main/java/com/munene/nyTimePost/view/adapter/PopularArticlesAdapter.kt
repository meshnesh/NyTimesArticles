package com.munene.nyTimePost.view.adapter

import android.content.Context
import com.bumptech.glide.Glide
import com.munene.nyTimePost.R
import com.munene.nyTimePost.databinding.ItemPopularArticleBinding
import com.munene.nyTimePost.model.PopularArticle
import com.munene.nyTimePost.view.base.DataBoundAdapter
import com.munene.nyTimePost.view.base.DataBoundViewHolder
import com.munene.nyTimePost.view.base.RecyclerItemClickListener

class PopularArticlesAdapter(
    private val context: Context,
    private val recyclerItemClickListener: RecyclerItemClickListener
) : DataBoundAdapter<ItemPopularArticleBinding>(R.layout.item_popular_article) {

    private var mPopularArticle: ArrayList<PopularArticle> = ArrayList()
    private lateinit var binding: ItemPopularArticleBinding

    override fun getItemCount(): Int {
        return mPopularArticle.size
    }

    override fun bindItem(holder: DataBoundViewHolder<ItemPopularArticleBinding>?, position: Int, payloads: MutableList<Any>?) {
        val popularArticle = mPopularArticle[position]
        binding = holder?.binding!!
        binding.popularArticle = popularArticle

        if (popularArticle.media.isEmpty()) {
            Glide
                .with(context)
                .load(R.drawable.sound_cloud)
                .placeholder(R.drawable.sound_cloud)
                .into(binding.img)
        } else {

            Glide
                .with(context)
                .load(popularArticle.media[0].mediaMetadata[2].url)
                .placeholder(R.drawable.sound_cloud)
                .into(binding.img)
        }

        binding.cdPopularArticle.setOnClickListener {
            recyclerItemClickListener.onItemClicked(popularArticle)
        }
    }

    fun updatePopularArticles(popularArticles: ArrayList<PopularArticle>) {
        mPopularArticle = popularArticles
        notifyDataSetChanged()
    }
}