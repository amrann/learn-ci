package com.dcdng.subms_3_2

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dcdng.subms_3_2.core.data.source.remote.response.StoryListResponse
import com.dcdng.subms_3_2.core.di.StoryModuleDependencies
import com.dcdng.subms_3_2.core.ui.adapter.ListStoriesAdapter
import com.dcdng.subms_3_2.core.ui.adapter.LoadingStateAdapter
import com.dcdng.subms_3_2.core.utils.DataMapper
import com.dcdng.subms_3_2.databinding.ActivityMainBinding
import com.dcdng.subms_3_2.di.DaggerStoryComponent
import com.dcdng.subms_3_2.factory.StoryViewModelFactory
import com.dcdng.subms_3_2.viewmodel.MainViewModel
import com.dcdng.subms_3_2.viewmodel.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ListStoriesAdapter.OnItemClickListener {

  companion object {
    private const val TAG = "MainActivity"
  }

  private val viewModel: MainViewModel by viewModels()

  @Inject
  lateinit var storyViewModelFactory: StoryViewModelFactory

  private lateinit var binding: ActivityMainBinding

  private var authToken: String = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    DaggerStoryComponent.builder()
      .context(this)
      .appDependencies(
        EntryPointAccessors.fromApplication(
          applicationContext,
          StoryModuleDependencies::class.java
        )
      )
      .build()
      .inject(this)
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.rvStory.setHasFixedSize(true)

    viewModel.let { vm ->
      vm.getSession().observe(this) { user ->
        if (!user.isLogin) {
          startActivity(Intent(this, WelcomeActivity::class.java))
          finish()
        } else {
          authToken = user.token
          Log.e(TAG, "authToken 1 => $authToken")
          showRecyclerList(authToken)
        }
      }
    }

    setupView()
    setupAction()
  }

//  private fun setupStoryViewModel(token: String): StoryViewModel {
//    val vm: StoryViewModel by viewModels {
//      StoryViewModelFactory.getInstance(token)
//    }
//    return vm
//  }

    private fun setupStoryViewModel(token: String): StoryViewModel {
      val vm: StoryViewModel by viewModels {
        storyViewModelFactory
      }
      vm.updateToken(token)
      return vm
    }

  private fun setupView() {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      window.insetsController?.hide(WindowInsets.Type.statusBars())
    } else {
      window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
      )
    }
    supportActionBar?.hide()
  }

  private fun setupAction() {
    binding.topAppBar.setOnMenuItemClickListener { menuItem ->
      when (menuItem.itemId) {
        R.id.logoutButton -> {
          Log.e(TAG, "R.id.logoutButton ")
          viewModel.logout()
          true
        }
        R.id.mnMap -> {
          val intent = Intent(this, MapsActivity::class.java)
          startActivity(intent)
          true
        }
        R.id.mnFav -> {
          val uri = Uri.parse("subms_3_1://favorite")
          startActivity(Intent(Intent.ACTION_VIEW, uri))
          true
        }
        else -> false
      }
    }

    binding.fabAdd.setOnClickListener {
      val intent = Intent(this, AddStoryActivity::class.java)
      startActivity(intent)
    }
  }

//  private fun showLoading(state: Boolean) {
//    binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
//  }
//

  private fun showRecyclerList(token: String) {
    if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
      binding.rvStory.layoutManager = GridLayoutManager(this, 2)
    } else {
      binding.rvStory.layoutManager = LinearLayoutManager(this)
    }
    val storyViewModel = setupStoryViewModel(token)
    Log.e(TAG, "storyViewModel => $storyViewModel")

    // dengan paging using coroutines
    val adapter = ListStoriesAdapter(this)
    // Set up adapter with footer first
    binding.rvStory.adapter = adapter.withLoadStateFooter(
      footer = LoadingStateAdapter {
        adapter.retry()
      }
    )
    // Collect flow
    lifecycleScope.launch {
      lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        storyViewModel.dataPagingStories.collect { pagingData ->
          val dataMap = pagingData.let { DataMapper.mapModelPagingStoryToResponse(it) }
          adapter.submitData(dataMap)
        }
      }
    }

//    // dengan paging
//    val adapter = ListStoriesAdapter(this)
//    storyViewModel.dataPagingStories.observe(this) {
//      binding.rvStory.adapter = adapter.withLoadStateFooter(footer = LoadingStateAdapter {
//        adapter.retry()
//      })
//      adapter.submitData(lifecycle, it)
//    }
  }

  override fun onItemClicked(data: StoryListResponse, sharedElements: List<Pair<View, String>>) {
    val intent = Intent(this, DetailActivity::class.java).apply {
      putExtra("StoryListResponse", data)
    }

    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
      this,
      *sharedElements.toTypedArray()
    )
    startActivity(intent, optionsCompat.toBundle())
  }
}