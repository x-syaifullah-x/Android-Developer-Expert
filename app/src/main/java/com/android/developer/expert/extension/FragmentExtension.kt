package com.android.developer.expert.extension

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.android.developer.expert.base.BaseActivity

inline fun <reified T : Activity> Fragment.openActivity(
    optionToBundle: ActivityOptionsCompat? = null,
    block: Intent.() -> Unit = {}
) {
    val intent = Intent(requireActivity(), T::class.java)
    block(intent)
    startActivity(intent, optionToBundle?.toBundle())
}

inline fun <reified T : Activity> Fragment.openActivityAndFinis(block: Intent.() -> Unit = {}) {
    val intent = Intent(requireActivity(), T::class.java)
    block(intent)
    startActivity(intent)
    requireActivity().finish()
}

inline fun <reified T : BaseActivity<*>> Fragment.factory() = run {
    val activity = (requireActivity() as T)
    val isInject = activity.isInject()
    if (!isInject) {
        val message = "${T::class.java.name} not inject || isInject() = $isInject"
        throw IllegalArgumentException(message)
    }
    return@run activity.viewModelFactory
}