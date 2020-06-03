package com.example.mapbuildtry

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View

class MainActivity : AppCompatActivity() {

    private val size = Point()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        windowManager.defaultDisplay.getSize(size)
        setContentView(DrawView(this, size))
    }

    internal inner class DrawView(context: Context, size: Point): View(context) {
        private var data: ObjData = ObjData(context, size)

        override fun onDraw(canvas: Canvas) {
            data.draw(canvas)
        }
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(event: MotionEvent): Boolean {
            manageTouch(data, /*context, */event)
            invalidate()
            return true
        }
    }
}
