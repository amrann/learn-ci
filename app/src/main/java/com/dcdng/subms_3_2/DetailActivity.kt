package com.dcdng.subms_3_2

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dcdng.subms_3_2.core.data.source.remote.response.StoryListResponse
import com.dcdng.subms_3_2.databinding.ActivityDetailBinding
import com.dcdng.subms_3_2.viewmodel.DetailViewModel
import com.dcdng.subms_3_2.viewmodel.MainViewModel
import com.dcdng.subms_3_2.core.utils.DataMapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

  private lateinit var bindingDetail: ActivityDetailBinding
  private var isIdExists: Boolean = false

  private val viewModel: MainViewModel by viewModels()

  private val detailViewModel: DetailViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    bindingDetail = ActivityDetailBinding.inflate(layoutInflater)
    setContentView(bindingDetail.root)

    setStatusFavorite(isIdExists)
    setupData()

    viewModel.let { vm ->
      vm.getSession().observe(this) { user ->
        if (!user.isLogin) {
          startActivity(Intent(this, WelcomeActivity::class.java))
          finish()
        }
      }
    }

  }

  private fun setStatusFavorite(statusFavorite: Boolean) {
    if (statusFavorite) {
      bindingDetail.btnFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_true))
    } else {
      bindingDetail.btnFav.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_false))
    }
  }

  private fun setupData() {
    @Suppress("DEPRECATION")
    val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      intent.getParcelableExtra("StoryListResponse", StoryListResponse::class.java)
    } else {
      intent.getParcelableExtra<StoryListResponse>("StoryListResponse") as StoryListResponse
    }

    val dataMap = data?.let { DataMapper.mapResponseStoryListToModel(it) }

    detailViewModel.listFavoriteStories.observe(this) { dataList ->
      isIdExists = dataList.any { it.id == dataMap?.id }
      setStatusFavorite(isIdExists)
    }

    with(bindingDetail) {
      Glide.with(applicationContext)
        .load(data?.photoUrl)
        .centerCrop()
        .into(imgStoryDetail)

      tvNamaDetail.text = data?.name ?: ""
      tvDescDetail.text = data?.description ?: ""

      btnFav.setOnClickListener {
        isIdExists = !isIdExists
        setStatusFavorite(isIdExists)
        if (isIdExists) {
          dataMap?.let {
              dt -> detailViewModel.insertFavoriteStory(dt)
            Toast.makeText(this@DetailActivity, "Data favorite ditambahkan", Toast.LENGTH_SHORT).show()
          }
        } else {
          dataMap?.let {
              dt -> detailViewModel.deleteFavoriteStory(dt)
            Toast.makeText(this@DetailActivity, "Data favorite dihapus", Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
  }

}