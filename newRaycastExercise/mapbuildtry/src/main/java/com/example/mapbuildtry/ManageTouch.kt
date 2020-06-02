package com.example.mapbuildtry

import android.content.Context
import android.view.MotionEvent

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

fun longTouch(data: objData, ev: MotionEvent, shiftB: borders, isUp: Boolean) {
    var play = objPlay()
    if (data.buttons.drag.on == true) {
        play.DragMap(data, shiftB)
    } else {
        play.DrawLine(data, shiftB, isUp)
        ;//play.DrawLine(data, shiftB)
    }
}

fun shortTouch(data: objData, vertex: vertex) {
//    if (data.buttons.drag.on == true) {
//        ;//выполнить команду
//    }
//    if (data.buttons.draw.on == true) {
//    }
}

fun ManageTouch(data: objData, context: Context, ev: MotionEvent) {
    var TouchVertex: borders
    var shortTouch: Int = IsShortTouch(ev, data.touch)
    if (shortTouch != -1) {
        //проверить нажатия на кнопки
        var where = data.touch.WhereIsShortTouch(shortTouch)
        if ((data.buttons.ableButton(where)) == true) {
            getMode(data, context)
        }
        else {//кнопки не нажаты; что делаем, капитан?
            shortTouch(data, where)
        }
    }
    else {
//        DrawPoint(ev, data)
        TouchVertex = LongTapEventVertex(ev, data.touch)
        longTouch(data, ev, TouchVertex, true)
        if (EndTapEvent(ev, data.touch) != -1.0f)
            longTouch(data, ev, TouchVertex, false)
    }
    EndTapEvent(ev, data.touch)
}
