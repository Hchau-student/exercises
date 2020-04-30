package com.example.myapplication

import android.content.pm.ActivityInfo
import android.graphics.Point
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import kotlin.system.exitProcess

class watchClip : AppCompatActivity() {

    var see_button: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setContentView(R.layout.activity_watch_clip)

        //  initialasing video
        var videoClip: VideoView = findViewById(R.id.bee_gees)
        val uri = Uri.parse("android.resource://"
                + getPackageName() + "/" + R.raw.bee_gees_clip)
        videoClip.setVideoURI(uri)

        //  var mediaControll = MediaController(this)
        videoClip.setMediaController(MediaController(this))
        videoClip.start()

        //  initialasing button
        var backToMenuButton: Button = findViewById(R.id.backToMenuButton)
        backToMenuButton.setBackgroundResource(R.drawable.watch_clip_no_button)
    }

    /*
    **  changing button if screen was pressed
    */
    fun tapEvent(v: View?) {
        see_button = if (see_button == true) false else true
        var backToMenuButton: Button = findViewById(R.id.backToMenuButton)
        backToMenuButton.setBackgroundResource (
            if (see_button == true)
                R.drawable.watch_clip_button_back
            else
                R.drawable.watch_clip_no_button
        )
    }

    /*
    **  going to menu if button was pressed
    */
    fun buttonTapEvent(b: View?) {
        if (see_button == false)
            return
        val display = getWindowManager().getDefaultDisplay()
        val win_size = Point()
        display.getSize(win_size)
        exitProcess(0)
    }
}
