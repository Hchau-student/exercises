package com.example.mapbuildtry

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View

class MainActivity : AppCompatActivity() {

    val size = Point()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindowManager().getDefaultDisplay().getSize(size)
        setContentView(DrawView(this, size))
    }

    internal inner class DrawView(context: Context, size: Point): View(context) {
        var size = size
        var data: objData = objData(context, size)

        override fun onDraw(canvas: Canvas) {
            data.Draw(canvas/*, button, drawMap*/)
        }
        override fun onTouchEvent(event: MotionEvent): Boolean {
            ManageTouch(data, context, event)
            invalidate()
            return true
        }
    }
}
