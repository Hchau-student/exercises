package com.example.raycastcanvastry

import android.graphics.Bitmap
import android.graphics.Point

fun DrawWalls(size: Point, bitmap: Bitmap): Bitmap {
    var srcPixels = IntArray(size.x * size.y)

    // raycast should be here
    for (i in 0..size.x - 1) {
        for (j in 0..(size.y - 1))
            srcPixels[i + j * size.x] = 0x60fa0060
    }
    bitmap.setPixels(srcPixels, 0, size.x,
        0, 0, size.x, size.y)
    return (bitmap)
}

fun DrawColor(size: Point, bitmap: Bitmap, color: Int) {
    var srcPixels = IntArray(size.x * size.y)
    bitmap.getPixels(srcPixels, 0, size.x,
        0, 0, size.x, size.y)
    for (i in 0..size.x - 1) {
        for (j in 0..(size.y - 1))
            srcPixels[i + j * size.x] = color or 0x70000000
    }
    bitmap.setPixels(srcPixels, 0, size.x,
        0, 0, size.x, size.y)
}
