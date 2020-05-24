package com.example.mapbuildtry

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point

class objMapView {
    var bm: Bitmap
    var mapSize: borders
    var backSize: borders
    var beginPos: vertex
    var scale: Int
    var isMoving: Boolean
    constructor(size: Point, context: Context) {
        var bm: Bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.background)
        this.bm = Bitmap.createScaledBitmap(bm, size.x, size.y, false)
        this.mapSize = borders(vertex(0.0f, 0.0f), vertex(size.x.toFloat(), size.y.toFloat()))
        this.backSize = borders(vertex(0.0f, 0.0f), vertex(size.x.toFloat() * 2, size.y.toFloat() * 2))
        this.beginPos = vertex(0.0f, 0.0f)
        scale = 100
        isMoving = false
    }
}

class borders {
    var begin: vertex
    var end: vertex
    constructor(begin: vertex, end: vertex) {
        this.begin = begin
        this.end = end
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
