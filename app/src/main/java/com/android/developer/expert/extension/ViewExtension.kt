package com.android.developer.expert.extension

import android.app.Activity
import android.content.ContextWrapper
import android.view.View
import android.view.ViewAnimationUtils
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.util.Pair
import androidx.core.view.forEach
import androidx.core.view.isVisible
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.hypot

fun View.setVisibleWithCircularReveal(isVisible: Boolean) {
    post {
        try {
            val cx = (width / 2)
            val cy = (height / 2)

            val radius = hypot(cx.toFloat(), cy.toFloat())
            val startRadius = if (isVisible) 0F else radius
            val endRadius = if (isVisible) radius else 0F
            val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, startRadius, endRadius)
            if (isVisible) {
                this.isVisible = isVisible
            } else {
                anim.doOnEnd { this.isVisible = isVisible }
            }
            anim.start()
        } catch (e: Throwable) {
            if (isVisible) this.isVisible = isVisible
        }
    }
}

fun BottomNavigationView.disableMenuChecked() {
    menu.forEach { menu ->
        menu.isEnabled = !menu.isChecked
    }
}

fun BottomNavigationView.show(isShow: Boolean) {
    val layoutParam = layoutParams as CoordinatorLayout.LayoutParams
    val behavior = layoutParam.behavior
    if (behavior is HideBottomViewOnScrollBehavior) behavior.slideUp(this)
    layoutParam.behavior =
        if (isShow) {
            behavior ?: HideBottomViewOnScrollBehavior<BottomNavigationView>()
        } else {
            null
        }
}

fun View.startPostponedEnterTransition(isStart: Boolean) {
    if (isStart) {
        viewTreeObserver.addOnPreDrawListener {
            viewTreeObserver.removeOnPreDrawListener { true }
            ((context as ContextWrapper).baseContext as Activity).startPostponedEnterTransition()
            return@addOnPreDrawListener true
        }
    }
}

fun View.removeTransitionName() {
    transitionName = null
}

fun View.createPair(transitionName: String): Pair<View, String> {
    this.transitionName = transitionName
    return Pair.create(this, this.transitionName)
}