package com.example.raycastcanvastry

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.lang.Thread.sleep


class MainActivity : AppCompatActivity() {
    init {//библиотека компилится CMake'ом, лежит в директории приложухи;
        //если захочешь дописать свои файлы - прописывай их
        //в том же мейкфайле
        System.loadLibrary("native-lib")
    }//ф-ия написана в файле Trryy.cpp
    external fun stringFromJNI(x: Int, y: Int,
                               a_text: IntArray, b_text: IntArray, c_text: IntArray,
    pDirX: Float, pDirY: Float, pCameraX: Float, pCameraY: Float,
    PosX: Float, PosY: Float, WorldMap: IntArray, wm_w: Int, wm_h: Int): IntArray
    //не знаю, как передавать структуры в c++ файл

    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_COUNTER2 = "counter2"
    val APP_PREFERENCES_COUNTER = "counter"
    val APP_RESOLUTION = "resolution"
    var variable_progress: Int = 0
    lateinit var pref: SharedPreferences
    var my_val: Float = 1.0f
    var resolution: Int = 18

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
        if (pref.contains(APP_RESOLUTION)) {
            resolution = pref.getInt(APP_RESOLUTION, 0)
        }
    }

    internal inner class DrawView(context: Context?, size: Point, ray: r_Raycast) :
        View(context) {
        var image: IntArray = IntArray(0)


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
            var start: Int = 0
            if (pref.contains(APP_PREFERENCES_COUNTER)) {
                variable_progress = pref.getInt(APP_PREFERENCES_COUNTER, 0)
            }
//            if (pref.contains(APP_RESOLUTION)) {
                raycast.pref.resolution = resolution
//            }
            raycast.pref.inverse = if (variable_progress == 1) 1 else -1
            var srcPixels = IntArray(raycast.bm_size.x * raycast.bm_size.y)
            var end: Int = 100

            PlayerMove(raycast, event)
            val runnable: Runnable = object : Runnable {

                override fun run() {
                    r_DrawWalls(raycast.bm_size, raycast, start, end, srcPixels)
                    synchronized(this) {
                    }
                }
            }
            var thread: Thread = Thread()

            var step: Int = raycast.bm_size.x / 2
            start = -step
            end = 0
            while(end <= raycast.bm_size.x) {
                thread = Thread(runnable)
                start = end
                end += step
                thread.start()
                thread.join()
            }
            raycast.bm.setPixels(srcPixels, 0, raycast.bm_size.x,
                0, 0, raycast.bm_size.x, raycast.bm_size.y)
            invalidate()
            return true
        }
    }
}
