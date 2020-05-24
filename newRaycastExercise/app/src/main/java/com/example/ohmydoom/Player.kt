package com.example.ohmydoom

import android.graphics.Point
import android.view.MotionEvent
import kotlin.math.cos
import kotlin.math.sin

class Player {
    var Pos: vertex
    var angle: Float
    constructor(x: Float, y: Float, angle: Float) {
        this.Pos = vertex(x, y)
        this.angle = angle
    }
    fun go(/*передать карту*/) {
        Pos.x += cos(angle) * 20
        Pos.y += sin(angle) * 20

    }
    fun rotate(touch: Float) {
        angle += (touch) / 100
    }
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
