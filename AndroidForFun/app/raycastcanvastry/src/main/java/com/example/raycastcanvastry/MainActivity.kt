package com.example.raycastcanvastry

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View


class MainActivity : AppCompatActivity() {

    //made with guide: https://lodev.org/cgtutor/raycasting.html
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val display = getWindowManager().getDefaultDisplay()
        val size = Point()

        display.getSize(size)

        setContentView(DrawView(this, size))
    }

    internal inner class DrawView(context: Context?, size: Point) :
        View(context) {
        var TouchScreen: t_TapScreen = t_TapScreen()
        val bm_size = size
        var bm = Bitmap.createBitmap(size.x, size.y,
        Bitmap.Config.ARGB_8888)
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var rect = Rect(0, 0, size.x, size.y)

        override fun onDraw(canvas: Canvas) {
            DrawCanvas(canvas, bm, rect, paint)
        }
        override fun onTouchEvent(event: MotionEvent): Boolean {
            val endColor = EndTapEvent(event, TouchScreen)

            DrawColor(bm_size, bm, if (endColor != 0) endColor else TouchScreen.getColorLongTouch())
            TapEvent(event, TouchScreen)

            invalidate()
            return true
        }
    }
}
