package com.example.mapbuildtry

import android.graphics.Point
import android.graphics.Rect

class objPlay {

    private fun changeShift(shift: Float, centre: Float, size: Int): Float {
        return shift * (centre * 10 / size)
    }
    private fun swapBorders(shift: Borders, first: TapScreen.Touch): Borders {
        val res = Borders(Vertex(shift.begin.x, shift.begin.y), Vertex(shift.end.x, shift.end.y))
        if (!first.isLeft) { res.begin.x = shift.end.x; res.end.x = shift.begin.x }
        if (!first.isTop) { res.begin.y = shift.end.y; res.end.y = shift.begin.y }
        return res
    }
    private fun changeShiftBorders(shift: Borders, first: TapScreen.Touch, second: TapScreen.Touch, size: Point): Borders {
        shift.begin.x = changeShift(shift.begin.x, first.Begin.x, size.x)
        shift.end.x = changeShift(shift.end.x, second.Begin.x, size.x)
        shift.begin.y = changeShift(shift.begin.y, first.Begin.y, size.y)
        shift.end.y = changeShift(shift.end.y, second.Begin.y, size.y)
        return swapBorders(shift, first)
    }

    fun dragMap(data: ObjData, shiftB: Borders) {
        if ((data.touch.secondTouch.isMoving) and
            (data.touch.firstTouch.pointer != data.touch.secondTouch.pointer)) {

            val newShift = changeShiftBorders(shiftB, data.touch.firstTouch,
                data.touch.secondTouch, data.size)
            val leftTop = Vertex(newShift.begin.x, newShift.begin.y)
            val rightBottom = Vertex(newShift.end.x, newShift.end.y)

            data.mapView.rect = Rect(data.mapView.rect.left - leftTop.x.toInt(),
                data.mapView.rect.top - leftTop.y.toInt(),
                data.mapView.rect.right - rightBottom.x.toInt(),
                data.mapView.rect.bottom - rightBottom.y.toInt())
        } else {
            val shift = if (shiftB.begin.equals(Vertex(0.0f, 0.0f)))
                shiftB.end else shiftB.begin
            data.mapView.rect = Rect(
                data.mapView.rect.left - shift.x.toInt(),
                data.mapView.rect.top - shift.y.toInt(),
                data.mapView.rect.right - shift.x.toInt(),
                data.mapView.rect.bottom - shift.y.toInt()
            )
        }
    }
    fun drawLine(data: ObjData, /* shiftB: Borders,*/ isUp: Boolean) {
        if ((data.touch.secondTouch.isMoving) and
            (data.touch.firstTouch.pointer != data.touch.secondTouch.pointer))
            return
        drawPoint(data, if (data.touch.secondTouch.hold)
            data.touch.secondTouch.pointer else data.touch.firstTouch.pointer, isUp)
    }

    private fun drawPoint(data: ObjData, pointer: Int, isUp: Boolean) {
        if (!data.buttons.draw.on) return
        val vertex = if (pointer == data.touch.firstTouch.pointer) {
           Vertex (data.touch.firstTouch.Begin.x, data.touch.firstTouch.Begin.y)
        } else Vertex (data.touch.secondTouch.Begin.x, data.touch.secondTouch.Begin.y)

        if (data.buttons.ableButton(Vertex(vertex.x, vertex.y)))
            return
        val x = data.size.x.toFloat() / (data.mapView.rect.right - data.mapView.rect.left)
        val y = data.size.y.toFloat() / (data.mapView.rect.bottom - data.mapView.rect.top)
        val where = Vertex(vertex.x, vertex.y)
        where.x = x * (-data.mapView.rect.left + vertex.x)
        where.y = y * (-data.mapView.rect.top + vertex.y)
        if (where.x < 0) where.x = 0.0f; if (where.y < 0) where.y = 0.0f
        if (where.x >= data.size.x) where.x = data.size.x.toFloat() - 1
        if (where.y >= data.size.y) where.y = data.size.y.toFloat() - 1
        data.mapView.addPoint(where, isUp)
    }
}
