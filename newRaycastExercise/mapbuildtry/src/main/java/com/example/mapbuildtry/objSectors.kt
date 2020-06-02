package com.example.mapbuildtry

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import java.lang.Float

class objSectors {
    var bm: Bitmap
    var size: Point
    var maze: Array<maze>
    var array: Array<sector>
    constructor(bm: Bitmap, size: Point) {
        this.size = size
        this.maze = arrayOf(maze(-1,
            vertex(0.0f, 0.0f), vertex(0.0f, 0.0f)))
        this.array = arrayOf(sector(vertex(0.0f, 0.0f)))
        this.array[0].isFinished = true
        this.bm = Bitmap.createScaledBitmap(bm, size.x, size.y, false)
        var src = IntArray((size.x * size.y))
        this.bm.setPixels(src, 0, size.x, 0, 0,
            size.x, size.y)
    }
    fun RemoveLine(point: vertex) {

    }

    fun DrawLine(point: vertex, isUp: Boolean) {
        // 1) дойти до первого сектора с isFinished == false
        // 2) отрисовать линию, проверить, не замкнулся ли сектор
        // 3) если сектор не замкнулся, добавить точку в дату,
        // отрисовать линию
        // 4) если сектор замкнулся (отрисованная линия пересекла стену),
        // вызвать команду Finish
        // 5) return

        var i = 0
        while (i < this.array.size) {
            if (this.array[i].isFinished == false)
                break
            i++
        }
        if (i >= this.array.size) { //значит isFinished везде == true
            this.array += sector(point)
            this.bm.setPixel(point.x.toInt(), point.y.toInt(), 0xffffff00.toInt())
            return
        }
        if (this.array[i].points.size > 1 && isUp == true) {
           this.array[i].delPoint(this.array[i].points[this.array[i].points.size - 1])
        }
        else if (this.array[i].points.size > 1) {
            this.array[i].points += drawWall(
                this.array[i].points[this.array[i].points.size - 2],
                this.array[i].points[this.array[i].points.size - 1],
                0xff00ff00.toInt()
            )
            if (!(this.array[i].points[this.array[i].points.size - 1].equals(point)))
                array[i].isFinished = true
            return
        }
        this.array[i].points += drawWall(
            this.array[i].points[this.array[i].points.size - 1],
            point,
            0xffff0000.toInt()
        )
        this.array[i].isFinished = false

        // от последней точки сектора отрисовать линию до новой точки

        // ??? есть ли ещё кейсы?
    }

    fun drawWall(one: vertex, two: vertex, color: Int): vertex {
        var pixels = IntArray(this.size.x * this.size.y)
        var res: vertex
        this.bm.getPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
        for (i in 0..((size.x - 1) * (size.y - 1))) {
            if ((pixels[i] == Color.BLACK) or (pixels[i] == Color.RED))
                pixels[i] = 0x0
        }
        res = fillPixArr(pixels, one, two, color)
        this.bm.setPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
        return res
    }

    fun Finish() {
        // 1) посмотреть, как замкнулась фигура - не должно остаться
        // линий и точек, не принадлежащих ни одному другому сектору
        //
        // думаю, норм вариант - стирать линии (по точкам) и точки
        // от начала, проверяя, остаётся ли на поле заданная точка;
        // как только точка перестаёт существовать, перестать стирать дату
        // и добавить точку (если её ещё нет) в дату
        //
        // 2) обновить дату точек - они должны быть расположены по часовой стрелке,
        // первая точка - самая верхняя левая
        //
        // 3) закрасить все пиксели внутри сектора
        //
        //4) isFinished для выбранного сектора = true
    }
    fun checkCol(src: IntArray, point: Point, size: Point, color: Int) {
        if (src[point.x + point.y * size.x] != 0xff00ff00.toInt())
            src[point.x + point.y * size.x] = color
    }
    fun FatLine(pixels: IntArray, point: Point, size: Point, color: Int) {
        checkCol(pixels, point, size, color)
        checkCol(pixels, Point(point.x + 1, point.y), size, color)
        checkCol(pixels, Point(point.x, point.y + 1), size, color)
        checkCol(pixels, Point(point.x + 1, point.y + 1), size, color)
    }

    fun NextCoord(error: Int, diff: Int, isX: Boolean, dir: Point, new: Point, delta: Int): Int {
        var newErr = error + delta
        if (newErr * 2 >= diff) {
            if (isX == false) new.x += dir.x else new.y += dir.y
            if (isX == false) new.y += dir.y else new.x += dir.x
            return (newErr - diff)
        }
        if (isX == false) new.y += dir.y else new.x += dir.x
        return newErr
    }

    fun IsThereWall(pixels: IntArray, point: Point, color: Int, begin: vertex, dir: Int, isX: Boolean): Boolean {
        if (pixels[(point.x + point.y * this.size.x)] == Color.GREEN && color == Color.RED) {
            if ((isX == true && (point.x > begin.x + 1 && dir > 0) or (point.x < begin.x - 1 && dir < 0)) or
            (isX == false && (point.y > begin.y + 1 && dir > 0) or (point.y < begin.y - 1 && dir < 0)))
                return true
        }
        return false
    }

