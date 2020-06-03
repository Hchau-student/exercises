package com.example.mapbuildtry

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point

//добавить структуру карты, секторов и всего  такого
class ObjData (context: Context, val size: Point) {
    val mapView = ObjMapView(size, context)
    val buttons = ButtonData(context)
    val touch: TapScreen = TapScreen()

    fun draw(canvas: Canvas) {
        canvas.save()
        this.mapView.draw(canvas, this.size)
        this.buttons.draw(canvas)
        canvas.restore()
    }
}
