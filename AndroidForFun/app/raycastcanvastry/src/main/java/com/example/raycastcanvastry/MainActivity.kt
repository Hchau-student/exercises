package com.example.raycastcanvastry

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_COUNTER2 = "counter2"
    val APP_PREFERENCES_COUNTER = "counter"
    var variable_progress: Int = 0
    lateinit var pref: SharedPreferences
    var my_val: Float = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        val display = getWindowManager().getDefaultDisplay()
        val size = Point()

        display.getSize(size)
        var r: r_Raycast = r_Raycast(size, this)

        setContentView(DrawView(this, size, r))
    }
    override fun onResume() {
        super.onResume()

        if (pref.contains(APP_PREFERENCES_COUNTER)) {
            variable_progress = pref.getInt(APP_PREFERENCES_COUNTER, 0)
        }
        if (pref.contains(APP_PREFERENCES_COUNTER2)) {
            my_val = (pref.getInt(APP_PREFERENCES_COUNTER2, 0) + 1) * 0.25f
            my_val = 1.0f
        }
    }

    internal inner class DrawView(context: Context?, size: Point, ray: r_Raycast) :
        View(context) {
        var raycast = ray
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var rect = Rect(0, 0, size.x, size.y)

        override fun onDraw(canvas: Canvas) {
            raycast.wallHeight = my_val
            if (raycast.pref.call == true) {
                val intent = Intent(this.context, Preferences::class.java)
                startActivity(intent)
                DrawCanvas(canvas, raycast.bm, raycast.pref, rect, paint)
            }
            DrawCanvas(canvas, raycast.bm, raycast.pref, rect, paint)
            raycast.pref.call = false
        }
        override fun onTouchEvent(event: MotionEvent): Boolean {
            raycast.pref.inverse = variable_progress
            PlayerMove(raycast, event)
            invalidate()
            return true
        }
    }
}
