package com.example.dynamicviewkt

import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation


fun ViewGroup.hide() {
    this.visibility = View.GONE
}

fun ViewGroup.visible() {
    this.visibility = View.VISIBLE
}


fun ViewGroup.expand() {

    val viewGroup: ViewGroup = this

    viewGroup.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    val targetHeight: Int = this.measuredHeight

    viewGroup.layoutParams.height = 1
    viewGroup.visibility = View.VISIBLE

    val animation = object : Animation() {

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            viewGroup.layoutParams.height = if (interpolatedTime == 1f)
                WindowManager.LayoutParams.WRAP_CONTENT
            else
                (targetHeight * interpolatedTime).toInt()
            viewGroup.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    animation.duration = ((targetHeight / viewGroup.context.resources.displayMetrics.density).toInt().toLong())
    viewGroup.startAnimation(animation)


}

fun ViewGroup.collapse() {
    val viewGroup: ViewGroup = this

    val initialHeight = viewGroup.measuredHeight

    val anim = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {

                viewGroup.visibility = View.GONE

            } else {
                viewGroup.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                viewGroup.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }

    }

    anim.duration = (initialHeight / viewGroup.context.resources.displayMetrics.density).toInt().toLong()
    viewGroup.startAnimation(anim)
}