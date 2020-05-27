package com.example.mapbuildtry

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View.MeasureSpec.getMode

    //
    //проверено, работает
    fun getMode(data: objData, context: Context) {
        if (data.buttons.drag.on == true) {
//            data.buttons.drag.bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.dancing)
//            data.buttons.draw.bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.dancing)
            ;//выполнить команду
        }
        if (data.buttons.draw.on == true) {
//            data.buttons.draw.bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.grad)
//            data.buttons.drag.bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.grad)
        }
    }
    class objPlay {
        fun short(data: objData) {
            if (data.buttons.drag.on == true) {
                ;//выполнить команду
            }
            if (data.buttons.draw.on == true) {
                ;//выполнить команду
            }
        }

        fun long(data: objData, ev: MotionEvent, shiftB: borders) {
            if (data.buttons.drag.on == true) {
                if ((data.touch.SecondTouch.isMoving == true) and
                    (data.touch.FirstTouch.pointer != data.touch.SecondTouch.pointer)) {

                    var shift = vertex(0.0f, 0.0f)
                    var shift1 = vertex(0.0f, 0.0f)
                    if (data.touch.FirstTouch.isLeft == true) {
                        shift.x = shiftB.begin.x * (data.touch.FirstTouch.Begin.x * 10 / data.size.x)
                        shift1.x = shiftB.end.x * ((data.touch.SecondTouch.Begin.x * 10 / data.size.x))
                    } else { shift.x = shiftB.end.x * (data.touch.SecondTouch.Begin.x * 10 / data.size.x)
                        shift1.x = shiftB.begin.x * (data.touch.FirstTouch.Begin.x * 10 / data.size.x) }

                    if (data.touch.FirstTouch.isTop == true) {
                        shift.y = shiftB.begin.y * (data.touch.FirstTouch.Begin.y * 10 / data.size.y)
                        shift1.y = shiftB.end.y * (data.touch.SecondTouch.Begin.y * 10 / data.size.y)
                    } else { shift.y = shiftB.end.y * (data.touch.SecondTouch.Begin.y * 10 / data.size.y)
                        shift1.y = shiftB.begin.y * (data.touch.FirstTouch.Begin.y * 10 / data.size.y) }

                    data.mapView.rect = Rect(data.mapView.rect.left - (shift.x).toInt(),
                        data.mapView.rect.top - shift.y.toInt(),
                        data.mapView.rect.right - shift1.x.toInt(),
                        data.mapView.rect.bottom - shift1.y.toInt())
                } else {
                    var shift = if (shiftB.begin.equals(vertex(0.0f, 0.0f)))
                        shiftB.end else shiftB.begin
                    data.mapView.rect = Rect(
                        data.mapView.rect.left - shift.x.toInt(),
                        data.mapView.rect.top - shift.y.toInt(),
                        data.mapView.rect.right - shift.x.toInt(),
                        data.mapView.rect.bottom - shift.y.toInt()
                    )
                }
            }
        }
    }

fun ManageTouch(data: objData, context: Context, ev: MotionEvent) {
    var TouchVertex: borders
    var play = objPlay()
    var shortTouch: Int = IsShortTouch(ev, data.touch)
    if (shortTouch != -1) {
        //проверить нажатия на кнопки
        if ((data.buttons.ableButton(data.touch.WhereIsShortTouch(shortTouch))) == true) {
            getMode(data, context)
        }
        else {//кнопки не нажаты; что делаем, капитан?
            play.short(data)
        }
    }
    else {
        TouchVertex = LongTapEventVertex(ev, data.touch)
            play.long(data, ev, TouchVertex)
    }
    EndTapEvent(ev, data.touch)
}
