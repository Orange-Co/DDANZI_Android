package co.orange.core.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat

fun Activity.setStatusBarColorFromResource(colorResId: Int) {
    val statusBarColor = ContextCompat.getColor(this, colorResId)
    window.statusBarColor = statusBarColor
}

fun Activity.setNavigationBarColorFromResource(colorResId: Int) {
    val navigationBarColor = ContextCompat.getColor(this, colorResId)
    window.navigationBarColor = navigationBarColor
}

fun ComponentActivity.initOnBackPressedListener(
    view: View,
    delay: Long = 2000L,
) {
    var backPressedTime = 0L
    val onBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime >= delay) {
                    backPressedTime = System.currentTimeMillis()
                    view.context.toast("버튼을 한번 더 누르면 종료됩니다.")
                } else {
                    finishAffinity()
                }
            }
        }

    this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
}

fun Activity.initFocusWithKeyboard(editText: EditText) {
    editText.requestFocus()
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
        editText,
        InputMethodManager.SHOW_IMPLICIT,
    )
}
