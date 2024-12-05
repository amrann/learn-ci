package com.dcdng.subms_3_2.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dcdng.subms_3_2.databinding.ActivityFavoriteBinding
import com.dcdng.subms_3_2.DetailActivity
import com.dcdng.subms_3_2.core.di.FavoriteModuleDependencies
import com.dcdng.subms_3_2.core.ui.adapter.ListFavoriteStoriesAdapter
import com.dcdng.subms_3_2.core.utils.DataMapper
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {
  private lateinit var favBinding: ActivityFavoriteBinding
  private val adapterListFavorite = ListFavoriteStoriesAdapter()

  @Inject
  lateinit var favoriteViewModelFactory: FavoriteViewModelFactory

  private val favoriteStoryViewModel: FavoriteStoryViewModel by viewModels {
    favoriteViewModelFactory
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    DaggerFavoriteComponent.builder()
      .context(this)
      .appDependencies(
        EntryPointAccessors.fromApplication(
          applicationContext,
          FavoriteModuleDependencies::class.java
        )
      )
      .build()
      .inject(this)
    super.onCreate(savedInstanceState)
    favBinding = ActivityFavoriteBinding.inflate(layoutInflater)
    setContentView(favBinding.root)

    adapterListFavorite.onItemClick = { selectedData ->
      val intent = Intent(this, DetailActivity::class.java)
      val dataMap = selectedData.let { DataMapper.mapModelFavoriteStoryToResponse(it) }
      intent.putExtra("StoryListResponse", dataMap)
      startActivity(intent)
    }

    favoriteStoryViewModel.listFavoriteStories.observe(this) { result ->
      adapterListFavorite.submitList(result)
      favBinding.viewEmpty.root.visibility =
        if (result.isNotEmpty()) View.GONE else View.VISIBLE
    }

    with(favBinding.rvStory) {
      layoutManager = LinearLayoutManager(context)
      setHasFixedSize(true)
      adapter = adapterListFavorite
    }

  }
}