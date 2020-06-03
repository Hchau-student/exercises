package com.example.mapbuildtry

import android.graphics.Color
import android.graphics.Point

class ObjDrawLine (private var size: Point, private var start: Vertex, private var end: Vertex) {
    private var nextPix: Point = Point(this.start.x.toInt(), this.start.y.toInt())
    private var two: Point = Point(this.end.x.toInt(), this.end.y.toInt())
    private var isFinished = false
    private var delta: Point = Point(
        (if (two.x - nextPix.x < 0) nextPix.x - two.x else two.x - nextPix.x),
        if (two.y - nextPix.y < 0) nextPix.y - two.y else two.y - nextPix.y
    )
    private var isX: Boolean = (delta.y < delta.x)

    private fun checkCross(src: IntArray, point: Point, color: Int) {
        if ((point.x >= size.x) || (point.y >= size.y)) return
        if (src[point.x + point.y * size.x] != Color.GREEN)
            src[point.x + point.y * size.x] = color
    }

    private fun fatLine(pixels: IntArray, color: Int) {
        checkCross(pixels, nextPix, color)
        checkCross(pixels, Point(nextPix.x + 1, nextPix.y), color)
        checkCross(pixels, Point(nextPix.x, nextPix.y + 1), color)
        checkCross(pixels, Point(nextPix.x + 1, nextPix.y + 1), color)
    }

    private fun nextPoint(error: Int, diff: Int, dir: Point, intDelta: Int): Int {
        val newErr = error + intDelta
        if (newErr * 2 >= diff) {
            if (!isX) nextPix.x += dir.x else nextPix.y += dir.y
            if (!isX) nextPix.y += dir.y else nextPix.x += dir.x
            return (newErr - diff)
        }
        if (!isX) nextPix.y += dir.y else nextPix.x += dir.x
        return newErr
    }

    private fun isThereWall(pixels: IntArray, color: Int, dir: Int): Boolean {
        if ((nextPix.x > size.x - 1) || (nextPix.y > size.y - 1)) return true
        if ((nextPix.x < 0) || (nextPix.y < 0)) return true
        if (pixels[(nextPix.x + nextPix.y * size.x)] == Color.GREEN
            && color == Color.RED) {
            if (isX && (nextPix.x > start.x + 1 && dir > 0)
                or (nextPix.x < start.x - 1 && dir < 0))
                return true
            if (!isX && (nextPix.y > start.y + 1 && dir > 0)
                or (nextPix.y < start.y - 1 && dir < 0))
                return true
        }
        return false
    }

    private fun bresenham(pixels: IntArray, color: Int): Boolean {
        var error = 0
        val dir = Point((if (two.x > this.start.x) 1 else -1), if (two.y > this.start.y) 1 else -1)
        var startInt: Int = if (!isX) this.nextPix.y else this.nextPix.x
        val endInt: Int = if (!isX) two.y else two.x
        while (startInt != endInt) {
            startInt = if (!isX) this.nextPix.y else this.nextPix.x
            if (isThereWall(pixels, color, if (!isX) dir.y else dir.x)) return true
            fatLine(pixels, color)
            error = if (!isX) nextPoint(error, delta.y, dir, delta.x)
            else nextPoint(error, delta.x, dir, delta.y)
        }
        return false
    }

    fun fillPixArr(pixArray: IntArray, color: Int): Vertex {
        //drawing by Bresenham's line algorithm
//        if (isUp == false)
//            this.end
        isFinished = bresenham(pixArray, color)
//        if ((nextPix.x > end.x && start.x > end.x) or (nextPix.x < end.x && start.x < end.x)) {
            return Vertex(nextPix.x.toFloat(), nextPix.y.toFloat())
//        }
//        return end
    }
}
