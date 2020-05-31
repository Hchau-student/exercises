package com.example.mapbuildtry

import android.graphics.Bitmap
import android.graphics.Point
import java.lang.Float

class objSectors {
    var bm: Bitmap
    var maze: Array<maze>
    var array: Array<points>
    constructor(bm: Bitmap, size: Point) {
        this.maze = arrayOf(maze(-1,
            vertex(0.0f, 0.0f), vertex(0.0f, 0.0f)))
        this.array = arrayOf(points(vertex(0.0f, 0.0f)))
        this.bm = Bitmap.createScaledBitmap(bm, size.x, size.y, false)
        var src = IntArray((size.x * size.y))
        this.bm.setPixels(src, 0, size.x, 0, 0,
            size.x, size.y)
    }
    fun removeLine(point: vertex) {

    }

    fun DrawLine() {
        // 1) дойти до первого сектора с isFinished == false
        // 2) отрисовать линию, проверить, не замкнулся ли сектор
        // 3) если сектор не замкнулся, добавить точку в дату,
        // отрисовать линию
        // 4) если сектор замкнулся (отрисованная линия пересекла стену),
        // вызвать команду Finish
        // 5) return

        // 1) если сектора с isFinished == false нет, создать новый сектор
        // 2) отрисовать первую точку
        // 3) return

        // ??? есть ли ещё кейсы?
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

class points {
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
        for (i in 0..this.points.size - 1)
            if (this.points[i].equals(vertex)) {
                this.points.drop(i)
                this.isFinished = false
            }
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