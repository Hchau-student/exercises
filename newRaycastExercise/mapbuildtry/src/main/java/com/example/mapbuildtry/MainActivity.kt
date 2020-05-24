package com.example.mapbuildtry

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View

fun DrawCanvas(canvas: Canvas, data: objData, size: Point/*, button: objButton, drawMap: objDrawMap*/) {
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var rect = Rect(-2000, 0, size.x, size.y)//какой кусок карты попадёт на экран
    //нарисовать все кнопки
    canvas.translate(0F, 0F)
    canvas.save()
    canvas.drawBitmap(data.mapView.bm!!, null, rect!!, paint)
    data.buttons.draw(canvas)
    canvas.restore()
}

class MainActivity : AppCompatActivity() {

    val size = Point()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindowManager().getDefaultDisplay().getSize(size)
        setContentView(DrawView(this, size))
    }

    internal inner class DrawView(context: Context, size: Point): View(context) {
        var size = size

//        var mapView: objMapView = objMapView(size, context)
        var data: objData = objData(context, size)

        override fun onDraw(canvas: Canvas) {
//            var button: objButton = objButton(Point(0, 0), Point(100, 100), context, R.drawable.dancing)
            DrawCanvas(canvas, data, size/*, button, drawMap*/)
        }
        override fun onTouchEvent(event: MotionEvent): Boolean {
            ManageTouch(data, context, event)
//            PlayerMove(drawMap.player, tapScreen, event)
            //1) движ - поймать
            //2) перерисовать карту
            //
            invalidate()
            return true
        }
    }
}
