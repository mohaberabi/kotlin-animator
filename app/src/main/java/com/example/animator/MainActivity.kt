package com.example.animator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class MainActivity : AppCompatActivity() {

    private lateinit var star: ImageView
    private lateinit var rotateButton: Button
    private lateinit var translateButton: Button
    private lateinit var scaleButton: Button
    private lateinit var fadeButton: Button
    private lateinit var colorizeButton: Button
    private lateinit var showerButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        star = findViewById(R.id.star)
        rotateButton = findViewById<Button>(R.id.rotateButton)
        translateButton = findViewById<Button>(R.id.translateButton)
        scaleButton = findViewById<Button>(R.id.scaleButton)
        fadeButton = findViewById<Button>(R.id.fadeButton)
        colorizeButton = findViewById<Button>(R.id.colorizeButton)
        showerButton = findViewById<Button>(R.id.showerButton)

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

        colorizeButton.setOnClickListener {
            colorizer()
        }

        showerButton.setOnClickListener {
            shower()
        }
    }


    private fun rotater() {


        val animator = ObjectAnimator.ofFloat(
            star,
            View.ROTATION,
            -360f,
            0f
        )

        animator.disableViewWhileAnimating(rotateButton)
        animator.duration = 1000
        animator.start()
    }

    private fun translater() {

        val animator = ObjectAnimator.ofFloat(
            star,
            View.TRANSLATION_X,
            200f
        )
        animator.disableViewWhileAnimating(translateButton)

        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE

        animator.start()
    }

    private fun scaler() {

        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)

        val animator = ObjectAnimator.ofPropertyValuesHolder(
            star,
            scaleX,
            scaleY
        )
        animator.disableViewWhileAnimating(scaleButton)

        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    private fun fader() {

        val animator = ObjectAnimator.ofFloat(
            star,
            View.ALPHA,
            0f
        )
        animator.disableViewWhileAnimating(fadeButton)

        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE

        animator.start()
    }

    private fun colorizer() {
//
//        var animator = ObjectAnimator.ofArgb(
//            star.parent,
//            "backgroundColor", Color.BLACK, Color.RED
//        )
//        animator.setDuration(500)
//        animator.repeatCount = 1
//        animator.repeatMode = ObjectAnimator.REVERSE
//        animator.disableViewWhileAnimating(bgColor)
//        animator.start()
    }

    private fun shower() {

        val container = star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW = star.width.toFloat()
        var starH = star.height.toFloat()
        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,

            )

        container.addView(newStar)

        newStar.scaleX = Math.random().toFloat() * 1.5f + 1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        newStar.translationX = Math.random().toFloat() * containerW - starW / 2

        val mover = ObjectAnimator.ofFloat(
            newStar,
            View.TRANSLATION_Y,
            -starH,
            containerH + starH
        )
        mover.interpolator = AccelerateInterpolator(1f)
        val rotator = ObjectAnimator.ofFloat(
            newStar,
            View.ROTATION,
            (Math.random() * 1080).toFloat(),
            containerH + starH
        )
        rotator.interpolator = LinearInterpolator()
        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 1500).toLong()
        set.start()

    }


    private fun ObjectAnimator.disableViewWhileAnimating(
        view: View,
    ) {

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.isEnabled = true
            }

            override fun onAnimationStart(animation: Animator) {
                view.isEnabled = false
            }
        })
    }

}