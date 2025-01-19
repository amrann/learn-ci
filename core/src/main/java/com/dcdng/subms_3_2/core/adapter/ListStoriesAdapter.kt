package com.dcdng.subms_3_2.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dcdng.subms_3_2.core.databinding.ItemStoriesBinding
import com.dcdng.subms_3_2.core.domain.model.StoryList

class ListStoriesAdapter(private val listener: OnItemClickListener) :
  PagingDataAdapter<StoryList, ListStoriesAdapter.MyViewHolder>(DIFF_CALLBACK) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MyViewHolder(binding)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val data = getItem(position)
    if (data != null) {
      holder.bind(data, listener)
    }
  }

  class MyViewHolder(private val binding: ItemStoriesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: StoryList, listener: OnItemClickListener) {
      Glide.with(itemView.context)
        .load(data.photoUrl)
        .centerCrop()
        .into(binding.imgStory)
      binding.tvNama.text = data.name
      binding.tvDesc.text = data.description

      itemView.setOnClickListener {
        val sharedElements = listOf(
          Pair(binding.imgStory as View, "gambar_detail"),
          Pair(binding.tvNama as View, "nama_detail"),
          Pair(binding.tvDesc as View, "desc_detail")
        )
        listener.onItemClicked(data, sharedElements)
      }
    }
  }

  interface OnItemClickListener {
    fun onItemClicked(data: StoryList, sharedElements: List<Pair<View, String>>)
  }

  companion object {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryList>() {
      override fun areItemsTheSame(oldItem: StoryList, newItem: StoryList): Boolean {
        return oldItem == newItem
      }
      override fun areContentsTheSame(oldItem: StoryList, newItem: StoryList): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }
}