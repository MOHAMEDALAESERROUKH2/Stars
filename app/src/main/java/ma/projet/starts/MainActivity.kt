package ma.projet.starts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.core.app.ShareCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ma.projet.starts.adapter.StarAdapter
import ma.projet.starts.beans.Star
import ma.projet.starts.services.StarService

class MainActivity : AppCompatActivity() {
    private var stars: List<Star>? = null
    private var recyclerView: RecyclerView? = null
    private var starAdapter: StarAdapter? = null
    private var service: StarService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stars = ArrayList()
        service = StarService.instance
        init()
        recyclerView = findViewById(R.id.recycle_view)
        starAdapter = StarAdapter(this, service!!.findAll())
        if(recyclerView != null){
            recyclerView!!.adapter = starAdapter
            recyclerView!!.layoutManager = GridLayoutManager(this, 2)
        }
    }
    fun init() {
        service!!.create(
            Star("Blake Lively", "https://www.stars-photos.com/image.php?id=3278", 1F)
        )
        service!!.create(
            Star("Jennifer Lawrence","https://www.stars-photos.com/image.php?id=4593",5F)
        )
        service!!.create(
            Star("Cristiano Ronaldo","https://www.stars-photos.com/image.php?id=3279",4F)
        )
        service!!.create(
            Star("El Baz Anas","https://www.stars-photos.com/image.php?id=4607",2F)
        )

//Ajouter d’autres starts
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (starAdapter != null) {
                    starAdapter!!.filter.filter(newText)
                }
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            val txt = "Stars"
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Stars")
                .setText(txt)
                .startChooser()
        }
        return super.onOptionsItemSelected(item)
    }
}