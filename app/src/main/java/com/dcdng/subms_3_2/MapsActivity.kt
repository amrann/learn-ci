package com.dcdng.subms_3_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dcdng.subms_3_2.core.utils.Result
import com.dcdng.subms_3_2.core.di.StoryModuleDependencies
import com.dcdng.subms_3_2.core.domain.model.StoryList

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.dcdng.subms_3_2.databinding.ActivityMapsBinding
import com.dcdng.subms_3_2.di.DaggerStoryComponent
import com.dcdng.subms_3_2.factory.StoryViewModelFactory
import com.dcdng.subms_3_2.viewmodel.MainViewModel
import com.dcdng.subms_3_2.viewmodel.StoryViewModel
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

  private val viewModel: MainViewModel by viewModels()

  @Inject
  lateinit var storyViewModelFactory: StoryViewModelFactory

  private var mMap: GoogleMap? = null
  private lateinit var binding: ActivityMapsBinding
  private val boundsBuilder = LatLngBounds.Builder()

  private var authToken: String = ""
  private val storyList = ArrayList<StoryList>()

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
    binding = ActivityMapsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel.getSession().observe(this) { user ->
      if (!user.isLogin) {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
      } else {
//        viewModel.getDataStories(user.token)
        authToken = user.token
        val storyViewModel = setupStoryViewModel(authToken)
        storyViewModel.getDataStories().observe(this) { result ->
          handleStoriesResult(result)
        }
      }
    }

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
  }

  override fun onMapReady(googleMap: GoogleMap) {
    mMap = googleMap.apply {
      uiSettings.isZoomControlsEnabled = true
      uiSettings.isIndoorLevelPickerEnabled = true
      uiSettings.isCompassEnabled = true
      uiSettings.isMapToolbarEnabled = true
    }
    addManyMarker()
  }


  private fun addManyMarker() {
    mMap?.let { map ->
      var hasPoints = false
      storyList.forEach { dt ->
        val latLng = LatLng(dt.lat, dt.lon)
        map.addMarker(
          MarkerOptions()
            .position(latLng)
            .title(dt.name)
        )
        boundsBuilder.include(latLng)
        hasPoints = true
      }
      if (hasPoints) {
        val bounds: LatLngBounds = boundsBuilder.build()
        map.animateCamera(
          CameraUpdateFactory.newLatLngBounds(
            bounds,
            resources.displayMetrics.widthPixels,
            resources.displayMetrics.heightPixels,
            300
          )
        )
      }
    }
  }

  private fun handleStoriesResult(result: Result<*>) {
    when (result) {
      is Result.Success<*> -> {
        val data = result.data
        if (data is List<*>) {
          val storyItems = data.filterIsInstance<StoryList>()
          storyList.clear()
          storyList.addAll(storyItems)
          mMap?.let { addManyMarker() }
        }
      }
      is Result.Error -> {
        Toast.makeText(
          this,
          "Terjadi kesalahan: ${result.error}",
          Toast.LENGTH_SHORT
        ).show()
      }
      Result.Loading -> {
        // Show progress bar if necessary
      }
    }
  }

  private fun setupStoryViewModel(token: String): StoryViewModel {
    return viewModels<StoryViewModel> {
      storyViewModelFactory
    }.value.apply {
      updateToken(token)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    mMap?.clear()
    mMap = null
    storyList.clear()
  }
}