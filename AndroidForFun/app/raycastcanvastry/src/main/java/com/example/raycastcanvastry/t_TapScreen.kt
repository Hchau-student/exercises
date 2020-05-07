package com.example.raycastcanvastry

import android.view.MotionEvent

class t_TapScreen {
    var FirstTouch: Touch =
        Touch(0, false, FlVec2(0.0f, 0.0f))
    var SecondTouch: Touch =
        Touch(0, false, FlVec2(0.0f, 0.0f))

    class FlVec2 {
        var x: Float = 0.0f
        var y: Float = 0.0f

        constructor(_x: Float, _y: Float) {
            this.x = _x
            this.y = _y
        }
    }

    class Touch {
        var pointer: Int = 0
        var hold: Boolean = false
        var Begin: FlVec2 = FlVec2(0.0f, 0.0f)
        var End: FlVec2 = FlVec2(0.0f, 0.0f)
        var isMoving: Boolean = false

        constructor(_pointer: Int, _hold: Boolean, _Begin: FlVec2) {
            this.pointer = _pointer
            this.hold = _hold
            this.Begin = _Begin
            this.End = _Begin
        }
    }

    fun tupDown(ev: MotionEvent) {
        if (FirstTouch.hold == false) {
            FirstTouch = Touch(ev.actionIndex, true, FlVec2(ev.x, ev.y))
        } else if (SecondTouch.hold == false) {
            SecondTouch = Touch(ev.actionIndex, true, FlVec2(ev.x, ev.y))
        }
    }

    fun renew(ev: MotionEvent) {
        if (ev.actionIndex == FirstTouch.pointer && FirstTouch.hold == true) {
            FirstTouch.End = FlVec2(ev.x, ev.y)
            if (FirstTouch.Begin.equals(FirstTouch.End) == false) {
                FirstTouch.isMoving = true
            }
            //повернуться
        } else if (ev.actionIndex == SecondTouch.pointer && SecondTouch.hold == true) {
            SecondTouch.End = FlVec2(ev.x, ev.y)
            if (SecondTouch.End.equals(SecondTouch.Begin) == false) {
                SecondTouch.isMoving = true
            }
        }
    }

    fun endTouch(ev: MotionEvent) {
        if (ev.actionIndex == SecondTouch.pointer) {
            if (SecondTouch.hold == true)
                SecondTouch.hold = false
        } else if (ev.actionIndex == FirstTouch.pointer) {
            FirstTouch.hold = false
        }
    }





    //проверка
    fun getColorShortTouch(): Int {
        if (FirstTouch.hold == true && FirstTouch.isMoving == false) {
            return (0x00ff00ff)
        } else if (SecondTouch.hold == true && SecondTouch.isMoving == false) {
            return (0x70ff00ff)
        }
        return (0)
    }
    fun getColorLongTouch(): Int {
        if (FirstTouch.hold == true && FirstTouch.isMoving == true) {
            var res: Int = ((FirstTouch.Begin.x - FirstTouch.End.x +
                    FirstTouch.Begin.y - FirstTouch.End.y)).toInt() % 255
            return (if (res < 0) res * -1 else res)
        }
        if (SecondTouch.hold == true && SecondTouch.isMoving == true) {
            var res: Int = ((SecondTouch.Begin.x - SecondTouch.End.x +
                    SecondTouch.Begin.y - SecondTouch.End.y)).toInt() % 255
            return (if (res < 0) res * -1 else res)
        }
        return (0)
    }
}
