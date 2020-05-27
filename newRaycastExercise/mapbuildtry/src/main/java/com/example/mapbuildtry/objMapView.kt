package com.example.mapbuildtry

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.Rect

class objMapView {
    var bm: Bitmap
    var rect: Rect
    var mapSize: borders
    var backSize: borders
    var beginPos: vertex
    var scale: Int
    var isMoving: Boolean
    constructor(size: Point, context: Context) {
        this.rect = Rect(-size.x / 2, -size.y / 2,
            size.x + size.x / 2, size.y + size.y / 2)
        var bm: Bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.anonim)
        this.bm = Bitmap.createScaledBitmap(bm, size.x, size.y, false)
        this.mapSize = borders(vertex(0.0f, 0.0f), vertex(size.x.toFloat(), size.y.toFloat()))
        this.backSize = borders(vertex(-size.x.toFloat() / 2, -size.y.toFloat() / 2),
            vertex(size.x.toFloat() + size.x.toFloat() / 2, size.y.toFloat() + size.y.toFloat() / 2))
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
