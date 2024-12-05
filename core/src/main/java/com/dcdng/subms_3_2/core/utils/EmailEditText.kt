package com.dcdng.subms_3_2.core.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.dcdng.subms_3_2.core.utils.TAG.EMAIL_IS_NOT_VALID
import com.dcdng.subms_3_2.core.utils.TAG.FIELD_REQUIRED

class EmailEditText : AppCompatEditText {
  constructor(context: Context) : super(context) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init()
  }

  private fun init() {
    addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

      override fun onTextChanged(s: CharSequence, start: Int, before: Int, after: Int) {
        error = if (s.toString().isEmpty()) {
          FIELD_REQUIRED
        } else if (!isValidEmail(s.toString())) {
          EMAIL_IS_NOT_VALID
        } else null
      }

      override fun afterTextChanged(s: Editable) {
      }

    })
  }

  private fun isValidEmail(mail: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()
  }
}