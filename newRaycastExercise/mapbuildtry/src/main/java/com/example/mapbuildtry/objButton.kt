package com.example.mapbuildtry

import android.content.Context
import android.content.res.Resources
import android.graphics.*

class objButton {
    var rect: Rect
    var bitmap: Bitmap
    var borders: borders
    var on: Boolean = false
    constructor(start: Point, end: Point, context: Context, resource: Int) {
        this.rect = Rect(start.x, start.y,
            end.x, end.y)
        this.borders = borders(
            vertex(start.x.toFloat(), start.y.toFloat()),
        vertex(end.x.toFloat(), end.y.toFloat())
        )
        this.bitmap = BitmapFactory.decodeResource(context?.getResources(), resource)
    }
    fun isButtonTaped(x: Float, y: Float): Boolean {
        if (x < this.borders.begin.x)
            return false
        if (x > this.borders.end.x)
            return false
        if (y < this.borders.begin.y)
            return false
        if (y > this.borders.end.y)
            return false
        return true
    }
}

class buttonData {
    var draw: objButton
    var drag: objButton
    //add else buttons here; 3D?
    //    var draw: objButton
    constructor(context: Context) {
        this.drag = objButton(Point(50, 50), Point(150, 150),
            context, R.drawable.dancing)
        this.drag.on = false
        this.draw = objButton(Point(50, 200), Point(150, 300),
            context, R.drawable.dancing)
        this.draw.on = true
        //add else buttons initialisation here

    }
    fun ableButton(location: vertex): Boolean {
        if (drag.isButtonTaped(location.x, location.y) == true) {
            if (drag.on == false) {
                drag.on = true

                //unable else buttons
                draw.on = false
            }
            return true
        }
        if (draw.isButtonTaped(location.x, location.y) == true) {
            if (draw.on == false) {
                draw.on = true

                //unable else buttons
                drag.on = false
            }
            return true
        }
        return false
    }
    fun draw(canvas: Canvas) {
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawBitmap(this.drag.bitmap!!, null, this.drag.rect!!, paint)
        canvas.drawBitmap(this.draw.bitmap!!, null, this.draw.rect!!, paint)
        //and else buttons
    }
}
