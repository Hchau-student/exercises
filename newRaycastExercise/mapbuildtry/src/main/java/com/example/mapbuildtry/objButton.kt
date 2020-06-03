package com.example.mapbuildtry

import android.content.Context
import android.graphics.*

class ObjButton(start: Point, end: Point, context: Context, resource: Int) {
    var rect: Rect
    var bitmap: Bitmap
    private var borders: Borders
    var on: Boolean = false
    var filter: ColorFilter

    init {
        val cmData = floatArrayOf(
            1f, 0f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
            0f, 0f, 0f, 0.5f, 0f
        )
        val cm = ColorMatrix(cmData)
        this.filter = ColorMatrixColorFilter(cm)
        this.rect = Rect(start.x, start.y, end.x, end.y)
        this.borders = Borders(Vertex(start.x.toFloat(), start.y.toFloat()),
        Vertex(end.x.toFloat(), end.y.toFloat()))
        this.bitmap = BitmapFactory.decodeResource(context.resources, resource)
    }
    fun isButtonTaped(x: Float, y: Float): Boolean {
        if ((x < this.borders.begin.x) or (x > this.borders.end.x))
            return false
        if ((y < this.borders.begin.y) or (y > this.borders.end.y))
            return false
        return true
    }
}

class ButtonData//add else buttons initialisation here//add else buttons here; 3D?
//    var draw: objButton
    (context: Context) {
    var draw: ObjButton = ObjButton(Point(50, 230), Point(200, 380),
        context, R.drawable.dancing)
    var drag: ObjButton = ObjButton(Point(50, 50), Point(200, 200),
        context, R.drawable.dancing)

    init {
        this.drag.on = false
        this.draw.on = true
    }
    fun ableButton(location: Vertex): Boolean {
        if (drag.isButtonTaped(location.x, location.y)) {
            if (!drag.on) {
                drag.on = true

                //unable else buttons
                draw.on = false
            }
            return true
        }
        if (draw.isButtonTaped(location.x, location.y)) {
            if (!draw.on) {
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
        if (!drag.on) paint.colorFilter = drag.filter
        canvas.drawBitmap(this.drag.bitmap, null, this.drag.rect, paint)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        if (!draw.on) paint.colorFilter = draw.filter
        canvas.drawBitmap(this.draw.bitmap, null, this.draw.rect, paint)
        //and else buttons
    }
}
