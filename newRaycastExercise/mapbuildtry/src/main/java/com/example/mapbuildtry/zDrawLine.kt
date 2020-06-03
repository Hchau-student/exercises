package com.example.mapbuildtry

import android.graphics.Color
import android.graphics.Point
import kotlin.math.abs

class ObjLine (var size: Point, var start: Vertex, private var end: Vertex) {
    private var nextPix: Point = Point(this.start.x.toInt(), this.start.y.toInt())
    private var two: Point = Point(this.end.x.toInt(), this.end.y.toInt())
    var isFinished = false
    var isFinishedStart = false
    private var delta: Point = Point(
        (if (two.x - nextPix.x < 0) nextPix.x - two.x else two.x - nextPix.x),
        if (two.y - nextPix.y < 0) nextPix.y - two.y else two.y - nextPix.y
    )
    private var isX: Boolean = (delta.y < delta.x)

    private fun checkCross(src: IntArray, point: Point, color: Int) {
        if ((point.x >= size.x) || (point.y >= size.y)) return
        if (src[point.x + point.y * size.x] != Color.GREEN) {
            src[point.x + point.y * size.x] = color
        }
        else if (color == 0x0 && src[point.x + point.y * size.x] == Color.GREEN) {
            src[point.x + point.y * size.x] = color
        }
    }

    private fun fatLine(pixels: IntArray, color: Int) {
        checkCross(pixels, nextPix, color)
        if (color == 0x0) {
            checkCross(pixels, Point(nextPix.x + 1, nextPix.y), color)
            checkCross(pixels, Point(nextPix.x, nextPix.y + 1), color)
            checkCross(pixels, Point(nextPix.x + 1, nextPix.y + 1), color)
            checkCross(pixels, Point(nextPix.x + 2, nextPix.y), color)
            checkCross(pixels, Point(nextPix.x, nextPix.y + 2), color)
            checkCross(pixels, Point(nextPix.x + 2, nextPix.y + 2), color)
            checkCross(pixels, Point(nextPix.x - 1, nextPix.y), color)
            checkCross(pixels, Point(nextPix.x, nextPix.y - 1), color)
            checkCross(pixels, Point(nextPix.x - 1, nextPix.y - 1), color)
        }
    }

    fun end(): Vertex = end

    fun noEnd(): Boolean = end.x == -1.0f

    fun changeEnd(newEnd: Vertex) { end = newEnd }

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
        if (isX && abs(nextPix.x - start.x) <= 5)
            return false
        if (!isX && abs(nextPix.y - start.y) <= 5)
            return false
        if (color == 0xff50ffff.toInt()) {
            if (pixels[(nextPix.x + nextPix.y * size.x)] == Color.GREEN ||
                pixels[nextPix.x + 1 + nextPix.y * size.x] == Color.GREEN ||
                pixels[nextPix.x - 1 + nextPix.y * size.x] == Color.GREEN ||
                pixels[nextPix.x + (nextPix.y + 1) * size.x] == Color.GREEN ||
                pixels[nextPix.x + (nextPix.y - 1) * size.x] == Color.GREEN)
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
            if (color != Color.RED && isThereWall(pixels, color, if (!isX) dir.y else dir.x)) return true
            fatLine(pixels, color)
            error = if (!isX) nextPoint(error, delta.y, dir, delta.x)
            else nextPoint(error, delta.x, dir, delta.y)
        }
        return false
    }

    fun fillPixArr(pixArray: IntArray, color: Int): Boolean {
        //drawing by Bresenham's line algorithm
        nextPix = Point(this.start.x.toInt(), this.start.y.toInt())
        two = Point(end.x.toInt(), end.y.toInt())
        delta = Point(
            (if (two.x - nextPix.x < 0) nextPix.x - two.x else two.x - nextPix.x),
            if (two.y - nextPix.y < 0) nextPix.y - two.y else two.y - nextPix.y
        )
        isX = (delta.y < delta.x)
        if (pixArray[nextPix.x + nextPix.y * size.x] == Color.GREEN) //перед отрисовкой
            //чекнуть, не начинается ли линия на зелёной точке
            isFinishedStart = true
        if (color == Color.RED || color == 0xff50ffff.toInt())
            isFinished = bresenham(pixArray, color) //если мы упираемся в зелёную точку, вернётся true -
        // значит, линия упирается в другой блок
        else
            bresenham(pixArray, color)
        if (color != Color.GREEN)
            end = Vertex(nextPix.x.toFloat(), nextPix.y.toFloat())
        return false
    }

    fun redrawLine(pixArray: IntArray) {
        fillPixArr(pixArray, 0x0)
    }

    fun checkIfCross(check: Vertex): Boolean {
        nextPix = Point(this.start.x.toInt(), this.start.y.toInt())
        two = Point(end.x.toInt(), end.y.toInt())
        if ((abs(end.x - check.x) < 3) && (abs(end.y - check.y) < 3))
            return false
        var error = 0
        val dir = Point((if (two.x > this.start.x) 1 else -1), if (two.y > this.start.y) 1 else -1)
        var startInt: Int = if (!isX) this.nextPix.y else this.nextPix.x
        val endInt: Int = if (!isX) two.y else two.x
        while (startInt != endInt) {
            if ((abs(nextPix.x - check.x) < 3) && (abs(nextPix.y - check.y) < 3)) {
//                start.x = nextPix.x.toFloat(); start.y = nextPix.y.toFloat()
                isFinishedStart = true
            //мб имеет смысл дорисовать пиксели и замкнуть фигуру?
                return true
            }
            startInt = if (!isX) this.nextPix.y else this.nextPix.x
            error = if (!isX) nextPoint(error, delta.y, dir, delta.x)
            else nextPoint(error, delta.x, dir, delta.y)
        }
        return false
    }
}
