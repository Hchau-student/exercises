package com.example.myapplication

/*
**      enviroment includes
*/
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.content.Intent
/*
**      Video management
*/
import android.widget.Button
/*
**      window management
*/
import android.view.View
/*
**      to get first picture
*/
import android.graphics.*

class MainActivity : Activity() {

    //in main activity button should be visible only if screen was touched
    override fun onCreate(savedInstanceState: Bundle?) {

        // getting size of window for correct calculations
        val display = getWindowManager().getDefaultDisplay()
        val win_size = Point()
        display.getSize(win_size)
        super.onCreate(savedInstanceState)

        // fixing screen orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //drawing main view
        setContentView(R.layout.activity_main)
        var background: View = findViewById(R.id.start_background)
        var buttonPlay: Button = findViewById(R.id.buttonPlay)
        var buttonWatchClip: Button = findViewById(R.id.buttonWatchClip)
        var krown: View = findViewById(R.id.krown)
        var boss: View = findViewById(R.id.boss)

        background.setBackgroundResource(R.drawable.main_view_background)

        buttonPlay.setBackgroundResource(R.drawable.main_button_play_v3)
        buttonWatchClip.setBackgroundResource(R.drawable.main_button_watch_clip)

        krown.setBackgroundResource(R.drawable.main_view_krown)
        boss.setBackgroundResource(R.drawable.main_view_boss)

    }

    fun tapPlay(v: View?) {
        val intent = Intent(this, startGame::class.java)
        startActivity(intent)
    }

    fun tapWatch(v: View?) {
        val intent = Intent(this, watchClip::class.java)
        startActivity(intent)
    }
}
