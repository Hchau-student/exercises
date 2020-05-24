package com.example.ohmydoom

class objDrawMap {
    var player: Player
    var vertexArray: Array<vertex> = arrayOf()
    constructor(arr: FloatArray, player: FloatArray) {//здесь может быть парсинг
        var i = 0
        while (i + 1 < arr.size) {
            this.vertexArray += vertex(arr[i], arr[i + 1])
            i += 2
        }
        this.player = Player(player[0], player[1], player[2])
    }
}

class vertex {
    var x: Float
    var y: Float
    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }
}
