package com.example.mapbuildtry

import android.content.Context
import android.graphics.*

class objMapView {
    var bm: Bitmap
    var background: Bitmap
    var rect: Rect
    var sectors: objSectors
    var context: Context
    constructor(size: Point, context: Context) {
        this.context = context
        this.rect = Rect(-size.x * 2, -size.y * 2,
            size.x + size.x * 2, size.y + size.y * 2)
        this.background = BitmapFactory.decodeResource(context?.getResources(), R.drawable.anonim)
        this.bm = Bitmap.createScaledBitmap(this.background, size.x, size.y, false)
        sectors = objSectors(this.bm, size)
        draw_lines(size)
    }

    fun draw_lines(size: Point)
    {
        var src = IntArray((size.x * size.y))
        var i = 5
        for (k in 0..(size.x - 1))
        {
            for (l in 0..(size.y - 1)) {
                if ((l % i <= 0 || k % i <= 0))
                    src[k + l * size.x] = 0x30003033
            }
        }
        this.bm.setPixels(src, 0, size.x, 0, 0,
            size.x, size.y)
    }

    fun addPoint(point: vertex, isUp: Boolean)
    {
        this.sectors.DrawLine(point, isUp)
        if (isUp == false)
            this.sectors.bm.setPixel(point.x.toInt(), point.y.toInt(), 0xffffff00.toInt())
//        else
//        1) пройтись по всем секторам, найти среди них первый
        //isFinished == false
        //
//        this.points[this.points.size - 1] +=
    }
    fun Draw(canvas: Canvas, size: Point) {
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var rect = Rect(0, 0, size.x, size.y)
        canvas.drawBitmap(this.background!!, null, rect!!, paint)
        canvas.drawBitmap(this.bm!!, null, this.rect!!, paint)
        canvas.drawBitmap(this.sectors.bm!!, null, this.rect!!, paint)
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
