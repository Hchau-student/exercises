package com.example.raycastcanvastry

import java.lang.Math.cos
import java.lang.Math.sin

//import android.graphics.Point

public class y_Player {

    //добавить FOV
    var moveSpeed: Double = 0.5
    var rotationSpeed: Double = 0.5

    var FOV: FVec2 = FVec2(1.0, 0.66) //расстояние между
        //Direction && Plane
    var Pos: FVec2 = FVec2(22.0, 12.0) //player position
    var Dir: FVec2 = FVec2(-1.0, 0.0) //in
    // possible FOV circle camera direction
    var CameraPlane: FVec2 = FVec2(0.0, 0.66) // масштаб
    // угла обзора в possible FOV circle

    var time = 0.0 //time of current frame
    var oldTime = 0.0 //time of previous frame

    class FVec2 {
        var x: Double = 0.0
        var y: Double = 0.0
        constructor(x: Double, y: Double) {
            this.x = x
            this.y = y
        }

    }
//    fun go() { //за пределами функции обозначить координату и идти к ней
////        if ()
//        Pos.x += Dir.x * moveSpeed
//        Pos.y += Dir.y * moveSpeed
//    }
    fun go(map: z_Map) {
//    int(posX + dirX * moveSpeed)][int(posY)
    var nextStep: Double = Pos.x + Dir.x * (moveSpeed * 2)
        val newPosx: Double = Pos.x + Dir.x * moveSpeed
        if ((nextStep.toInt() < map.With) and (nextStep.toInt() >= 0)) {
            if ((map.worldMap[nextStep.toInt() + (Pos.y.toInt() * map.With).toInt()] == 0))// and
//               if (map.worldMap[Pos.x.toInt() + (Pos.y * map.With).toInt()] == 0)
               Pos.x = newPosx
        }
    nextStep = Pos.y + Dir.y * (moveSpeed * 2)
        val newPosy: Double = Pos.y + Dir.y * moveSpeed
        if ((nextStep.toInt() < map.Height) and (nextStep.toInt() >= 0)) {
                if ((map.worldMap[Pos.x.toInt() + (nextStep.toInt()* map.With).toInt()] == 0))//\\\ and
//                    if (map.worldMap[(Pos.x + Pos.y * map.With).toInt()] == 0)
            {
                    Pos.y = newPosy
            }
        }
    }
    fun rotate(touch: Float) { //поворот - как? Куда?
        val oldDirX: Double = Dir.x
//        if ()
        rotationSpeed = (touch).toDouble() / 100
        Dir.x = Dir.x * cos(rotationSpeed) - Dir.y * sin(rotationSpeed)
        Dir.y = oldDirX * sin(rotationSpeed) + Dir.y * cos(rotationSpeed)
        val oldPlaneX: Double = CameraPlane.x
        CameraPlane.x = CameraPlane.x * cos(rotationSpeed) - CameraPlane.y * sin(rotationSpeed)
        CameraPlane.y = oldPlaneX * sin(rotationSpeed) + CameraPlane.y * cos(rotationSpeed)
    }
}