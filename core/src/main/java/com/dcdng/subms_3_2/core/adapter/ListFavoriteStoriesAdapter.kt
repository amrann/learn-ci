package com.dcdng.subms_3_2.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dcdng.subms_3_2.core.databinding.ItemStoriesBinding
import com.dcdng.subms_3_2.core.domain.model.FavoriteStory

class ListFavoriteStoriesAdapter : ListAdapter<FavoriteStory, ListFavoriteStoriesAdapter.ListViewHolder>(
  DIFF_CALLBACK
) {

  var onItemClick: ((FavoriteStory) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ListViewHolder(
      ItemStoriesBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )

  override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    val data = getItem(position)
    if (data != null) {
      holder.bind(data)
    }
  }

  inner class ListViewHolder(private var binding: ItemStoriesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: FavoriteStory) {
      Glide.with(itemView.context)
        .load(data.photoUrl)
        .centerCrop()
        .into(binding.imgStory)
      binding.tvNama.text = data.name
      binding.tvDesc.text = data.description
    }

    init {
      itemView.setOnClickListener {
        onItemClick?.invoke(getItem(bindingAdapterPosition))
      }
    }
  }

  companion object {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteStory>() {
      override fun areItemsTheSame(oldItem: FavoriteStory, newItem: FavoriteStory): Boolean {
        return oldItem == newItem
      }
      override fun areContentsTheSame(oldItem: FavoriteStory, newItem: FavoriteStory): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }
}