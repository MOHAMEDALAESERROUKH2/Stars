package ma.projet.starts.services

import ma.projet.starts.beans.Star
import ma.projet.starts.dao.IDao

class StarService private constructor() : IDao<Star?> {
    private val stars: MutableList<Star>

    init {
        stars = ArrayList()
    }

    companion object {
        @JvmStatic
        var instance: StarService? = null
            get() {
                if (field == null) field = StarService()
                return field
            }
            private set
    }

    override fun create(o: Star?): Boolean {
        return stars.add(o!!)
    }

    override fun update(o: Star?): Boolean {
        for (s in stars) {
            if (o != null) {
                if (s.star == o.id.toFloat()) {
                    s.img = o.img
                    s.name = o.name
                    s.star = o.star
                }
            }
        }
        return true
    }

    override fun delete(o: Star?): Boolean {
        return stars.remove(o)
    }

    override fun findById(id: Int): Star? {
        for (s in stars) {
            if (s.id == id) return s
        }
        return null
    }

    override fun findAll(): List<Star> {
        return stars
    }
}