    fun Bresenham(isX: Boolean, start: vertex, nextPix: Point, two: Point, pixels: IntArray,
             color: Int, delta: Point) {
        var error = 0
        var dir = Point( (if(two.x > start.x) 1 else -1), if (two.y > start.y) 1 else -1)
        var startCoord = if (isX == false) nextPix.y else nextPix.x
        var end = if (isX == false) two.y else two.x
        while (startCoord != end) {
            if (isX == false) startCoord = nextPix.y else startCoord = nextPix.x
            if (IsThereWall(pixels, nextPix, color, start, if (isX == false) dir.y else dir.x, isX)) break
            FatLine(pixels, nextPix, this.size, color)
            error = if (!isX) NextCoord(error, delta.y, isX, dir, nextPix, delta.x)
            else NextCoord(error, delta.x, isX, dir, nextPix, delta.y)
        }
    }

    fun fillPixArr(pixArray: IntArray, start: vertex, end: vertex, color: Int): vertex {
        //drawing by Bresenham's line algorithm
        var nextPix = Point(start.x.toInt(), start.y.toInt())
        var two = Point(end.x.toInt(), end.y.toInt())
        var delta = Point((if (two.x - nextPix.x < 0) nextPix.x - two.x else two.x - nextPix.x),
            if (two.y - nextPix.y < 0) nextPix.y - two.y else two.y - nextPix.y)
        if (delta.y < delta.x) {
            Bresenham(true, start, nextPix, two, pixArray, color, delta)
//            while (nextPix.x != two.x) {
//                if (IsThereWall(pixels, nextPix, color, start, direction.x, true)) break
//                FatLine(pixels, nextPix, this.size, color)
//                error = NextCoord(error, delta.x, true, direction, nextPix, delta.y)
//            }
        } else {
            Bresenham(false, start, nextPix, two, pixArray, color, delta)
//            while (nextPix.y != two.y) {
//                if (IsThereWall(pixels, nextPix, color, start, direction.y, false)) break
//                FatLine(pixels, nextPix, size, color)
//                error = NextCoord(error, delta.y, false, direction, nextPix, delta.x)
//            }
        }
        return vertex(nextPix.x.toFloat(), nextPix.y.toFloat())
    }
}

class maze {
    var borders: borders
    var sector: Int
    constructor(i: Int, vertex1: vertex, vertex2: vertex)
    {
        sector = i
        borders = borders(vertex1, vertex2)
    }
}

class sector {
    var points: Array<vertex>
    var isFinished: Boolean
    constructor(vertex: vertex) {
        this.isFinished = false
        this.points = arrayOf(vertex(vertex.x, vertex.y))
    }

    fun addPoint(vertex: vertex) {
        this.points += vertex
    }

    fun delPoint(vertex: vertex) {
        var res: Array<vertex> = emptyArray()
        for (i in 0..this.points.size - 1) {
            if (this.points[i].equals(vertex)) {
                this.isFinished = false
//                res = this.points.drop(i).toTypedArray()
                continue
            }
            else
                res += this.points[i]
        }
        this.points = res
    }

    fun GetFinalOrder (index: Int) {
        var howMuch = 1
        var i = index
        var direction = 0
        var newOrder = arrayOf(this.points[index])
        //1) найти направление по часовой
        if (index > 0) {
            if (this.points[index].x > this.points[index - 1].x)
                direction = 1
            else if ((this.points[index].x == this.points[index - 1].x)
                and (this.points[index].y < this.points[index - 1].y))
                direction = 1
            else
                direction = -1
        } else {
            if (this.points[index + 1].x > this.points[index].x)
                direction = 1
            else if ((this.points[index + 1].x == this.points[index].x)
                and (this.points[index + 1].y < this.points[index].y))
                direction = 1
            else
                direction = -1
        }
        while (howMuch != this.points.size) {
            if (i + direction < 0) {
                i = this.points.size - 1
            } else if (index + direction >= this.points.size) {
                i = 0
            } else {
                i = i + direction
            }
            newOrder += this.points[i]
            howMuch++
        }
        this.points = newOrder
    }

    fun Finish() {
        //1) find the toppest spot
        var keepSearching = false // if
        var index = 0
        this.isFinished = true
        var theTopest: vertex =  vertex(0.0f, Float.POSITIVE_INFINITY)
        for (i in 0..this.points.size - 1)
        {
            if (this.points[i].y < theTopest.y) {
                theTopest = this.points[i]
                index = i
                keepSearching = false
            }
            else if (this.points[i].y == theTopest.y)
                keepSearching = true
        }
        if (keepSearching == false)
            return GetFinalOrder(index)
        for (i in 0..this.points.size - 1) {
            if ((this.points[i].y == theTopest.y) && this.points[i].x < theTopest.x)
                theTopest = this.points[i]; index = i
        }
        GetFinalOrder(index)
    }
}