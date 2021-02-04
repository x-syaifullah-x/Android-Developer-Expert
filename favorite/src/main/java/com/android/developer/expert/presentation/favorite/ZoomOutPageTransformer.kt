package com.android.developer.expert.presentation.favorite

import android.graphics.Camera
import android.graphics.Matrix
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class ZoomOutPageTransformer : ViewPager.PageTransformer {
    companion object {
        private val MATRIX_OFFSET: Matrix = Matrix()
        private val CAMERA_OFFSET: Camera = Camera()
        private val TEMP_FLOAT_OFFSET = FloatArray(2)
    }

    override fun transformPage(view: View, position: Float) {
        val rotation: Float = (if (position < 0) 30f else -30f) * abs(position)

        view.translationX = getOffsetX(rotation, view.width, view.height)
        view.pivotX = view.width * 0.5f
        view.pivotY = 0f
        view.rotationY = rotation
    }

    private fun getOffsetX(rotation: Float, width: Int, height: Int): Float {
        MATRIX_OFFSET.reset()
        CAMERA_OFFSET.save()
        CAMERA_OFFSET.rotateY(abs(rotation))
        CAMERA_OFFSET.getMatrix(MATRIX_OFFSET)
        CAMERA_OFFSET.restore()
        MATRIX_OFFSET.preTranslate(-width * 0.5f, -height * 0.5f)
        MATRIX_OFFSET.postTranslate(width * 0.5f, height * 0.5f)
        TEMP_FLOAT_OFFSET[0] = width.toFloat()
        TEMP_FLOAT_OFFSET[1] = height.toFloat()
        MATRIX_OFFSET.mapPoints(TEMP_FLOAT_OFFSET)
        return (width - TEMP_FLOAT_OFFSET[0]) * if (rotation > 0.0f) 1.0f else -1.0f
    }
}