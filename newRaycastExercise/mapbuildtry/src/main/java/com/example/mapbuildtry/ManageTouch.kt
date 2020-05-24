package com.example.mapbuildtry

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Point
import android.view.MotionEvent
import android.view.View.MeasureSpec.getMode

    //
    //проверено, работает
    fun getMode(data: objData) {
        if (data.buttons.drag.on == true) {
            ;//выполнить команду
//            data.buttons.draw.bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.grad)
//            data.buttons.drag.bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.grad)
        }
        if (data.buttons.draw.on == true) {
//            data.buttons.draw.bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.dancing)
//            data.buttons.drag.bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.dancing)
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

        fun long(data: objData) {
            if (data.buttons.drag.on == true) {
                ;//выполнить команду
            }
            if (data.buttons.draw.on == true) {
                ;//выполнить команду
            }
        }
    }



fun ManageTouch(data: objData, context: Context, ev: MotionEvent) {
    var TouchCoord: Float
    var TouchVertex: vertex
    var play: objPlay = objPlay()
    var shortTouch: Boolean = IsShortTouch(ev, data.touch)
    if (shortTouch == true) {
        //проверить нажатия на кнопки
        TouchVertex = data.touch.WhereIsShortTouch(ev.actionIndex)
        if ((data.buttons.ableButton(TouchVertex)) == true) {
            EndTapEvent(ev, data.touch)
//            var j: objMapTouches = objMapTouches()
            getMode(data)
        }
        else {//кнопки не нажаты; что делаем, капитан?
            play.short(data)
//            ;
        }
    }
    else {
        TouchCoord = LongTapEvent(ev, data.touch).toFloat()
        if (TouchCoord != 0.0f)
        ;
        play.long(data)
//        player.rotate(TouchCoord)
    }

    EndTapEvent(ev, data.touch)
}
