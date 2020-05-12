package com.example.raycastcanvastry

import android.graphics.Bitmap
import android.graphics.Point
//import jdk.nashorn.internal.objects.ArrayBufferView.buffer
import java.lang.Math.abs
import kotlin.math.floor as floor1


//made with guide: https://lodev.org/cgtutor/raycasting.html
//Need to split all of this!!! It's impossible to read!
fun r_DrawWalls(size: Point, bitmap: Bitmap, raycast: r_Raycast): Bitmap {
    var srcPixels = IntArray(size.x * size.y)
//
//    // rayDir for leftmost ray (x = 0) and rightmost ray (x = w)
//    val rayDirX0: Float = (raycast.player.Dir.x - raycast.player.CameraPlane.x).toFloat()
//    val rayDirY0: Float = (raycast.player.Dir.y - raycast.player.CameraPlane.y).toFloat()
//    val rayDirX1: Float = (raycast.player.Dir.x + raycast.player.CameraPlane.x).toFloat()
//    val rayDirY1: Float = (raycast.player.Dir.y + raycast.player.CameraPlane.y).toFloat()
//
    for (y in 0..size.y - 1) {

        val rayDirX0: Float = (raycast.player.Dir.x - raycast.player.CameraPlane.x).toFloat()
        val rayDirY0: Float = (raycast.player.Dir.y - raycast.player.CameraPlane.y).toFloat()
        val rayDirX1: Float = (raycast.player.Dir.x + raycast.player.CameraPlane.x).toFloat()
        val rayDirY1: Float = (raycast.player.Dir.y + raycast.player.CameraPlane.y).toFloat()

        // Current y position compared to the center of the screen (the horizon)
        val p: Int = y - size.y / 2

        // Vertical position of the camera.
        val posZ: Float = (0.5 * size.y).toFloat()

        // Horizontal distance from the camera to the floor for the current row.
        // 0.5 is the z position exactly in the middle between floor and ceiling.
        val rowDistance = posZ / p

        // calculate the real world step vector we have to add for each x (parallel to camera plane)
        // adding step by step avoids multiplications with a weight in the inner loop
        val floorStepX: Float = rowDistance * (rayDirX1 - rayDirX0) / size.x
        val floorStepY: Float = rowDistance * (rayDirY1 - rayDirY0) / size.x

        // real world coordinates of the leftmost column. This will be updated as we step to the right.
        var floorX: Float = (raycast.player.Pos.x + rowDistance * rayDirX0).toFloat()
        var floorY: Float = (raycast.player.Pos.y + rowDistance * rayDirY0).toFloat()
        for (x in 0..size.x - 1) {
            // the cell coord is simply got from the integer parts of floorX and floorY
            val cellX = floorX.toInt()
            val cellY = floorY.toInt()

            // get the texture coordinate from the fractional part
            val tx = (((size.x * (floorX - cellX)).toInt() % (size.x - 1))).toInt()
            val ty = (((size.y * (floorY - cellY))).toInt() % (size.y - 1)).toInt()
            if (tx < 0) tx * -1
            if (ty < 0) ty * -1
            floorX += floorStepX
            floorY += floorStepY

            // choose texture and draw the pixel
//            val floorTexture = 3
//            val ceilingTexture = 6
            var color: Int = 0

            var texture = raycast.textures.a_text
            // floor
            if ((ty < size.y) and (tx < size.x) and (ty > 0) and (tx > 0)) {
                color = texture[size.x * ty + tx]
                srcPixels[x + y * size.x] = color
            }
        }
    }
    for (i in 0..size.x - 1) {
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
        val lineHeight: Int = ((raycast.bm_size.y / perpWallDist) * raycast.wallHeight).toInt()

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

        //draw the pixels of the stripe as a vertical line
        var textYY: Int = 0 //find begin
        if (lineHeight > size.y)
            textYY = (lineHeight - size.y) / 2

        for (j in 0..size.y - 1) {
            if (j < drawStart || j > drawEnd)
                ;
//                srcPixels[i + j * size.x] = 0xff000000.toInt()
            else {
                srcPixels[i + j * size.x] = text[texX + ((textYY * size.x) / (lineHeight) * 1.97).toInt() * size.x]
                textYY++
            }
        }
    }


    bitmap.setPixels(srcPixels, 0, size.x,
        0, 0, size.x, size.y)
    return (bitmap)
}
