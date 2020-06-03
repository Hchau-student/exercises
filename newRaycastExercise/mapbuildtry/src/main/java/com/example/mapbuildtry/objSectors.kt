package com.example.mapbuildtry

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import kotlin.math.abs

class ObjSectors(bm: Bitmap, private val size: Point) {
    var bm: Bitmap
    private var array: Array<Sector>
    val pixels = IntArray(size.x * size.y)

    init {
        var nullPoint1 = Vertex(0.0f, 0.0f)
        var nullPoint2 = Vertex(0.0f, 0.0f)
        this.array = arrayOf(Sector(size, nullPoint1, nullPoint2))
        this.array[0].isFinished = true
        this.bm = Bitmap.createScaledBitmap(bm, size.x, size.y, false)
        this.bm.setPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
    }

    fun drawLine(point: Vertex, isUp: Boolean) {
        var i = 0
        while (i < array.size) {
            if (!array[i].isFinished)
                break
            i++
        }
        if (i == array.size) { //значит isFinished везде == true
            array += Sector(size, point, Vertex(-1.0f, -1.0f))
            if (bm.getPixel(point.x.toInt(), point.y.toInt()) != Color.GREEN)
                bm.setPixel(point.x.toInt(), point.y.toInt(), Color.YELLOW)
            return
        }
        if (isUp && i < array.size) {
            if (array[i].last().noEnd()) {
                for (i in 0 until size.x * size.y) {
                    if ((pixels[i] == Color.BLACK) or (pixels[i] == Color.RED)
                        or (pixels[i] == 0xff50ffff.toInt()))
                        pixels[i] = 0x0
                }
                this.bm.setPixels(pixels, 0, size.x, 0, 0,
                    size.x, size.y)
                return
            }
            drawWall(array[i].points.last(), Color.GREEN)
            if (array[i].isFinishedSegment()) {
                array[i].isFinished = true
                correctFigure(i)
            }
            else if (!array[i].last().noEnd())
                array[i].points += ObjLine(size,
                    array[i].last().end(), Vertex(-1.0f, -1.0f))
        }
        if (!isUp) {
            array[i].changeLastPoint(point)
            if (!drawWall(array[i].points.last(), 0xff50ffff.toInt()))
                array[i].changeLastPoint(Vertex(-1.0f, -1.0f))
        }
    }

    private fun correctFigure(i: Int) {
        var delete = !array[i].points[0].isFinishedStart
        var res: Array<ObjLine> = emptyArray()
        for (i in 0 until size.x * size.y) { pixels[i] = 0x0 }
        for (j in array[i].points.indices) {
            if (array[i].points[j].start == array[i].last().end())
                delete = false
            array[i].points[j].redrawLine(pixels)
            if (!delete)
                res += array[i].points[j]
        }
        array[i].points = res
        for (k in array.indices) {
            for (j in array[k].points.indices)
                array[k].points[j].fillPixArr(pixels, Color.GREEN)
        }
        this.bm.setPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
    }

    private fun drawWall(line: ObjLine, color: Int): Boolean {
        val res: Boolean
        val prev = Vertex(line.end().x, line.end().y)
        for (i in 0 until size.x * size.y) {
            if ((pixels[i] == Color.BLACK) or (pixels[i] == Color.RED)
                or (pixels[i] == 0xff50ffff.toInt()))
                pixels[i] = 0x0
        }
        res = line.fillPixArr(pixels, color)
        if (abs(line.start.x - line.end().x) < 10 && abs(line.start.y - line.end().y) < 10) {
            for (i in 0 until size.x * size.y) {
                if ((pixels[i] == Color.BLACK) or (pixels[i] == Color.RED) or
                        (pixels[i] == 0xff50ffff.toInt()))
                    pixels[i] = 0x0
            }
            var redLine = ObjLine(size, Vertex(line.start.x, line.start.y), prev)
            redLine.fillPixArr(pixels, Color.RED)
            this.bm.setPixels(pixels, 0, size.x, 0, 0,
                size.x, size.y)
            return false
        }
        this.bm.setPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
        return true
    }

    fun finishSector(point: Vertex, res: Vertex) {
//        if (res.x )
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
}

//class Maze(i: Int, Vertex1: vertex, vertex2: vertex) {
//    private var borders: Borders = Borders(vertex1, vertex2)
//    private var sector: Int = i
//}

class Sector(size: Point, start: Vertex, end: Vertex) {
    var points: Array<ObjLine> = arrayOf(ObjLine(size, start, end))
    var isFinished: Boolean = false
    var size = points.size

    fun last(): ObjLine {
//        if (points.size > 0)
        var size = points.size
        return points[size - 1]
    }

    fun changeLastPoint(point: Vertex) {
        var size = points.size
        points[size - 1].changeEnd(point)
    }

    fun isFinishedSegment(): Boolean {
        if (!last().isFinished)
            return false
        //а теперь пробегись по точкам и измени дату по часовой стрелке!
        if (points[0].isFinishedStart == true)
            return true
        if (checkIfSelfCross(last().end()))
            return true
        //изменить дату
        points += ObjLine(points[0].size,
            Vertex(points[0].start.x, points[0].start.y),
            Vertex(-1.0f, -1.0f))
        points[0].isFinishedStart = true
        return false
    }

    fun checkIfSelfCross(check: Vertex): Boolean {
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
//    private fun getFinalOrder (index: Int) {
//        var howMuch = 1
//        var i = index
//        var newOrder = arrayOf(this.points[index])
//        //1) найти направление по часовой
//        val direction = if (index > 0) {
//            when {
//                this.points[index].x > this.points[index - 1].x -> 1
//                (this.points[index].x == this.points[index - 1].x)
//                        and (this.points[index].y < this.points[index - 1].y) -> 1
//                else -> -1
//            }
//        } else {
//            when {
//                this.points[index + 1].x > this.points[index].x -> 1
//                (this.points[index + 1].x == this.points[index].x)
//                        and (this.points[index + 1].y < this.points[index].y) -> 1
//                else -> -1
//            }
//        }
//        while (howMuch != this.points.size) {
//            i = when {
//                i + direction < 0 -> this.points.size - 1
//                index + direction >= this.points.size -> 0
//                else -> i + direction
//            }
//            newOrder += this.points[i]
//            howMuch++
//        }
//        this.points = newOrder
//    }



//    fun finish() {
//        var keepSearching = false
//        var index = 0
//        this.isFinished = true
//        var theTop =  vertex(0.0f, Float.POSITIVE_INFINITY)
//        for (i: Int in points.indices)
//        {
//            if (points[i].y < theTop.y) {
//                theTop = points[i]
//                index = i
//                keepSearching = false
//            }
//            else if (points[i].y == theTop.y)
//                keepSearching = true
//        }
//        if (!keepSearching)
//            return getFinalOrder(index)
//        for (i in points.indices) {
//            if ((points[i].y == theTop.y) && points[i].x < theTop.x)
//                theTop = this.points[i]; index = i
//        }
//        getFinalOrder(index)
//    }
}