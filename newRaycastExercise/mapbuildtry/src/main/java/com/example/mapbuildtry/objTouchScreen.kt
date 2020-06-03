package com.example.mapbuildtry

import android.view.MotionEvent
import kotlin.math.abs

class TapScreen {
    var firstTouch: Touch =
        Touch(-1, false, FlVec2(0.0f, 0.0f))
    var secondTouch: Touch =
        Touch(-1, false, FlVec2(0.0f, 0.0f))
    class FlVec2(var x: Float, var y: Float)

    class Touch(var pointer: Int, var hold: Boolean, var Begin: FlVec2) {
        var end: FlVec2 = Begin
        var isMoving = false
        var isLeft = false
        var isTop = false

    }

    fun tupDown(ev: MotionEvent, pointer: Int) {
            if ((firstTouch.pointer == -1)) {
                firstTouch = Touch(ev.getPointerId(pointer), true, FlVec2(ev.getX(pointer), ev.getY(pointer)))
            } else if ((secondTouch.pointer == -1) and (firstTouch.pointer != ev.getPointerId(pointer))) {
                secondTouch = Touch(ev.getPointerId(pointer), true, FlVec2(ev.getX(pointer), ev.getY(pointer)))
                if (firstTouch.Begin.x < secondTouch.Begin.x) {
                    firstTouch.isLeft = true; secondTouch.isLeft = false
                } else {
                    firstTouch.isLeft = false; secondTouch.isLeft = true
                }
                if (firstTouch.Begin.y < secondTouch.Begin.y) {
                    firstTouch.isTop = true; secondTouch.isTop = false
                } else {
                    firstTouch.isTop = false; secondTouch.isTop = true
                }
            }
    }

    private fun renewTouch(ev: MotionEvent, pointer: Int, touch: Touch): Vertex {
        val res = Vertex(0.0f, 0.0f)
        val evTouch = Vertex(ev.getX(ev.findPointerIndex(pointer)),
        ev.getY(ev.findPointerIndex(pointer)))
        if (!touch.isMoving) {
           if ((abs(touch.Begin.x - evTouch.x) <= 30.0)
                and (abs(touch.Begin.y - evTouch.y) <= 30.0))
                return (res)
        }
        touch.end = FlVec2(evTouch.x, evTouch.y)
        touch.isMoving = true
        res.x = (touch.Begin.x - touch.end.x)
        res.y = (touch.Begin.y - touch.end.y)
        touch.Begin = FlVec2(evTouch.x, evTouch.y)
        return res
    }

    fun renew(ev: MotionEvent, pointer: Int): Vertex {
        if ((pointer == secondTouch.pointer) and (secondTouch.hold))
            return renewTouch(ev, pointer, secondTouch)
        if ((pointer == firstTouch.pointer) and (firstTouch.hold))
            return renewTouch(ev, pointer, firstTouch)
        return (Vertex(0.0f, 0.0f))
    }

    fun endTouch(pointerID: Int) {
        if (pointerID == secondTouch.pointer) {
            secondTouch = Touch(-1, false, FlVec2(0.0f, 0.0f))
        }
        if (pointerID == firstTouch.pointer) {
            firstTouch = Touch(-1, false, FlVec2(0.0f, 0.0f))
        }
    }

    //проверка
    fun isShortTouch(id: Int): Boolean {
        if (!firstTouch.isMoving && (id == firstTouch.pointer) && firstTouch.hold) {
            return (true)
        }
        if (!secondTouch.isMoving && (id == secondTouch.pointer) && secondTouch.hold) {
            return (true)
        }
        return (false)
    }

    fun whereIsShortTouch(id: Int): Vertex {
        if (!firstTouch.isMoving && (id == firstTouch.pointer) && firstTouch.hold) {
            return (Vertex(firstTouch.Begin.x, firstTouch.Begin.y))
        }
        if (!secondTouch.isMoving && (id == secondTouch.pointer) && secondTouch.hold) {
            return (Vertex(secondTouch.Begin.x, secondTouch.Begin.y))
        }
        return (Vertex(0.0f, 0.0f))
    }
}
