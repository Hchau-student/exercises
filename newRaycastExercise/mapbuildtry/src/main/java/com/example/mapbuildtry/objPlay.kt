package com.example.mapbuildtry

import android.graphics.Point
import android.graphics.Rect

class objPlay {

    fun changeShift(shift: Float, centre: Float, size: Int): Float {
        return shift * (centre * 10 / size)
    }
    fun swapBorders(shift: borders, first: TapScreen.Touch): borders {
        var res = borders(vertex(shift.begin.x, shift.begin.y), vertex(shift.end.x, shift.end.y))
        if (first.isLeft == false) { res.begin.x = shift.end.x; res.end.x = shift.begin.x }
        if (first.isTop == false) { res.begin.y = shift.end.y; res.end.y = shift.begin.y }
        return res
    }
    fun changeShiftBorders(shift: borders, first: TapScreen.Touch, second: TapScreen.Touch, size: Point): borders {
        var res = shift
        res.begin.x = changeShift(shift.begin.x, first.Begin.x, size.x)
        res.end.x = changeShift(shift.end.x, second.Begin.x, size.x)
        res.begin.y = changeShift(shift.begin.y, first.Begin.y, size.y)
        res.end.y = changeShift(shift.end.y, second.Begin.y, size.y)
        return swapBorders(res, first)
    }

    fun DragMap(data: objData, shiftB: borders) {
        if ((data.touch.SecondTouch.isMoving == true) and
            (data.touch.FirstTouch.pointer != data.touch.SecondTouch.pointer)) {

            var newShift = changeShiftBorders(shiftB, data.touch.FirstTouch,
                data.touch.SecondTouch, data.size)
            var leftTop = vertex(newShift.begin.x, newShift.begin.y)
            var rightBottom = vertex(newShift.end.x, newShift.end.y)

            data.mapView.rect = Rect(data.mapView.rect.left - leftTop.x.toInt(),
                data.mapView.rect.top - leftTop.y.toInt(),
                data.mapView.rect.right - rightBottom.x.toInt(),
                data.mapView.rect.bottom - rightBottom.y.toInt())
        } else {
            var shift = if (shiftB.begin.equals(vertex(0.0f, 0.0f)))
                shiftB.end else shiftB.begin
            data.mapView.rect = Rect(
                data.mapView.rect.left - shift.x.toInt(),
                data.mapView.rect.top - shift.y.toInt(),
                data.mapView.rect.right - shift.x.toInt(),
                data.mapView.rect.bottom - shift.y.toInt()
            )
        }
    }
    fun DrawLine(data: objData, shiftB: borders, isUp: Boolean) {
        if ((data.touch.SecondTouch.isMoving == true) and
            (data.touch.FirstTouch.pointer != data.touch.SecondTouch.pointer))
            return
        DrawPoint(data, if (data.touch.SecondTouch.hold)
            data.touch.SecondTouch.pointer else data.touch.FirstTouch.pointer, isUp)
    }

    fun DrawPoint(data: objData, pointer: Int, isUp: Boolean) {
        if (data.buttons.draw.on == false)
            return
        var vertex = if (pointer == data.touch.FirstTouch.pointer) data.touch.FirstTouch.Begin
        else data.touch.SecondTouch.Begin
        if ((data.buttons.ableButton(vertex(vertex.x, vertex.y)) == true))
            return
        var x = data.size.x.toFloat() / (data.mapView.rect.right - data.mapView.rect.left)
        var y = data.size.y.toFloat() / (data.mapView.rect.bottom - data.mapView.rect.top)
        var where = vertex(vertex.x, vertex.y)
        where.x = x * (-data.mapView.rect.left + vertex.x)
        where.y = y * (-data.mapView.rect.top + vertex.y)
        if (where.x < 0) where.x = 0.0f; if (where.y < 0) where.y = 0.0f
        if (where.x >= data.size.x) where.x = data.size.x.toFloat() - 1
        if (where.y >= data.size.y) where.y = data.size.y.toFloat() - 1
        data.mapView.addPoint(where, isUp)
    }
}
