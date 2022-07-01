package com.example.lottietest

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.airbnb.lottie.LottieAnimationView

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        configListener()
    }

    private fun configListener() {
        findViewById<Button>(R.id.playButton).apply {
            setOnClickListener {
                setupAnimation()
            }
        }
    }

    private fun setupAnimation() {
        findViewById<LottieAnimationView>(R.id.trackersAnimation).apply {
            setCacheComposition(false)
            setAnimation(R.raw.light_trackers)
            maintainOriginalImageBounds = true
            setImageAssetDelegate {
                generateDefaultDrawable(context).toBitmap(24.toPx(), 24.toPx())
            }
        }.playAnimation()
    }

    private fun generateDefaultDrawable(
        context: Context,
        letter: String = "R"
    ): Drawable {
        return object : Drawable() {

            private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = ContextCompat.getColor(context, R.color.purple_200)
            }

            private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = ContextCompat.getColor(context, R.color.white)
                typeface = Typeface.SANS_SERIF
            }

            override fun draw(canvas: Canvas) {
                val centerX = bounds.width() * 0.5f
                val centerY = bounds.height() * 0.5f
                textPaint.textSize = (bounds.width() / 2).toFloat()
                val textWidth: Float = textPaint.measureText(letter) * 0.5f
                val textBaseLineHeight = textPaint.fontMetrics.ascent * -0.4f
                canvas.drawCircle(centerX, centerY, centerX, backgroundPaint)
                canvas.drawText(letter, centerX - textWidth, centerY + textBaseLineHeight, textPaint)
            }

            override fun setAlpha(alpha: Int) {
            }

            override fun setColorFilter(colorFilter: ColorFilter?) {
            }

            override fun getOpacity(): Int {
                return PixelFormat.TRANSPARENT
            }
        }
    }
}

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()