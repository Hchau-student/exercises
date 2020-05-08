package com.example.raycastcanvastry

import android.graphics.Bitmap
import android.graphics.Point
import java.lang.Math.abs
import kotlin.math.floor as floor1

//Need to split all of this!!! It's impossible to read!
fun r_DrawWalls(size: Point, bitmap: Bitmap, raycast: r_Raycast): Bitmap {
    var srcPixels = IntArray(size.x * size.y)

    // raycast should be here
    for (i in 0..size.x - 1) {
//        for (j in 0..(size.y - 1))
//            srcPixels[i + j * size.x] = 0x60fa0060
        var cameraX: Double = 2 * i / raycast.bm_size.x.toDouble() - 1 //x-coordinate in camera space
        var rayDirX: Double = raycast.player.Dir.x + raycast.player.CameraPlane.x * cameraX
        var rayDirY:Double = raycast.player.Dir.y + raycast.player.CameraPlane.y * cameraX
        var mapX: Int = raycast.player.Pos.x.toInt()
        var mapY: Int = raycast.player.Pos.y.toInt()
        var sideDistX: Double
        var sideDistY: Double
        var deltaDistX: Double = abs(1 / rayDirX)
        var deltaDistY: Double = abs(1 / rayDirY)
        var perpWallDist: Double
        var stepX: Int
        var stepY: Int
        var hit = 0 //was there a wall hit?
        var side: Int = 0 //was a NS or a EW wall hit?

        if(rayDirX < 0)
        {
            stepX = -1
            sideDistX = (raycast.player.Pos.x - mapX) * deltaDistX
        }
        else
        {
            stepX = 1
            sideDistX = (mapX + 1.0 - raycast.player.Pos.x) * deltaDistX
        }
        if(rayDirY < 0)
        {
            stepY = -1
            sideDistY = (raycast.player.Pos.y - mapY) * deltaDistY
        }
        else
        {
            stepY = 1
            sideDistY = (mapY + 1.0 - raycast.player.Pos.y) * deltaDistY
        }
        while (hit == 0)
        {
            //jump to next map square, OR in x-direction, OR in y-direction
            if(sideDistX < sideDistY)
            {
                sideDistX += deltaDistX
                mapX += stepX
                side = 0
            }
            else
            {
                sideDistY += deltaDistY
                mapY += stepY
                side = 1
            }
            //Check if ray has hit a wall
            if (raycast.map.worldMap[mapX + mapY * raycast.map.With] > 0) {
                hit = 1
            }
        }
        if (side == 0) {
            perpWallDist = (mapX - raycast.player.Pos.x + (1 - stepX) / 2) / rayDirX
        } else  {
            perpWallDist = (mapY - raycast.player.Pos.y + (1 - stepY) / 2) / rayDirY
        }
        //Calculate height of line to draw on screen
        //Calculate height of line to draw on screen
        val lineHeight: Int = (raycast.bm_size.y / perpWallDist).toInt()

        //calculate lowest and highest pixel to fill in current stripe

        //calculate lowest and highest pixel to fill in current stripe
        var drawStart: Int = -lineHeight / 2 + raycast.bm_size.y / 2
        if (drawStart < 0) drawStart = 0
        var drawEnd: Int = lineHeight / 2 + raycast.bm_size.y / 2
        if (drawEnd >= raycast.bm_size.y) drawEnd = raycast.bm_size.y - 1
        //calculate value of wallX
        var wallX: Double //where exactly the wall was hit
        wallX = if (side == 0) raycast.player.Pos.y + perpWallDist * rayDirY
        else raycast.player.Pos.x + perpWallDist * rayDirX
        wallX -= floor1(wallX)

        var texX: Int = (wallX * (size.x)).toInt()
        if(side == 0 && rayDirX > 0) texX = size.x - texX - 1
        if(side == 1 && rayDirY < 0) texX = size.x - texX - 1

        val text: IntArray = if (raycast.map.worldMap[mapX + mapY * raycast.map.With] == 1)
            raycast.textures.a_text else if (raycast.map.worldMap[mapX + mapY * raycast.map.With] == 2)
            raycast.textures.b_text else raycast.textures.c_text

        val step: Double = 1.0 * size.y / lineHeight
        var texPos: Double = (drawStart - size.y / 2 + lineHeight / 2) * step
//
//        var color: Int
//        when (raycast.map.worldMap[mapX + mapY * raycast.map.With]) {
//            1 -> color = 0xffff0000.toInt()
//            2 -> color = 0xff00ff00.toInt()
//            3 -> color = 0xff0000ff.toInt()
//            4 -> color = 0xffffffff.toInt()
//            else -> color = 0xffffff00.toInt()
//        }
//        if(side == 1) {color = color / 2;}


        //draw the pixels of the stripe as a vertical line
        var textYY: Int = 0
        for (j in 0..size.y - 1) {
            if (j < drawStart || j > drawEnd)
                srcPixels[i + j * size.x] = 0xff000000.toInt()
//            else
//                srcPixels[i + j * size.x] = color
            else {
                srcPixels[i + j * size.x] = text[texX + ((textYY * size.x) / (lineHeight) * 1.97).toInt() * size.x]
//                var color: Int = text[texX + ((textYY * size.x) / (lineHeight) * 1.97).toInt() * size.x]
                textYY++
//            if (side === 1) {
//                color = color - 0x55000000
////                srcPixels[i + j * size.x] = 0xff000000.toInt()
////                srcPixels[i + j * size.x] += color
////                color
//                //подвигать rgb и сделать потемнее (поменьше значения)
//                //объединить обратно в color
//            }
//                srcPixels[i + j * size.x] = color
            }
        }
    }
    bitmap.setPixels(srcPixels, 0, size.x,
        0, 0, size.x, size.y)
    return (bitmap)
}