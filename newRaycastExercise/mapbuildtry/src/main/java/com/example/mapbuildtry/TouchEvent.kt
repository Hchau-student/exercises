package com.example.mapbuildtry

import android.view.MotionEvent

fun LongTapEventVertex(event: MotionEvent, TouchScreen: TapScreen): borders {
    var ret: borders = borders(vertex(0.0f, 0.0f), vertex(0.0f, 0.0f))
    for (pointer in 0..event.pointerCount - 1) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                TouchScreen.tupDown(event, pointer)
            }
            MotionEvent.ACTION_POINTER_DOWN -> { //новое нажатие
                TouchScreen.tupDown(event, pointer)
            }
            MotionEvent.ACTION_MOVE -> { //движение
                if (event.getPointerId(pointer) == TouchScreen.FirstTouch.pointer)
                    ret.begin = TouchScreen.renew(event, event.getPointerId(pointer))
                if (event.getPointerId(pointer) == TouchScreen.SecondTouch.pointer)
                    ret.end = TouchScreen.renew(event, event.getPointerId(pointer))
            }
        }
    }
    return (ret)
}

fun IsShortTouch(event: MotionEvent, TouchScreen: TapScreen): Int {
    var ret: Int = -1
    for (pointer in 0..event.pointerCount - 1)
    when (event.actionMasked) {
        MotionEvent.ACTION_UP -> {
            ret = if (TouchScreen.isShortTouch(event.getPointerId(pointer)))
                event.getPointerId(pointer) else -1
        }
        MotionEvent.ACTION_POINTER_UP -> {
            ret = if (TouchScreen.isShortTouch(event.getPointerId(pointer)))
                event.getPointerId(pointer) else -1
        }
        MotionEvent.ACTION_CANCEL -> {
            ret = if (TouchScreen.isShortTouch(event.getPointerId(pointer)))
                event.getPointerId(pointer) else -1
        }
    }
    return (ret)
}

fun EndTapEvent(event: MotionEvent, TouchScreen: TapScreen): Float {
    var res: Float = 0.0f
    for (pointer in 0..event.pointerCount - 1) {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                res =
                    if (TouchScreen.isShortTouch(event.getPointerId(pointer)) == true) 0.5f else 0.0f
                TouchScreen.endTouch(event.getPointerId(pointer))
            }
            MotionEvent.ACTION_POINTER_UP -> {
                res =
                    if (TouchScreen.isShortTouch(event.getPointerId(pointer)) == true) 0.5f else 0.0f
                TouchScreen.endTouch(event.getPointerId(pointer))
            }
            MotionEvent.ACTION_CANCEL -> {
                res =
                    if (TouchScreen.isShortTouch(event.getPointerId(pointer)) == true) 0.5f else 0.0f
                TouchScreen.endTouch(event.getPointerId(pointer))
            }
        }
    }
    return (res)
}

fun DrawPoint(event: MotionEvent, data: objData)
{
    var play = objPlay()
    for (pointer in 0..event.pointerCount - 1) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                data.touch.tupDown(event, pointer)
                play.DrawPoint(data, event.getPointerId(pointer))
            }
            MotionEvent.ACTION_POINTER_DOWN -> { //новое нажатие
                data.touch.tupDown(event, pointer)
                play.DrawPoint(data, event.getPointerId(pointer))
            }
        }
    }
}