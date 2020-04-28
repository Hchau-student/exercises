package com.example.myapplication

/*
**      enviroment includes
*/
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
/*
**      Video management
*/
import android.widget.MediaController
import android.widget.VideoView
import android.net.Uri
/*
**      window management
*/
import android.view.View
import android.view.WindowManager
/*
**      to get first picture
*/
import android.view.MotionEvent
import android.graphics.*

//using in touchEvent to recognize touch location
fun     checkButton(y: Float, x: Float, win_size: Point) : String {
    if (y >= win_size.y / 2 && y <= win_size.y
        && x >= win_size.x / 6 && x <= win_size.x /3)
        return ("Play game") //left button
    if (y >= 0F && y <= win_size.y / 2
        && x >= win_size.x / 6 && x <= win_size.x /3)
        return ("Watch clip") //right button
    return ("Nothing")
}

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // getting size of window for correct calculations
        val display = getWindowManager().getDefaultDisplay()
        val win_size = Point()
        display.getSize(win_size)
        super.onCreate(savedInstanceState)
        // fixing screen orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        //drawing main view
        setContentView(DrawView(this, win_size))
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun watchVideoClip() {
        setContentView(R.layout.activity_main)
        var videoClip: VideoView = findViewById(R.id.bee_gees)
        val uri = Uri.parse("android.resource://"
                + getPackageName() + "/" + R.raw.bee_gees)
        videoClip.setVideoURI(uri)
        videoClip.setMediaController(MediaController(this))
        videoClip.start()
    }

    internal inner class DrawView(context: Context?, size: Point) :
        View(context) {
        //creating bitmap with main view
        val win_size = size
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var rect = Rect(0, 0, size.x, size.y)
        var icon = BitmapFactory.decodeResource(context?.getResources(),
            R.drawable.staying_alive_landscape)

        override fun onDraw(canvas: Canvas) {
            //putting bitmap on view
            canvas.translate(0F, 0F);
            canvas.save()
            canvas.translate(0f, 0f)
            canvas.drawBitmap(icon!!, null, rect!!, paint)
            canvas.restore()
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val evX = event.x
            val evY = event.y
            //checking if buttons were pressed
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val touchedScreen = checkButton(evY, evX, win_size)
                    if (touchedScreen.equals("Play game")) {
                        icon = BitmapFactory.decodeResource(
                            context?.getResources(),
                            R.drawable.slonik)
                    } else if (touchedScreen.equals("Watch clip")) {
                        watchVideoClip() //the right button
                    }
                    invalidate()
                }
                // button unpressed -> set default
                else -> {
                    icon = BitmapFactory.decodeResource(context?.getResources(),
                        R.drawable.staying_alive_landscape)
                    invalidate()
                }
            }
            return true
        }
    }
}
