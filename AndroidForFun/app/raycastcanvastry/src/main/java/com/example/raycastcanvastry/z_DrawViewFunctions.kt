package com.example.raycastcanvastry

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

fun DrawCanvas(canvas: Canvas, bm: Bitmap, rect: Rect, paint: Paint) {
    canvas.translate(0F, 0F);
    canvas.save()
    canvas.translate(0f, 0f)
    canvas.drawBitmap(bm!!, null, rect!!, paint)
    canvas.restore()
}
