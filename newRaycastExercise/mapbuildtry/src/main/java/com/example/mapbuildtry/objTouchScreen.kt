package com.example.mapbuildtry

import android.view.MotionEvent
import kotlin.math.abs

class TapScreen {
    var FirstTouch: Touch =
        Touch(-1, false, FlVec2(0.0f, 0.0f))
    var SecondTouch: Touch =
        Touch(-1, false, FlVec2(0.0f, 0.0f))

    class FlVec2 {
        var x: Float
        var y: Float

        constructor(x: Float, y: Float) {
            this.x = x
            this.y = y
        }
    }

    class Touch {
        var pointer: Int
        var hold: Boolean
        var Begin: FlVec2
        var End: FlVec2
        var isMoving: Boolean
        var isLeft: Boolean
        var isTop: Boolean

        constructor(pointer: Int, hold: Boolean, Begin: FlVec2) {
            this.pointer = pointer
            this.isLeft = false
            this.isTop = false
            this.hold = hold
            this.Begin = Begin
            this.End = Begin
            this.isMoving = false
        }
    }

    fun tupDown(ev: MotionEvent, pointer: Int) {
            if ((FirstTouch.pointer == -1)) {
                FirstTouch = Touch(ev.getPointerId(pointer), true, FlVec2(ev.getX(pointer), ev.getY(pointer)))
            } else if ((SecondTouch.pointer == -1) and (FirstTouch.pointer != ev.getPointerId(pointer))) {
                SecondTouch = Touch(ev.getPointerId(pointer), true, FlVec2(ev.getX(pointer), ev.getY(pointer)))
                if (FirstTouch.Begin.x < SecondTouch.Begin.x) {
                    FirstTouch.isLeft = true; SecondTouch.isLeft = false
                } else {
                    FirstTouch.isLeft = false; SecondTouch.isLeft = true
                }
                if (FirstTouch.Begin.y < SecondTouch.Begin.y) {
                    FirstTouch.isTop = true; SecondTouch.isTop = false
                } else {
                    FirstTouch.isTop = false; SecondTouch.isTop = true
                }
            }
    }

    fun renew_touch(ev: MotionEvent, pointer: Int, touch: Touch): vertex {
        var res = vertex(0.0f, 0.0f)
        var ev_coord = vertex(ev.getX(ev.findPointerIndex(pointer)),
        ev.getY(ev.findPointerIndex(pointer)))
        if (touch.isMoving == false) {
           if ((abs(touch.Begin.x - ev_coord.x) <= 30.0)
                and (abs(touch.Begin.y - ev_coord.y) <= 30.0))
                return (res)
        }
        touch.End = FlVec2(ev_coord.x, ev_coord.y)
        touch.isMoving = true
        res.x = (touch.Begin.x - touch.End.x)
        res.y = (touch.Begin.y - touch.End.y)
        touch.Begin = FlVec2(ev_coord.x, ev_coord.y)
        return res
    }

    fun renew(ev: MotionEvent, pointer: Int): vertex {
        if ((pointer == SecondTouch.pointer) and (SecondTouch.hold == true))
            return renew_touch(ev, pointer, SecondTouch)
        if ((pointer == FirstTouch.pointer) and (FirstTouch.hold == true))
            return renew_touch(ev, pointer, FirstTouch)
        return (vertex(0.0f, 0.0f))
    }

    fun endTouch(pointerID: Int) {
        if (pointerID == SecondTouch.pointer) {
            SecondTouch = Touch(-1, false, FlVec2(0.0f, 0.0f))
        }
        if (pointerID == FirstTouch.pointer) {
            FirstTouch = Touch(-1, false, FlVec2(0.0f, 0.0f))
        }
    }

    //проверка
    fun isShortTouch(id: Int): Boolean {
        if ((FirstTouch.isMoving == false) && (id == FirstTouch.pointer)
            && FirstTouch.hold == true) {
            return (true)
        }
        if ((SecondTouch.isMoving == false) && (id == SecondTouch.pointer)
            && SecondTouch.hold == true) {
            return (true)
        }
        return (false)
    }

    fun WhereIsShortTouch(id: Int): vertex {
        if ((FirstTouch.isMoving == false) && (id == FirstTouch.pointer)
            && FirstTouch.hold == true) {
            return (vertex(FirstTouch.Begin.x, FirstTouch.Begin.y))
        }
        if ((SecondTouch.isMoving == false) && (id == SecondTouch.pointer)
            && SecondTouch.hold == true) {
            return (vertex(SecondTouch.Begin.x, SecondTouch.Begin.y))
        }
        return (vertex(0.0f, 0.0f))
    }
}
