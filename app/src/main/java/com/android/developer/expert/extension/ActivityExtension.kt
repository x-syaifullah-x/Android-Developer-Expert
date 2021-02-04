package com.android.developer.expert.extension

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.*
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.viewbinding.ViewBinding

inline fun <reified T : Activity> Activity.openActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.openActivityAndFinish(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
    finish()
}

fun AppCompatActivity.disableDisplayShowAndHomeAsUp(des: NavDestination, @IdRes vararg id: Int) {
    if (id.contains(des.id)) {
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}

fun ActionBar?.show(isShow: Boolean) = this?.apply { if (isShow) show() else hide() }

fun ActionBar?.setHomeButton(
    isHomeButtonEnabled: Boolean = false,
    isDisplayHomeAsUpEnabled: Boolean = false
) = this?.apply {
    setHomeButtonEnabled(isHomeButtonEnabled)
    setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled)
}

@Suppress("DEPRECATION")
fun Activity.hideSystemUI() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.let {
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            it.hide(WindowInsets.Type.systemBars())
        }
    } else {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }
}