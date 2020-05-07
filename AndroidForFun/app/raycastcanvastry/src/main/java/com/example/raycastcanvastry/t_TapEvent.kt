package com.example.raycastcanvastry

import android.view.MotionEvent

fun TapEvent(event: MotionEvent, TouchScreen: t_TapScreen) {
    when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
            TouchScreen.tupDown(event)
        }
        MotionEvent.ACTION_POINTER_DOWN -> { //новое нажатие
            TouchScreen.tupDown(event)
        }
        MotionEvent.ACTION_MOVE -> { //движение
            TouchScreen.renew(event)
        }
    }
}

fun EndTapEvent(event: MotionEvent, TouchScreen: t_TapScreen): Int {
    var res: Int = 0
    when (event.actionMasked) {
        MotionEvent.ACTION_UP -> {
            res = TouchScreen.getColorShortTouch()
            TouchScreen.endTouch(event)
        }
        MotionEvent.ACTION_POINTER_UP -> {
            res = TouchScreen.getColorShortTouch()
            TouchScreen.endTouch(event)
        }
    }
    return (res)
}