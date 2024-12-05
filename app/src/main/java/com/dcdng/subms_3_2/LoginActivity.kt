package com.dcdng.subms_3_2

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dcdng.subms_3_2.core.domain.model.UserModel
import com.dcdng.subms_3_2.databinding.ActivityLoginBinding
import com.dcdng.subms_3_2.core.utils.CustomButton
import com.dcdng.subms_3_2.core.utils.EmailEditText
import com.dcdng.subms_3_2.core.utils.PasswordEditText
import com.dcdng.subms_3_2.viewmodel.LoginViewModel
import com.dcdng.subms_3_2.core.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

  private lateinit var emailTxtCustom : EmailEditText
  private lateinit var passTxtCustom : PasswordEditText
  private lateinit var loginBtn : CustomButton
  private lateinit var binding: ActivityLoginBinding

  private var isRequiredEmail: Boolean = false
  private var isRequiredPass: Boolean = false

  private val viewModel: LoginViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)

    emailTxtCustom = binding.emailEditText
    passTxtCustom = binding.passwordEditText

    loginBtn = binding.loginButton

    setButtonEnabled()

    emailTxtCustom.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        isRequiredEmailText()
        setButtonEnabled()
      }
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    })

    passTxtCustom.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(s: Editable?) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        isRequiredPasswordText()
        setButtonEnabled()
      }
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    })

    setupView()
    setupAction()
    playAnimation()
  }


  private fun isRequiredEmailText() {
    val result = emailTxtCustom.error
    isRequiredEmail = result == null
  }

  private fun isRequiredPasswordText() {
    val result = passTxtCustom.error
    isRequiredPass = result == null
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
    binding.loginButton.setOnClickListener {
      val email = emailTxtCustom.text.toString()
      val pass = passTxtCustom.text.toString()

      viewModel.postDataLogin(email, pass).observe(this) {
        when (it) {
          is Result.Loading -> {
            showLoading(true)
          }
          is Result.Error -> {
            showLoading(false)
            val msg = viewModel.messageResp().value
            AlertDialog.Builder(this).apply {
              setTitle("Gagal")
              setMessage(msg)
              setNegativeButton("Tutup") { dialog, _ ->
                dialog.dismiss()
              }
              create()
              show()
            }
          }
          is Result.Success<*> -> {
            showLoading(false)
            // balikan it.data => LoginResultResponse(useId=false, name=Admin, token=axacwrfe)
            val token = viewModel.getToken().value
            val userModel = UserModel(token.toString(), true)
            viewModel.saveSession(userModel)
            val msg = viewModel.messageResp().value
            AlertDialog.Builder(this).apply {
              setTitle("Berhasil")
              setMessage(msg)
              setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
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
    val message =
      ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
    val emailTextView =
      ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
    val emailEditTextLayout =
      ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
    val passwordTextView =
      ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
    val passwordEditTextLayout =
      ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
    val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

    AnimatorSet().apply {
      playSequentially(
        title,
        message,
        emailTextView,
        emailEditTextLayout,
        passwordTextView,
        passwordEditTextLayout,
        login
      )
      startDelay = 100
    }.start()
  }

  private fun showLoading(isLoading: Boolean) {
    binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
  }

  private fun setButtonEnabled() {
    loginBtn.isEnabled = isRequiredEmail && isRequiredPass
  }
}