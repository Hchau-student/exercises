package com.example.mapbuildtry

import android.content.Context
import android.graphics.Point

class objData {
    var mapView: objMapView
    var buttons: buttonData
    var size: Point
    var touch: TapScreen = TapScreen()
    //добавить структуру карты, секторов и всего  такого
    constructor(context: Context, size: Point) {
        this.buttons = buttonData(context)
        this.mapView = objMapView(size, context)
        this.size = size
    }
}
