package com.example.mapbuildtry

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point

class ObjSectors(bm: Bitmap, private var size: Point) {
    var bm: Bitmap
//    private var maze: Array<Maze> = arrayOf(Maze(-1,
//        vertex(0.0f, 0.0f), vertex(0.0f, 0.0f)))
    private var array: Array<Sector> = arrayOf(Sector(Vertex(0.0f, 0.0f)))

    init {
        this.array[0].isFinished = true
        this.array[0].addPoint(Vertex(0.0f, 0.0f))
        this.bm = Bitmap.createScaledBitmap(bm, size.x, size.y, false)
        val src = IntArray((size.x * size.y))
        this.bm.setPixels(src, 0, size.x, 0, 0,
            size.x, size.y)
    }
//    fun removeLine(point: vertex) {
//
//    }

    fun drawLine(point: Vertex, isUp: Boolean) {
        var i = 0
        while (i < array.size) {
            if (!array[i].isFinished)
                break
            i++
        }
        if (i == array.size) { //значит isFinished везде == true
//            array += Sector(Vertex(0.0f, 0.0f))
            array += Sector(point)
//            array[i].addPoint(point)
            bm.setPixel(array[i].pointFromEnd(0).x.toInt(), array[i].pointFromEnd(  0).y.toInt(), Color.YELLOW)
            return
        }
        if (isUp && array[i].size > 1) {
            (drawWall(
                array[i].pointFromEnd(0),
                array[i].pointFromEnd(1),
                Color.GREEN
            ))
            array[i].addPoint(array[0].pointAt(1))
            if (array[0].points[1] != array[0].points[0])
                this.array[i].isFinished = true
        }
        if (!isUp) { //удаляю точку, если нажатиe не было окончено
            if (array[i].size > 1)
                array[i].delPoint(array[i].pointFromEnd(0))
            array[0].points[0] = (drawWall(array[i].pointFromEnd(0), point, Color.RED))
            array[0].points[1] = point
            array[i].addPoint(Vertex(array[0].points[0].x, array[0].points[0].y))
            this.array[i].isFinished = false
//            if (array[i].pointFromEnd(0) != point)
//                array[i].isFinished = true
        }
    }

    private fun drawWall(one: Vertex, two: Vertex, color: Int): Vertex {
        val pixels = IntArray(size.x * size.y)
        val res: Vertex
        val objLine = ObjDrawLine(size, one, two)
        this.bm.getPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
        for (i in 0 until size.x * size.y) {
            if ((pixels[i] == Color.BLACK) or (pixels[i] == Color.RED))
                pixels[i] = 0x0
        }
        res = objLine.fillPixArr(pixels, color)
        this.bm.setPixels(pixels, 0, size.x, 0, 0,
            size.x, size.y)
        return res
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

class Sector(vertex: Vertex) {
    var points: Array<Vertex> = arrayOf(Vertex(vertex.x, vertex.y))
    var isFinished: Boolean = false
    var size = points.size

    fun addPoint(vertex: Vertex) {
        this.points += vertex
        this.size = points.size
    }

    fun pointFromEnd(i: Int): Vertex {
        return points[this.size - i - 1]
    }

    fun pointAt(i: Int): Vertex {
        return points[i]
    }

    fun delPoint(vertex: Vertex) {
        var res: Array<Vertex> = emptyArray()
        for (i in points.indices) {
            if (this.points[i].equals(vertex)) {
                this.isFinished = false
//                res = this.points.drop(i).toTypedArray()
                continue
            }
            else
                res += this.points[i]
        }
        this.points = res
        this.size = points.size
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