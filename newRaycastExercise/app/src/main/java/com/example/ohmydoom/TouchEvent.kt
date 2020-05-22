package com.example.ohmydoom

import android.view.MotionEvent

fun LongTapEvent(event: MotionEvent, TouchScreen: TapScreen): Double {
    when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
            TouchScreen.tupDown(event)
            return (0.0)
        }
        MotionEvent.ACTION_POINTER_DOWN -> { //новое нажатие
            TouchScreen.tupDown(event)
            return (0.0)
        }
        MotionEvent.ACTION_MOVE -> { //движение
            return TouchScreen.renew(event)
        }
    }
    return (0.0)
}

fun IsShortTouch(event: MotionEvent, TouchScreen: TapScreen): Boolean {
    when (event.actionMasked) {
        MotionEvent.ACTION_UP -> {
            return (TouchScreen.isShortTouch(event.actionIndex))
        }
        MotionEvent.ACTION_POINTER_UP -> {
            return (TouchScreen.isShortTouch(event.actionIndex))
        }
        MotionEvent.ACTION_CANCEL -> {
            return (TouchScreen.isShortTouch(event.actionIndex))
        }
        else ->
            return (false)
    }
    return (false)
}

fun EndTapEvent(event: MotionEvent, TouchScreen: TapScreen): Float {
    var res: Float = 0.0f
    when (event.actionMasked) {
        MotionEvent.ACTION_UP -> {
            res = if (TouchScreen.isShortTouch(event.actionIndex) == true) 0.5f else 0.0f
            TouchScreen.endTouch(event)
            return (res)
        }
        MotionEvent.ACTION_POINTER_UP -> {
            res = if (TouchScreen.isShortTouch(event.actionIndex) == true) 0.5f else 0.0f
            TouchScreen.endTouch(event)
            return (res)
        }
        MotionEvent.ACTION_CANCEL -> {
            res = if (TouchScreen.isShortTouch(event.actionIndex) == true) 0.5f else 0.0f
            TouchScreen.endTouch(event)
            return (res)
        }
    }
    return (res)
}

fun PlayerMove(player: Player, TouchScreen: TapScreen, ev: MotionEvent) {
    var TouchCoord: Float = 0.0f
    var shortTouch: Boolean = IsShortTouch(ev, TouchScreen)
    if (shortTouch == true) {
        player.go()
    } else {
        TouchCoord = LongTapEvent(ev, TouchScreen).toFloat()
        if (TouchCoord != 0.0f)
            ;
        player.rotate(TouchCoord)
    }
    EndTapEvent(ev, TouchScreen)
}
