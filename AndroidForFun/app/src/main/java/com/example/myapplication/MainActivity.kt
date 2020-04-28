package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val display = getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(DrawView(this, size))
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun setBeeGees() {
        setContentView(R.layout.activity_main)
        var beeGees: VideoView = findViewById(R.id.bee_gees)
        val uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bee_gees)
        beeGees.setVideoURI(uri)
        beeGees.setMediaController(MediaController(this))
        beeGees.start()
    }

    internal inner class DrawView(context: Context?, size: Point) :
        View(context) {
        val hsize = size
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var rect = Rect(0, 0, size.x, size.y)
        var icon = BitmapFactory.decodeResource(context?.getResources(), R.drawable.staying_alive_landscape)

        override fun onDraw(canvas: Canvas) {
            canvas.translate(0F, 0F);
            canvas.save()
            canvas.translate(0f, 0f)
            canvas.drawBitmap(icon!!, null, rect!!, paint)
            canvas.restore()
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val evX = event.x
            val evY = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (evY >= 0F && evY <= hsize.y / 2 && evX >= hsize.x / 6 && evX <= hsize.x /3)
                        setBeeGees()  //здесь сделать выход на другой activity
                    else if (evY >= hsize.y / 2 && evY <= hsize.y && evX >= hsize.x / 6 && evX <= hsize.x /3) {
                        icon = BitmapFactory.decodeResource(context?.getResources(), R.drawable.slonik)
                    }
                    invalidate()
                }
                else -> {
                    icon = BitmapFactory.decodeResource(context?.getResources(), R.drawable.staying_alive_landscape)
                    invalidate()
                }
            }
            return true
        }
    }
}

