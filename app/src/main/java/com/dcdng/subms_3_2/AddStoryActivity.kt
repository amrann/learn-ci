package com.dcdng.subms_3_2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.dcdng.subms_3_2.core.data.source.remote.response.StoryUploadResponse
import com.dcdng.subms_3_2.core.data.source.remote.network.ApiConfig
import com.dcdng.subms_3_2.databinding.ActivityAddStoryBinding
import com.dcdng.subms_3_2.core.utils.getImageUri
import com.dcdng.subms_3_2.core.utils.reduceFileImage
import com.dcdng.subms_3_2.core.utils.uriToFile
import com.dcdng.subms_3_2.viewmodel.MainViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

@AndroidEntryPoint
class AddStoryActivity : AppCompatActivity() {
  private var currentImageUri: Uri? = null
  private lateinit var binding: ActivityAddStoryBinding

  private val viewModel: MainViewModel by viewModels()

  private lateinit var token: String


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAddStoryBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel.getSession().observe(this) { user ->
      if (!user.isLogin) {
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
      } else {
        token = user.token
      }
    }

    binding.btnCamera.setOnClickListener { startCamera() }
    binding.btnGallery.setOnClickListener {
      startGallery()
    }
    binding.btnUpload.setOnClickListener { uploadImage(token) }

  }

  private fun startCamera() {
    val imageUri = getImageUri(this)
    currentImageUri = imageUri
    launcherIntentCamera.launch(imageUri)
  }

  private fun startGallery() {
    launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
  }

  private fun uploadImage(token: String) {
    showLoading(true)

    currentImageUri?.let { uri ->
      showLoading(true)
      val imageFile = uriToFile(uri, this).reduceFileImage()
      val description = binding.descUpload.text.toString()

      val requestBody = description.toRequestBody("text/plain".toMediaType())
      val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
      val multipartBody = MultipartBody.Part.createFormData(
        "photo",
        imageFile.name,
        requestImageFile
      )
      lifecycleScope.launch {
        try {
          val apiService = ApiConfig.getApiService(token)
          val successResponse = apiService.uploadImage(multipartBody, requestBody)
          showLoading(false)
          AlertDialog.Builder(this@AddStoryActivity).apply {
            setTitle("Yeah!")
            setMessage(successResponse.message)
            setPositiveButton("Selesai") { _, _ ->
              val intent = Intent(context, MainActivity::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
              startActivity(intent)
              finish()
            }
            create()
            show()
          }
        } catch (e: HttpException) {
          val errorBody = e.response()?.errorBody()?.string()
          val errorResponse = Gson().fromJson(errorBody, StoryUploadResponse::class.java)
          showToast(errorResponse.message)
          showLoading(false)
        }
      }

    } ?: showToast(getString(R.string.gambar_kosong))
    showLoading(false)
  }

  private val launcherGallery = registerForActivityResult(
    ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    if (uri != null) {
      currentImageUri = uri
      showImage()
    } else {
      Log.d("Photo Picker", "No media selected")
    }
  }

  private val launcherIntentCamera = registerForActivityResult(
    ActivityResultContracts.TakePicture()
  ) { isSuccess ->
    if (isSuccess) {
      showImage()
    } else {
      currentImageUri = null
    }
  }

  private fun showImage() {
    currentImageUri?.let {
      binding.imgUploadPreview.setImageURI(it)
    }
  }

  private fun showLoading(state: Boolean) {
    binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
  }

  private fun showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }
}