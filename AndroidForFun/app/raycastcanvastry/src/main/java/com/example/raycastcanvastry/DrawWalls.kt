package com.example.raycastcanvastry

import android.graphics.Bitmap
import android.graphics.Point

fun	full_column_texture(column: IntArray, col_x: Int, col_height: Int, cur_pix: Int, raycast: r_Raycast, resBitm: IntArray)
{
    var y: Int = 0
    var	pix: Point = Point(0, 0)

    pix.x = cur_pix;
    pix.y = 0;
    y = 0;
    while (y < col_height)
    {
        pix.y = y + raycast.bm_size.y / 2 - col_height / 2;
        if (pix.y >= 0 && pix.y < raycast.bm_size.y)
            resBitm[pix.x + pix.y * raycast.bm_size.x] = column[y]
        y++;
    }
}

fun DrawWalls(size: Point, bitmap: Bitmap): Bitmap {
    var srcPixels = IntArray(size.x * size.y)

    // raycast should be here
    for (i in 0..size.x - 1) {
        for (j in 0..(size.y - 1))
            srcPixels[i + j * size.x] = 0x60fa0060
//        var cameraX: Double = 2 * i / w.toDouble - 1; //x-coordinate in camera space
//        double rayDirX = dirX + planeX * cameraX;
//        double rayDirY = dirY + planeY * cameraX;
    }
    bitmap.setPixels(srcPixels, 0, size.x,
        0, 0, size.x, size.y)
    return (bitmap)
}
//
//fun DrawWalls(size: Point, bitmap: Bitmap): Bitmap {
//    var srcPixels = IntArray(size.x * size.y)
//
//    // raycast should be here
//    for (i in 0..size.x - 1) {
////        for (j in 0..(size.y - 1))
////            srcPixels[i + j * size.x] = 0x60fa0060
//        var cameraX: Double = 2 * i / w.toDouble - 1; //x-coordinate in camera space
//        double rayDirX = dirX + planeX * cameraX;
//        double rayDirY = dirY + planeY * cameraX;
//    }
//    bitmap.setPixels(srcPixels, 0, size.x,
//        0, 0, size.x, size.y)
//    return (bitmap)
//}

//check touches
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
