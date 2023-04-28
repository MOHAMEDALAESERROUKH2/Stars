package ma.projet.starts.beans

class Star(name: String, img: String, star: Float) {
    var id: Int
    var name: String
    var img: String
    var star: Float
    companion object {
        private var comp = 0
    }
    init {
        id = ++comp
        this.name = name
        this.img = img
        this.star = star
    }
}