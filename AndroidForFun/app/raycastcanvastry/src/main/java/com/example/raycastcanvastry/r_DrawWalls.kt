@file:Suppress("JniMissingFunction", "JniMissingFunction")

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
    var rayDirX: Float
    var rayDirY: Float
    var mapX: Int
    var mapY: Int
    var sideDistX: Float
    var sideDistY: Float
    val rayDirX0: Float =
        (raycast.player.Dir.x - raycast.player.CameraPlane.x)
    val rayDirY0: Float =
        (raycast.player.Dir.y - raycast.player.CameraPlane.y)
    val rayDirX1: Float =
        (raycast.player.Dir.x + raycast.player.CameraPlane.x)
    val rayDirY1: Float =
        (raycast.player.Dir.y + raycast.player.CameraPlane.y)
    var cameraX: Float
    var deltaDistX: Float
    var deltaDistY: Float
    var perpWallDist: Float
    var stepX: Int
    var stepY: Int
    var hit: Int//was there a wall hit?
    var side: Int //was a NS or a EW wall hit?
    var lineHeight: Int
    var drawStart: Int
    var drawEnd: Int
    var wallX: Float
    var texX: Int
    var textYY: Int
    var text: IntArray
//    external fun stringFromJNI(): String
//
//    var str: String = stringFromJNI()

//    var p: Int
    var posZ: Float = 0.5f * size.y
    var rowDistance: Float
    var floorX: Float
    var floorY: Float
//    var cellX: Int
//    var cellY: Int
    //    var str: String = stringFromJNI()

    var tx: Int
    var ty: Int
    var color: Int
    var DirX = raycast.player.Dir.x
    var DirY = raycast.player.Dir.y
    var PlaneX = raycast.player.CameraPlane.x
    var PlaneY = raycast.player.CameraPlane.y
    var texture: IntArray = raycast.textures.a_text
    var PosX = raycast.player.Pos.x
    var PosY = raycast.player.Pos.y
        for (i in 0..size.x - 1) {
        cameraX = (2 * i / raycast.bm_size.x.toFloat() - 1) //x-coordinate in camera space
        rayDirX = DirX + PlaneX * cameraX
        rayDirY = DirY + PlaneY * cameraX
        mapX = PosX.toInt()
        mapY = PosY.toInt()
        hit = 0
        deltaDistX = abs(1 / rayDirX)
        deltaDistY = abs(1 / rayDirY)
        side = 0

//            RaycastFasterTry()

        if(rayDirX < 0) {
            stepX = -1
            sideDistX = (PosX - mapX) * deltaDistX
        } else {
            stepX = 1
            sideDistX = (mapX + 1.0f - PosX) * deltaDistX
        }
        if(rayDirY < 0)
        {
            stepY = -1
            sideDistY = (PosY - mapY) * deltaDistY
        } else {
            stepY = 1
            sideDistY = (mapY + 1.0f - PosY) * deltaDistY
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
            perpWallDist = (mapX - PosX + (1 - stepX) / 2) / rayDirX
        } else  {
            perpWallDist = (mapY - PosY + (1 - stepY) / 2) / rayDirY
        }
        //Calculate height of line to draw on screen
        //Calculate height of line to draw on screen
        lineHeight = ((raycast.bm_size.y / perpWallDist) * raycast.wallHeight).toInt()

        //calculate lowest and highest pixel to fill in current stripe

        //calculate lowest and highest pixel to fill in current stripe
        drawStart = -lineHeight / 2 + raycast.bm_size.y / 2
        if (drawStart < 0) drawStart = 0
            drawEnd = lineHeight / 2 + raycast.bm_size.y / 2
        if (drawEnd >= raycast.bm_size.y) drawEnd = raycast.bm_size.y - 1
        //calculate value of wallX
         //where exactly the wall was hit
        wallX = if (side == 0) PosY + perpWallDist * rayDirY
        else PosX + perpWallDist * rayDirX
        wallX -= floor1(wallX)

        texX  = (wallX * (size.x)).toInt()
        if(side == 0 && rayDirX > 0) texX = size.x - texX - 1
        if(side == 1 && rayDirY < 0) texX = size.x - texX - 1

        text = if (raycast.map.worldMap[mapX + mapY * raycast.map.With] == 1)
            raycast.textures.a_text else if (raycast.map.worldMap[mapX + mapY * raycast.map.With] == 2)
            raycast.textures.b_text else raycast.textures.c_text

        //draw the pixels of the stripe as a vertical line
        textYY = 0 //find begin
        if (lineHeight > size.y)
            textYY = (lineHeight - size.y) / 2
//        texture = raycast.textures.a_text

        var j = 0
        while (j < size.y) {
            while ((j >= drawEnd) and (j < size.y)) {
//                p = j - size.y / 2

                // Vertical position of the camera.
//                posZ = (0.5 * size.y).toFloat()

                // Horizontal distance from the camera to the floor for the current row.
                // 0.5 is the z position exactly in the middle between floor and ceiling.
//                rowDistance = TryMakeRaycastFaster(posZ, j)
                rowDistance = (posZ / (j - posZ))
                // calculate the real world step vector we have to add for each x (parallel to camera plane)
                // adding step by step avoids multiplications with a weight in the inner loop
                floorX = ((PosX + rowDistance * rayDirX0) + (rowDistance * (rayDirX1 - rayDirX0) / size.x) * i) % 1
                floorY = ((PosY + rowDistance * rayDirY0) + (rowDistance * (rayDirY1 - rayDirY0) / size.x) * i) % 1
//                tx = (((((PosX + rowDistance * rayDirX0) +
//                        ((rowDistance * (rayDirX1 - rayDirX0) / size.x) * i) % 1).to + PosX) % (size.x - 1))).toInt()
                tx = (((size.x * (floorX)).toInt() % (size.x - 1))).toInt()
                ty = (((size.y * (floorY))).toInt() % (size.y - 1)).toInt()
                if (tx < 0) tx * -1
                if (ty < 0) ty * -1
                if ((ty < size.y) and (tx < size.x) and (ty > 0) and (tx > 0)) {
                    color = texture[size.x * ty + tx]
                    srcPixels[i + j * size.x] = color
                }
                j++
            }
            if ((j > drawStart) and (j < drawEnd)) {
                srcPixels[i + j * size.x] = text[texX + ((textYY * size.x) / (lineHeight) * 1.97).toInt() * size.x]
                textYY++
            }
            j++
        }
    }

    bitmap.setPixels(srcPixels, 0, size.x,
        0, 0, size.x, size.y)
    return (bitmap)
}
