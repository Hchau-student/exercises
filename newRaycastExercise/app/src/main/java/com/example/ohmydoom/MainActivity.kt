package com.example.ohmydoom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.graphics.*
import android.view.MotionEvent

fun DrawCanvas(canvas: Canvas, bm: Bitmap, size: Point, drawMap: objDrawMap) {
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var rect = Rect(0, 0, size.x, size.y)
    canvas.translate(0F, 0F)
    canvas.save()
//    var o: objDrawMap = objDrawMap(floatArrayOf(70.0f, 20.0f, 70.0f, 70.0f), floatArrayOf(50.0f, 50.0f, 0.0f))
    canvas.drawBitmap(bm!!, null, rect!!, paint)
    DrawMap(size, canvas, drawMap)
    canvas.restore()
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val size = Point()
        getWindowManager().getDefaultDisplay().getSize(size)

        setContentView(DrawView(this, size))
    }
    internal inner class DrawView(context: Context, size: Point): View(context) {
        var size = size
        var tapScreen: TapScreen = TapScreen()
        var drawMap: objDrawMap = objDrawMap(floatArrayOf(70.0f, 20.0f, 70.0f, 170.0f), floatArrayOf(50.0f, 50.0f, 0.0f))
        var bm: Bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.anonim)
        var abm = Bitmap.createScaledBitmap(bm, size.x, size.y, false)
//        var player: Player = Player(50, 50, 0.0f)

        override fun onDraw(canvas: Canvas) {
            DrawCanvas(canvas, abm, size, drawMap)
        }
        override fun onTouchEvent(event: MotionEvent): Boolean {
            PlayerMove(drawMap.player, tapScreen, event)
            invalidate()
            return true
        }
    }
}
