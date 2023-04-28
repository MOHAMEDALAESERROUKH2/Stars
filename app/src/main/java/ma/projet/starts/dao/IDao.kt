package ma.projet.starts.dao

interface IDao<T> {
    fun create(o: T): Boolean
    fun update(o: T): Boolean
    fun delete(o: T): Boolean
    fun findById(id: Int): T
    fun findAll(): List<T>?
}