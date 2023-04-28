package ma.projet.starts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ma.projet.starts.R
import ma.projet.starts.beans.Star
import ma.projet.starts.services.StarService.Companion.instance
import java.util.*

@SuppressLint("ParcelCreator")
class StarAdapter(private val context: Context, private val stars: List<Star>) : RecyclerView.Adapter<StarAdapter.StarViewHolder>(),
    Filterable, Parcelable {
    private var starsFilter: MutableList<Star>
    private val mfilter: NewFilter

    constructor(parcel: Parcel) : this(
        TODO("context"),
        TODO("stars")
    ) {

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): StarViewHolder {
        val v = LayoutInflater.from(context).inflate(
            R.layout.star_item,
            viewGroup, false)
        val holder = StarViewHolder(v)
        holder.itemView.setOnClickListener { v ->
            val popup = LayoutInflater.from(context).inflate(R.layout.edit_start_item, null,
                false)
            val img = popup.findViewById<ImageView>(R.id.img)
            val bar = popup.findViewById<RatingBar>(R.id.stars)
            val name = popup.findViewById<TextView>(R.id.name)
            val ids = popup.findViewById<TextView>(R.id.ids)
            val bitmap = ((v.findViewById<View>(R.id.img) as ImageView).drawable as BitmapDrawable).bitmap
            img.setImageBitmap(bitmap)
            bar.rating = (v.findViewById<View>(R.id.stars) as RatingBar).rating
            ids.text = (v.findViewById<View>(R.id.ids) as TextView).text.toString()
            name.text = (v.findViewById<TextView>(R.id.name) as TextView).text.toString()
            val dialog = AlertDialog.Builder(context)
                .setTitle("Notez : ")
                .setMessage("Donner une note entre 1 et 5 :")
                .setView(popup)
                .setPositiveButton("Valider") { dialog, which ->
                    val s = bar.rating
                    val ids = ids.text.toString().toInt()
                    val star = instance!!.findById(ids)

                    if (star != null) {
                        star.star = s
                    }
                    instance!!.update(star)
                    notifyItemChanged(holder.adapterPosition)
                }
                .setNegativeButton("Annuler", null)
                .create()
            dialog.show()
        }
        return holder
    }
    override fun onBindViewHolder(starViewHolder: StarViewHolder, i: Int) {
        Log.d(TAG, "onBindView call ! $i")
        Glide.with(context)
            .asBitmap()
            .load(starsFilter[i].img)
            .apply(RequestOptions().override(100, 100))
            .into(starViewHolder.img)
        starViewHolder.name.text =
            starsFilter[i].name.uppercase(Locale.getDefault())
        starViewHolder.stars.rating = starsFilter[i].star
        starViewHolder.idss.text = starsFilter[i].id.toString() + ""
    }
    override fun getItemCount(): Int {
        return starsFilter.size
    }
    override fun getFilter(): Filter {
        return mfilter
    }
    inner class StarViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var idss: TextView
        var img: ImageView
        var name: TextView
        var stars: RatingBar
        var parent: RelativeLayout
        init {
            idss = itemView.findViewById(R.id.ids)
            img = itemView.findViewById(R.id.img)
            name = itemView.findViewById(R.id.name)
            stars = itemView.findViewById(R.id.stars)
            parent = itemView.findViewById(R.id.parent)
        }
    }
    inner class NewFilter(var mAdapter: RecyclerView.Adapter<*>) : Filter() {
        override fun performFiltering(charSequence: CharSequence):
                FilterResults {
            starsFilter.clear()
            val results = FilterResults()
            if (charSequence.isEmpty()) {
                starsFilter.addAll(stars)
            } else {
                val filterPattern =
                    charSequence.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (p in stars) {
                    if
                            (p.name.lowercase(Locale.getDefault()).startsWith(filterPattern)) {
                        starsFilter.add(p)
                    }
                }
            }
            results.values = starsFilter
            results.count = starsFilter.size
            return results
        }
        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            starsFilter = filterResults.values as MutableList<Star>
            mAdapter.notifyDataSetChanged()
        }
    }

    companion object CREATOR : Parcelable.Creator<StarAdapter> {
        private const val TAG = "StarAdapter"
        override fun createFromParcel(parcel: Parcel): StarAdapter {
            return StarAdapter(parcel)
        }

        override fun newArray(size: Int): Array<StarAdapter?> {
            return arrayOfNulls(size)
        }
    }

    init {
        starsFilter = ArrayList()
        starsFilter.addAll(stars)
        mfilter = NewFilter(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }


}



