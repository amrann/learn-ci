package com.dcdng.subms_3_2

import android.annotation.SuppressLint
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

  private lateinit var mMap: GoogleMap
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
          if (result != null) {
            when (result) {
              is Result.Success<*> -> {
//                binding?.progressBar?.visibility = View.GONE
//                storyList.addAll(result.data as Collection<StoryList>)
//                addManyMarker()
                val data = result.data
                if (data is List<*>) {
                  val storyItems = data.filterIsInstance<StoryList>()
                  storyList.addAll(storyItems)
                  addManyMarker()
                }
              }
              is Result.Error -> {
//                binding?.progressBar?.visibility = View.GONE
                Toast.makeText(
                  this,
                  "Terjadi kesalahan" + result.error,
                  Toast.LENGTH_SHORT
                ).show()
              }
              Result.Loading -> {
//                binding?.progressBar?.visibility = View.VISIBLE
              }
            }
          }
        }

      }
    }

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
  }
  override fun onMapReady(googleMap: GoogleMap) {
    mMap = googleMap
    mMap.uiSettings.isZoomControlsEnabled = true
    mMap.uiSettings.isIndoorLevelPickerEnabled = true
    mMap.uiSettings.isCompassEnabled = true
    mMap.uiSettings.isMapToolbarEnabled = true

//    addManyMarker()
  }

  @SuppressLint("SuspiciousIndentation")
  private fun addManyMarker() {
    storyList.forEach { dt ->
      val latLng = LatLng(dt.lat, dt.lon)
      mMap.addMarker(
        MarkerOptions()
          .position(latLng)
          .title(dt.name)
      )
      boundsBuilder.include(latLng)
    }
    val bounds: LatLngBounds = boundsBuilder.build()
      mMap.animateCamera(
        CameraUpdateFactory.newLatLngBounds(
          bounds,
          resources.displayMetrics.widthPixels,
          resources.displayMetrics.heightPixels,
          300
        )
      )
  }

  private fun setupStoryViewModel(token: String): StoryViewModel {
    val vm: StoryViewModel by viewModels {
      storyViewModelFactory
    }
    vm.updateToken(token)
    return vm
  }

//  private fun setupStoryViewModel(token: String): StoryViewModel {
//    Log.e(TAG, "token dalam setupStoryViewModel : $token")
//    val vm: StoryViewModel by viewModels {
//      StoryViewModelFactory.getInstance(token)
//    }
//    return vm
//  }
}