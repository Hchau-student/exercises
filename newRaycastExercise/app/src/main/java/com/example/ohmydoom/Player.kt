package com.example.ohmydoom

import android.graphics.Point
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

class vertex {
    var x: Float
    var y: Float
    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }
}
