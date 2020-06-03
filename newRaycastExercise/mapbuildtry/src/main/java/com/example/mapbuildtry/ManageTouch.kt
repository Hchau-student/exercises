package com.example.mapbuildtry

import android.view.MotionEvent

fun longTouch(data: ObjData, /* ev: MotionEvent,*/ shiftB: Borders, isUp: Boolean) {
    val play = objPlay()
    if (data.buttons.drag.on) {
        play.dragMap(data, shiftB)
    } else {
        play.drawLine(data, /*shiftB,*/ isUp)
    }
}

fun shortTouch(/*data: ObjData, vertex: vertex*/) {
//    if (data.buttons.drag.on == true) {
//        ;//выполнить команду
//    }
//    if (data.buttons.draw.on == true) {
//    }
}

fun manageTouch(data: ObjData, /*context: Context,*/ ev: MotionEvent) {
    val touches: Borders
    val shortTouch: Int = isShortTouch(ev, data.touch)
    if (shortTouch != -1) {
        val where = data.touch.whereIsShortTouch(shortTouch)
        if (!data.buttons.ableButton(where)) {
            //кнопки не нажаты; что делаем, капитан?
            touches = longTapEventVertex(ev, data.touch)
            longTouch(data, /*ev,*/ touches, true)
            shortTouch(/*data, where*/)
        }
    }
    else {
        touches = longTapEventVertex(ev, data.touch)
        longTouch(data, /*ev,*/ touches, false)
//        if (endTapEvent(ev, data.touch) != -1.0f)
        if (longTapEnded(ev, data.touch))
            longTouch(data, /*ev,*/ touches, true)
    }
    endTapEvent(ev, data.touch)
}
