package com.example.mapbuildtry

import android.content.Context
import android.graphics.*

class ObjMapView(size: Point, context: Context) {
    private var background: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.anonim)
    private var bm: Bitmap =
        Bitmap.createScaledBitmap(this.background, size.x, size.y, false)
    private val sectors = ObjSectors(this.bm, size, context)
    var rect: Rect = Rect(-size.x * 2, -size.y * 2,
        size.x + size.x * 2, size.y + size.y * 2)

    init { drawLines(size) }

    private fun drawLines(size: Point)
    {
        val src = IntArray((size.x * size.y))
        val i = 5
        for (k in 0 until size.x)
        {
            for (l in 0 until size.y) {
                if ((l % i <= 0 || k % i <= 0))
                    src[k + l * size.x] = 0x30003033
            }
        }
        this.bm.setPixels(src, 0, size.x, 0, 0,
            size.x, size.y)
    }

    fun addPoint(point: Vertex, isUp: Boolean)
    {
        this.sectors.drawLine(point, isUp)
    }

    fun draw(canvas: Canvas, size: Point) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rect = Rect(0, 0, size.x, size.y)
        canvas.drawBitmap(this.background, null, rect, paint)
        canvas.drawBitmap(this.bm, null, this.rect, paint)
        canvas.drawBitmap(this.sectors.bm, null, this.rect, paint)
    }
}

class Borders(var begin: Vertex, var end: Vertex)

class Vertex(var x: Float, var y: Float)
