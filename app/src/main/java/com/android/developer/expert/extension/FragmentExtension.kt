package com.android.developer.expert.extension

import androidx.fragment.app.Fragment
import com.android.developer.expert.base.BaseActivity

inline fun <reified T : BaseActivity<*>> Fragment.factory() = run {
    val activity = (requireActivity() as T)
    val isInject = activity.isInject()
    if (!isInject) {
        val message = "${T::class.java.name} not inject || isInject() = $isInject"
        throw IllegalArgumentException(message)
    }
    return@run activity.viewModelFactory
}