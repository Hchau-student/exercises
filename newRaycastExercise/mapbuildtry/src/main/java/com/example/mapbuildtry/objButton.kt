package com.example.mapbuildtry

import android.content.Context
import android.graphics.*


class objButton {
    var rect: Rect
    var bitmap: Bitmap
    var borders: borders
    var on: Boolean = false
    var filter: ColorFilter

    constructor(start: Point, end: Point, context: Context, resource: Int) {
        val cmData = floatArrayOf(
            1f, 0f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 0.5f, 0f
        )
        var cm = ColorMatrix(cmData)
        this.filter = ColorMatrixColorFilter(cm)
        this.rect = Rect(start.x, start.y,
            end.x, end.y)
        this.borders = borders(
            vertex(start.x.toFloat(), start.y.toFloat()),
        vertex(end.x.toFloat(), end.y.toFloat())
        )
        this.bitmap = BitmapFactory.decodeResource(context?.getResources(), resource)
    }
    fun isButtonTaped(x: Float, y: Float): Boolean {
        if ((x < this.borders.begin.x) or (x > this.borders.end.x))
            return false
        if ((y < this.borders.begin.y) or (y > this.borders.end.y))
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
        this.drag = objButton(Point(50, 50), Point(200, 200),
            context, R.drawable.dancing)
        this.drag.on = false
        this.draw = objButton(Point(50, 230), Point(200, 380),
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
        if (this.drag.on == false) paint.setColorFilter(this.drag.filter)
        canvas.drawBitmap(this.drag.bitmap!!, null, this.drag.rect!!, paint)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        if (this.draw.on == false) paint.setColorFilter(this.draw.filter)
        canvas.drawBitmap(this.draw.bitmap!!, null, this.draw.rect!!, paint)
        //and else buttons
    }
}
