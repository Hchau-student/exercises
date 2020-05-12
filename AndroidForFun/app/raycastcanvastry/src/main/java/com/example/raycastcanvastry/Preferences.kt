package com.example.raycastcanvastry

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class Preferences : AppCompatActivity() {

    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_COUNTER = "counter"
    val APP_PREFERENCES_COUNTER2 = "counter2"
    lateinit var pref: SharedPreferences
    var variable_progress: Int = 0
    var variable_progress2: Int = 0

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        mainMenu()
        super.onCreate(savedInstanceState)
    }
    override fun onPause() {
        super.onPause()

        // Запоминаем данные
        val editor = pref.edit()
        editor.putInt(APP_PREFERENCES_COUNTER, variable_progress)
        editor.putInt(APP_PREFERENCES_COUNTER2, variable_progress2)
        editor.apply()
    }
    override fun onResume() {
        super.onResume()

        if (pref.contains(APP_PREFERENCES_COUNTER)) {
            variable_progress = pref.getInt(APP_PREFERENCES_COUNTER, 0)
        }
        if (pref.contains(APP_PREFERENCES_COUNTER2)) {
            variable_progress2 = pref.getInt(APP_PREFERENCES_COUNTER2, 0)
        }
    }

    fun mainMenu() {
        setContentView(R.layout.game_preferences)
        var background: View = findViewById(R.id.background)
        background.setBackgroundResource(R.drawable.gradient)
        var game_Control: Button = findViewById(R.id.button1)
        game_Control.getBackground().setColorFilter(0, PorterDuff.Mode.MULTIPLY)
        val game_Control2: Button = findViewById(R.id.button2)
        game_Control2.getBackground().setColorFilter(0, PorterDuff.Mode.MULTIPLY)
        val game_Control3: Button = findViewById(R.id.button3)
        game_Control3.getBackground().setColorFilter(0, PorterDuff.Mode.MULTIPLY)
        val game_Control4: Button = findViewById(R.id.button4)
        game_Control4.getBackground().setColorFilter(0, PorterDuff.Mode.MULTIPLY)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun tapEventGameControl(v: View?) {


        setContentView(R.layout.preferences_control_game)
        var background: View = findViewById(R.id.background)
        background.setBackgroundResource(R.drawable.gradient)
        var inverse: SeekBar = findViewById(R.id.inverse)
        var inverse2: SeekBar = findViewById(R.id.seekBar2)
        inverse.setProgress(variable_progress)
        inverse2.setProgress(variable_progress2)

        inverse.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                var str_progress = getString(R.string.inverse)
                variable_progress = i
                var sharedPref = getSharedPreferences(str_progress, Context.MODE_PRIVATE)
                val editor: Editor = sharedPref.edit()
                editor.putInt(getString(R.string.inverse), variable_progress)
                editor.commit()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        inverse2.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                var str_progress = getString(R.string.inverse)
                variable_progress2 = i
                var sharedPref = getSharedPreferences(str_progress, Context.MODE_PRIVATE)
                val editor: Editor = sharedPref.edit()
                editor.putInt(getString(R.string.inverse), variable_progress2)
                editor.commit()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }



    fun tapEventBack(v: View?) {
        mainMenu()
    }

    fun tapEventGame(v: View?) {
        finish()
    }

    fun tapEventSeek(v: View?) {

    }
}
