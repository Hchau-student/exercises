package com.example.ohmydoom

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import kotlin.math.cos
import kotlin.math.sin

fun isDrawableVertex(v1: vertex, size: Point) : Boolean {
    if (v1.x < 0 || v1.y < 0 || v1.x > size.x - 400 || v1.y > size.y / 3 - 400)
        return false
    return true
}

//fun drawFirstSector(size: Point, map: Canvas, src: objDrawMap, segment: Int)

fun drawScreenSegment(size: Point, map: Canvas, src: objDrawMap, segment: Int) {
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.setColor(Color.YELLOW)
    paint.setStrokeWidth(15.0f)
    var square: Array<vertex> = arrayOf(vertex(200.0f, 45.0f + (size.y / 3) * (segment - 1)),
    vertex(size.x - 195.0f, (size.y / 3) * segment - 200.0f)
    )
//splitScreen
    map.drawLine(square[0].x, square[0].y, square[0].x, square[1].y, paint)
    map.drawLine(square[0].x, square[0].y, square[1].x, square[0].y, paint)
    map.drawLine(square[1].x, square[0].y, square[1].x, square[1].y, paint)
    map.drawLine(square[0].x, square[1].y, square[1].x, square[1].y, paint)
    paint.setColor(Color.argb(0xff, 0x00, 0xff, 0xff))
    //draw walls
    for (i in 0..src.vertexArray.size - 2) {
        if (isDrawableVertex(src.vertexArray[i], size)) {
            if (segment == 1) {
                map.drawLine(
                src.vertexArray[i].x + 200, src.vertexArray[i].y + square[0].y,
                src.vertexArray[i + 1].x + 200, src.vertexArray[i + 1].y + square[0].y, paint
            ) }
            if (segment == 2) {
                var angle = src.player.angle - 1.57f
                var t1 = vertex(src.vertexArray[i].x - src.player.Pos.x,
                    src.vertexArray[i].y - src.player.Pos.y)
                var t2 = vertex(src.vertexArray[i + 1].x - src.player.Pos.x,
                    src.vertexArray[i + 1].y - src.player.Pos.y)

                var tz1 = t1.x * cos(angle) + t1.y * sin(angle)
                var tz2 = t2.x * cos(angle) + t2.y * sin(angle)
                t1.x = t1.x * sin(angle) - t1.y * cos(angle)
                t2.x = t2.x * sin(angle) - t2.y * cos(angle)
                map.drawLine((50 - t1.x) + square[0].x,
                    50 - tz1 + square[0].y,
                    50 - t2.x + square[0].x,
                    50 - tz2 + square[0].y, paint)
            }
        }
    }
    paint.setColor(Color.argb(0xff, 0xff, 0xff, 0xff))
    if (segment == 1)
    map.drawLine(200 + src.player.Pos.x, 45.0f + (size.y / 3) * (segment - 1) + src.player.Pos.y,
        200 + src.player.Pos.x + (cos(src.player.angle) * 100),
        45.0f + (size.y / 3) * (segment - 1) + src.player.Pos.y + (sin(src.player.angle) * 100), paint
    )
    if (segment == 2)
        map.drawLine(200 + 50.0f, 45.0f + (size.y / 3) * (segment - 1) + 50.0f,
            200 + 150.0f,
            45.0f + (size.y / 3) * (segment - 1) + 50.0f, paint
        )
    paint.setColor(Color.argb(0xff, 0xaa, 0xaa, 0xaa))
    if (segment == 1)
        map.drawPoint(200 + src.player.Pos.x, 45.0f + (size.y / 3) * (segment - 1) + src.player.Pos.y, paint)
    if (segment == 2)
        map.drawPoint(200 + 50.0f, 45.0f + (size.y / 3) * (segment - 1) + 50, paint)
}

fun DrawLastSegment(size: Point, map: Canvas, src: objDrawMap) {
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.setStrokeWidth(15.0f)
    var y_plas = 45.0f + (size.y / 3) * 2 + 200
    paint.setColor(Color.BLUE)
    for (i in 0..src.vertexArray.size - 2) {
        if (isDrawableVertex(src.vertexArray[i], size) && isDrawableVertex(
                src.vertexArray[i + 1],
                size
            )
        ) {
            var angle = src.player.angle
            var t1 = vertex(
                src.vertexArray[i].x - src.player.Pos.x,
                src.vertexArray[i].y - src.player.Pos.y
            )
            var t2 = vertex(
                src.vertexArray[i + 1].x - src.player.Pos.x,
                src.vertexArray[i + 1].y - src.player.Pos.y
            )

            var tz1 = t1.x * cos(angle) + t1.y * sin(angle)
            var tz2 = t2.x * cos(angle) + t2.y * sin(angle)
            t1.x = t1.x * sin(angle) - t1.y * cos(angle)
            t2.x = t2.x * sin(angle) - t2.y * cos(angle)
            var i1: vertex = vertex(0.0f, 0.0f)
            var i2: vertex = vertex(0.0f, 0.0f)
            if (tz1 > 0 || tz2 > 0)
//            ' If the line crosses the player's viewplane, clip it.
            Intersect(t1.x, tz1, t2.x, tz2, -0.0001f,0.0001f, -20.0f,5.0f, i1)
            Intersect(t1.x,tz1, t2.x,tz2,  0.0001f,0.0001f,  20.0f, 5.0f, i2)
            if (tz1 <= 0) { if (i1.y > 0) {t1.x = i1.x; tz1 = i1.y } else t1.x =i2.x; tz1=i2.y }
            if (tz2 <= 0) {if (i1.y > 0) { t2.x = i1.x; tz2 = i1.y} else t2.x = i2.x; tz2=i2.y}

            var x1 = -t1.x * 16 / tz1
            var y1a = -50 / tz1
            var y1b = 50 / tz1
            var x2 = -t2.x * 16 / tz2
            var y2a = -50 / tz2
            var y2b = 50 / tz2
            map.drawLine(50 + x1, 50 + y1a + y_plas, 50 + x2, 50 + y2a + y_plas, paint)
            map.drawLine(50 + x1, 50 + y1b + y_plas, 50 + x2, 50 + y2b + y_plas, paint)
            map.drawLine(50 + x1, 50 + y1a + y_plas, 50 + x1, 50 + y1b + y_plas, paint)
            map.drawLine(50 + x2, 50 + y2a + y_plas, 50 + x2, 50 + y2b + y_plas, paint)
        }
    }
}

fun DrawMap(size: Point, map: Canvas, src: objDrawMap) {
    drawScreenSegment(size, map, src, 1)
    drawScreenSegment(size, map, src, 2)
    drawScreenSegment(size, map, src, 3)
    DrawLastSegment(size, map, src)
}

fun cross(x1: Float,y1: Float, x2: Float,y2: Float): Float {
    return x1*y2 - y1*x2
}

fun Intersect(x1: Float,y1: Float, x2: Float,y2: Float, x3: Float,y3: Float, x4: Float,y4: Float, xy: vertex) {

    xy.x = cross(x1,y1, x2,y2)
    xy.y = cross(x3,y3, x4,y4)
    var det = cross(x1-x2, y1-y2, x3-x4, y3-y4)
    xy.x = cross(xy.x, x1-x2, xy.y, x3-x4) / det
    xy.y = cross(xy.x, y1-y2, xy.y, y3-y4) / det
}
