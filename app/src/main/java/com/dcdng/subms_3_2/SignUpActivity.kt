package com.dcdng.subms_3_2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dcdng.subms_3_2.databinding.ActivitySignUpBinding
import com.dcdng.subms_3_2.core.utils.CustomButton
import com.dcdng.subms_3_2.viewmodel.SignUpViewModel
import com.dcdng.subms_3_2.core.utils.Result
import com.jakewharton.rxbinding2.widget.RxTextView
import com.dcdng.subms_3_2.core.utils.TAG.EMAIL_IS_NOT_VALID
import com.dcdng.subms_3_2.core.utils.TAG.FIELD_REQUIRED
import com.dcdng.subms_3_2.core.utils.TAG.PASS_IS_NOT_VALID
import com.dcdng.subms_3_2.core.utils.TAG.PASS_IS_NOT_CONTAIN_UPPERCASE
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

  private val viewModel: SignUpViewModel by viewModels()

  private lateinit var binding: ActivitySignUpBinding
  private lateinit var daftarButton: CustomButton

  private var nameSubscription: Disposable? = null
  private var emailSubscription: Disposable? = null
  private var passSubscription: Disposable? = null

  private var isRequiredName: Boolean = false
  private var isRequiredEmail: Boolean = false
  private var isRequiredPass: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySignUpBinding.inflate(layoutInflater)
    setContentView(binding.root)

    daftarButton = binding.signupButton


    setButtonEnabled()

    val nameStream = RxTextView.textChanges(binding.nameEditText)
      .skipInitialValue()
      .map { nm ->
        nm.toString().isEmpty()
      }
    nameSubscription = nameStream.subscribe {
        showNameExistAlert(it)
        setButtonEnabled()
      }

    val emailStream = RxTextView.textChanges(binding.emailEditText)
      .skipInitialValue()
      .map { email ->
        !Patterns.EMAIL_ADDRESS.matcher(email).matches()
      }
    emailSubscription = emailStream.subscribe {
      showEmailExistAlert(it)
      setButtonEnabled()
    }

    val passStream = RxTextView.textChanges(binding.passwordEditText)
      .skipInitialValue()
      .map { pass ->
        validatePassword(pass.toString())
      }
    passSubscription = passStream.subscribe { msg ->
      showPasswordExistAlert(msg.toString())
      setButtonEnabled()
    }

    setupView()
    setupAction()
    playAnimation()
  }

  private fun setButtonEnabled() {
    daftarButton.isEnabled =  isRequiredName && isRequiredEmail && isRequiredPass
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
    binding.signupButton.setOnClickListener {
      val nm = binding.nameEditText.text.toString()
      val email = binding.emailEditText.text.toString()
      val pass = binding.passwordEditText.text.toString()

      viewModel.postDataRegis(nm, email, pass).observe(this) {
        when (it) {
          is Result.Loading -> {
            showLoading(true)
          }
          is Result.Error -> {
            showLoading(false)
            val msg = viewModel.messageResp().value
            Log.e("CEEKKK", "Result.Error : $it")
            AlertDialog.Builder(this).apply {
              setTitle("Gagal")
              setMessage(msg)
              create()
              show()
            }
          }
          is Result.Success<*> -> {
            showLoading(false)
            val msg = viewModel.messageResp().value
            AlertDialog.Builder(this).apply {
              setTitle("Berhasil")
              setMessage(msg)
              setPositiveButton("Lanjut") { _, _ ->
                finish()
              }
              create()
              show()
            }
          }
        }
      }

    }
  }

  private fun playAnimation() {
    val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
    val nameTextView =
      ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
    val nameEditTextLayout =
      ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
    val emailTextView =
      ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
    val emailEditTextLayout =
      ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
    val passwordTextView =
      ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
    val passwordEditTextLayout =
      ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
    val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)

    AnimatorSet().apply {
      playSequentially(
        title,
        nameTextView,
        nameEditTextLayout,
        emailTextView,
        emailEditTextLayout,
        passwordTextView,
        passwordEditTextLayout,
        signup
      )
      startDelay = 100
    }.start()
  }

  private fun showNameExistAlert(isEmpty: Boolean) {
    binding.nameEditText.error = if (isEmpty) FIELD_REQUIRED else null
    val result = binding.nameEditText.error
    isRequiredName = result == null
  }

  private fun showEmailExistAlert(isNotValid: Boolean) {
    binding.emailEditText.error = if (isNotValid) EMAIL_IS_NOT_VALID else null
    val result = binding.emailEditText.error
    isRequiredEmail = result == null
  }

  private fun showPasswordExistAlert(passTxt: String) {
    binding.passwordEditText.error = if (passTxt.isNotEmpty()) passTxt else null
    val result = binding.passwordEditText.error
    isRequiredPass = result == null
  }

  private fun validatePassword(password: String): String {
    return when {
      password.length < 8 -> PASS_IS_NOT_VALID
      !password.matches(".*[A-Z].*".toRegex()) -> PASS_IS_NOT_CONTAIN_UPPERCASE
      else -> ""
    }
  }

  private fun showLoading(isLoading: Boolean) {
    binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
  }

  override fun onDestroy() {
    super.onDestroy()
    nameSubscription?.dispose()
    emailSubscription?.dispose()
    passSubscription?.dispose()
  }
}