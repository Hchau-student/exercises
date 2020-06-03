package com.example.mapbuildtry

import android.view.MotionEvent

fun longTapEventVertex(event: MotionEvent, TouchScreen: TapScreen): Borders {
    val ret = Borders(Vertex(0.0f, 0.0f), Vertex(0.0f, 0.0f))
    for (pointer in 0 until event.pointerCount) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                TouchScreen.tupDown(event, pointer)
            }
            MotionEvent.ACTION_POINTER_DOWN -> { //новое нажатие
                TouchScreen.tupDown(event, pointer)
            }
            MotionEvent.ACTION_MOVE -> { //движение
                if (event.getPointerId(pointer) == TouchScreen.firstTouch.pointer)
                    ret.begin = TouchScreen.renew(event, event.getPointerId(pointer))
                if (event.getPointerId(pointer) == TouchScreen.secondTouch.pointer)
                    ret.end = TouchScreen.renew(event, event.getPointerId(pointer))
            }
        }
    }
    return (ret)
}

fun isShortTouch(event: MotionEvent, TouchScreen: TapScreen): Int {
    var ret: Int = -1
    for (pointer in 0 until event.pointerCount) {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                ret = if (TouchScreen.isShortTouch(event.getPointerId(pointer)))
                    event.getPointerId(pointer) else -1
                if (ret != -1) return ret
            }
            MotionEvent.ACTION_POINTER_UP -> {
                ret = if (TouchScreen.isShortTouch(event.getPointerId(pointer)))
                    event.getPointerId(pointer) else -1
                if (ret != -1) return ret
            }
            MotionEvent.ACTION_CANCEL -> {
                ret = if (TouchScreen.isShortTouch(event.getPointerId(pointer)))
                    event.getPointerId(pointer) else -1
                if (ret != -1) return ret
            }
        }
    }
    return (ret)
}

fun endTapEvent(event: MotionEvent, TouchScreen: TapScreen): Float {
    var res: Float = -1.0f
    for (pointer in 0 until event.pointerCount) {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                res =
                    if (TouchScreen.isShortTouch(event.getPointerId(pointer))) 0.5f else 0.0f
                TouchScreen.endTouch(event.getPointerId(pointer))
            }
            MotionEvent.ACTION_POINTER_UP -> {
                res =
                    if (TouchScreen.isShortTouch(event.getPointerId(pointer))) 0.5f else 0.0f
                TouchScreen.endTouch(event.getPointerId(pointer))
            }
            MotionEvent.ACTION_CANCEL -> {
                res =
                    if (TouchScreen.isShortTouch(event.getPointerId(pointer))) 0.5f else 0.0f
                TouchScreen.endTouch(event.getPointerId(pointer))
            }
        }
    }
    return (res)
}

fun checkIsEnded(pointer: Int, screen: TapScreen): Boolean {
    if (screen.firstTouch.pointer == pointer && screen.firstTouch.isMoving)
        return true
    if (screen.firstTouch.pointer == pointer && screen.secondTouch.isMoving)
        return true
    return false
}

fun longTapEnded(event: MotionEvent, screen: TapScreen): Boolean {
    for (pointer in 0 until event.pointerCount) {
        when (event.actionMasked) {
            MotionEvent.ACTION_UP -> {
                return checkIsEnded (event.getPointerId(pointer), screen)
            }
            MotionEvent.ACTION_POINTER_UP -> {
                return checkIsEnded (event.getPointerId(pointer), screen)
            }
            MotionEvent.ACTION_CANCEL -> {
                return checkIsEnded (event.getPointerId(pointer), screen)
            }
        }
    }
    return false
}

//fun DrawPoint(event: MotionEvent, data: objData)
//{
//    var play = objPlay()
//    for (pointer in 0..event.pointerCount - 1) {
//        when (event.actionMasked) {
//            MotionEvent.ACTION_DOWN -> {
//                data.touch.tupDown(event, pointer)
//                play.DrawPoint(data, event.getPointerId(pointer))
//            }
//            MotionEvent.ACTION_POINTER_DOWN -> { //новое нажатие
//                data.touch.tupDown(event, pointer)
//                play.DrawPoint(data, event.getPointerId(pointer))
//            }
//        }
//    }
//}