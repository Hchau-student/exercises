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
        var raycast: r_Raycast = r_Raycast(size, this.context)
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var rect = Rect(0, 0, size.x, size.y)

        override fun onDraw(canvas: Canvas) {
            DrawCanvas(canvas, raycast.bm, rect, paint)
        }
        override fun onTouchEvent(event: MotionEvent): Boolean {
            var endColor = EndTapEvent(event, raycast.TouchScreen)
            if (endColor != 0.0f) {
                raycast.player.moveSpeed = endColor.toDouble()
                raycast.player.go()
            } else {
                endColor = TapEvent(event, raycast.TouchScreen).toFloat()
                if (endColor != 0.0f)
                    raycast.player.rotate(endColor)
            }
            TapEvent(event, raycast.TouchScreen)
            r_DrawWalls(raycast.bm_size, raycast.bm, raycast)

            invalidate()
            return true
        }
    }
}
