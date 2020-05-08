package com.example.raycastcanvastry

import android.view.MotionEvent

fun TapEvent(event: MotionEvent, TouchScreen: t_TapScreen): Double {
    when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
            TouchScreen.tupDown(event)
        }
        MotionEvent.ACTION_POINTER_DOWN -> { //новое нажатие
            TouchScreen.tupDown(event)
        }
        MotionEvent.ACTION_MOVE -> { //движение
            return TouchScreen.renew(event)
        }
    }
    return (0.0)
}

fun EndTapEvent(event: MotionEvent, TouchScreen: t_TapScreen): Float {
    var res: Float = 0.0f
    when (event.actionMasked) {
        MotionEvent.ACTION_UP -> {
            res = 0.5f
            TouchScreen.endTouch(event)
        }
        MotionEvent.ACTION_POINTER_UP -> {
            res = 0.5f
            TouchScreen.endTouch(event)
        }
    }
    return (res)
}