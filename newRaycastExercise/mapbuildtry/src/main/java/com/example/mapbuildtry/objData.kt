package com.example.mapbuildtry

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect

class objData {
    var mapView: objMapView
    var buttons: buttonData
    var size: Point
    var touch: TapScreen = TapScreen()
    //добавить структуру карты, секторов и всего  такого
    constructor(context: Context, size: Point) {
        this.buttons = buttonData(context)
        this.mapView = objMapView(size, context)
        this.size = size
    }
    fun Draw(canvas: Canvas) {
        canvas.save()
        this.mapView.Draw(canvas, this.size)
        this.buttons.draw(canvas)
        canvas.restore()
    }
}
