package com.example.mapbuildtry

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import kotlin.math.abs

class ObjSectors(bm: Bitmap, private val size: Point, context: Context) {
    var bm: Bitmap
    private var array: Array<Sector>
    private val pixels = IntArray(size.x * size.y)
    var round: IntArray = IntArray(9 * 9)

    init {
        var roundSrc = BitmapFactory.decodeResource(context.resources, R.drawable.round)
        var round: Bitmap
        round = Bitmap.createScaledBitmap(roundSrc, 9, 9, false)
        round.getPixels(this.round, 0, 9, 0, 0,
            9, 9)
        val nullPoint = Vertex(0.0f, 0.0f)
        this.array = arrayOf(Sector(size, nullPoint, nullPoint))
        this.array[0].isFinished = true
        this.bm = Bitmap.createScaledBitmap(bm, size.x, size.y, false)
        this.bm.setPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
    }

    private fun setPix() {
        bm.setPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
    }

    private fun setRound(point: Vertex): Boolean {
        if (point.x - 5 < 0 || point.y - 5 < 0)
            return false
        var xy = Point(point.x.toInt(), point.y.toInt())
        for (i in -4..4) {
            for (j in -4..4)
                pixels[xy.x + i + (xy.y + j) * size.x] =
                    round[i + 4 + (j + 4) * 9]
        }
        return true
    }

    private fun finishLine(sector: Int) {
        if (array[sector].last().noEnd()) {
            delPix(); setPix(); return
        }
        drawWall(array[sector].points.last(), Color.GREEN)
        if (array[sector].isFinishedSegment()) {
            array[sector].isFinished = true
            correctFigure(sector)
        }
        else if (!array[sector].last().noEnd())
            array[sector].points += ObjLine(size,
                array[sector].last().end(), Vertex(-1.0f, -1.0f))
    }

    fun drawLine(point: Vertex, isUp: Boolean) {
        var sector = -1
        while (++sector < array.size) {
            if (!array[sector].isFinished) { break }
        }
        if (sector == array.size) {
            array += getCloseCoordinate(point); return
        }
        if (isUp) {
            if (sector < array.size) { finishLine(sector); return }
            else return
        }
        array[sector].changeLastPoint(point)
        if (!drawWall(array[sector].points.last(), 0xff50ffff.toInt()))
            array[sector].changeLastPoint(Vertex(-1.0f, -1.0f))
    }

    private fun foundCloseCoordinate(x: Int, y: Int, res: Sector): Boolean {
        if (pixels[x + y * size.x] != Color.GREEN) { return false }
        res.points[0].start = Vertex(x.toFloat(), y.toFloat())
        res.points[0].isFinishedStart = true
        return true
    }

    private fun getCloseCoordinate(point: Vertex): Sector {
        for (pix in 0 until size.x * size.y) { pixels[pix] = 0x0 }
        var k = 0
//        while (k < array.size - 1) {
//            for (j in array[k].points.indices) {
//                array[k].points[j].fillPixArr(pixels, Color.GREEN)
//                setRound(array[k].points[j].end())
//                setRound(array[k].points[j].start)
//            }
//            k++
//        }
////        if (k > array.size - 2) {
//            for (j in array[k].points.indices - 1) {
//                array[k].points[j].fillPixArr(pixels, Color.GREEN)
//                setRound(array[k].points[j].end())
//                setRound(array[k].points[j].start)
//            }
////        }
        val index = Point(point.x.toInt(), point.y.toInt())
        val res = Sector(size, point, Vertex(-1.0f, -1.0f))
        for (i in -10..10) {
            if (index.x + i < 0 || index.y + i < 0) { continue }
            if (foundCloseCoordinate(index.x + i,  index.y, res) ||
                foundCloseCoordinate(index.x,  index.y + i, res) ||
                foundCloseCoordinate(index.x + i,  index.y + i, res))
                { break }
        }
        setRound(res.points[0].start)
        return res
    }

    private fun correctFigure(i: Int) {
        var delete = !array[i].points[0].isFinishedStart
        var res: Array<ObjLine> = emptyArray()
        for (pix in 0 until size.x * size.y) { pixels[pix] = 0x0 }
        for (j in array[i].points.indices) {
            if (array[i].points[j].start == array[i].last().end())
                delete = false
            if (!delete) { res += array[i].points[j] }
        }
        array[i].points = res
        for (k in array.indices) {
            for (j in array[k].points.indices) {
                array[k].points[j].fillPixArr(pixels, Color.GREEN)
                setRound(array[k].points[j].end())
                    setRound(array[k].points[j].start)
            }
        }
        setPix()
    }

    private fun delPix() {
        for (i in 0 until size.x * size.y) {
            if ((pixels[i] == Color.BLACK) or (pixels[i] == Color.RED)
                or (pixels[i] == 0xff50ffff.toInt()))
                pixels[i] = 0x0
        }
    }

    private fun drawWall(line: ObjLine, color: Int): Boolean {
        val prev = Vertex(line.end().x, line.end().y)
        delPix()
        line.fillPixArr(pixels, color)
        if (abs(line.start.x - line.end().x) < 10 && abs(line.start.y - line.end().y) < 10) {
            delPix()
            val redLine = ObjLine(size, Vertex(line.start.x, line.start.y), prev)
            redLine.fillPixArr(pixels, Color.RED)
            this.bm.setPixels(pixels, 0, size.x, 0, 0,
                size.x, size.y)
            return false
        }
        setRound(line.end())
        this.bm.setPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
        return true
    }
}

class Sector(size: Point, start: Vertex, end: Vertex) {
    var points = arrayOf(ObjLine(size, start, end))
    var isFinished: Boolean = false

    fun last(): ObjLine {
        val size = points.size
        return points[size - 1]
    }

    fun changeLastPoint(point: Vertex) {
        val size = points.size
        points[size - 1].changeEnd(point)
    }

    fun isFinishedSegment(): Boolean {
        if (!last().isFinished) { return false }
        //а теперь пробегись по точкам и измени дату по часовой стрелке!
        if (points[0].isFinishedStart) { return true } // проверить, создаётся ли сектор
        if (checkIfCross(last().end())) { return true }
        //изменить дату
        points += ObjLine(points[0].size,
            Vertex(points[0].start.x, points[0].start.y),
            Vertex(-1.0f, -1.0f))
        points[0].isFinishedStart = true
        return false
    }

    private fun checkIfCross(check: Vertex): Boolean {
        var res: Array<ObjLine> = emptyArray()
        for (i in 0 until points.size - 1) {
            if (points[i].checkIfCross(check)) {
                res += ObjLine(points[i].size, points[i].start, check)
                res += ObjLine(points[i].size, check, points[i].end())
                for (j in i + 1 until points.size)
                    res += points[j]
                points = res
                return true //стереть все
            }
            res += points[i]
            //невходящие во множество точки
        }
        return false //если нет, перевернуть все точки, сделать первую последней,
        //а у последней поставить isStartFinished = true
    }
}