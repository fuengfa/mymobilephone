package com.scb.mobilephone.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixplicity.easyprefs.library.Prefs
import com.scb.mobilephone.CustomItemTouchHelperListener
import com.scb.mobilephone.ui.model.MobileModel
import com.scb.mobilephone.R
import com.scb.mobilephone.ui.model.PREFS_KEY_ID

class FavoriteAdapter(private val listener: OnMobileClickListener)
    :RecyclerView.Adapter<FavViewHolder>(), CustomItemTouchHelperListener {
    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return false
    }

    override fun onItemDismiss(position: Int) {
        var idFavorit = Prefs.getStringSet(PREFS_KEY_ID, mutableSetOf<String>())
        Log.d("fue-listbeforeRemove",idFavorit.toString())
        idFavorit.remove(idFavorit.elementAt(position))
        Log.d("fue-listAfterRemove",idFavorit.toString())
        Prefs.putStringSet(PREFS_KEY_ID,idFavorit )
        Log.d("fue-listAfterPref",Prefs.getStringSet(PREFS_KEY_ID, mutableSetOf<String>()).toString())
        var sortListFavorite: ArrayList<MobileModel> = arrayListOf()
        var i: Int =0
        for (name in _mobiles) {
            if ( i == position){
                i++
            }else{
                sortListFavorite.add(name)
                i++
            }
        }
        i = 0
        Log.d("fue-listAfterremove", sortListFavorite.size.toString())

        submitList(sortListFavorite)

    }

    val mobiles: List<MobileModel>
        get() = _mobiles

    private var _mobiles: List<MobileModel> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavViewHolder(parent)

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(_mobiles[position])
    }

    override fun getItemCount(): Int {
        return mobiles.size
    }

    fun submitList(list: List<MobileModel>) {
        _mobiles = list
        notifyDataSetChanged()
    }
}

class FavViewHolder (parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(
            R.layout.favorite_list,
            parent, false)
) {
    private var mobilePic: ImageView ?=null
    private var mobileName: TextView ?=null
    private var mobilePrice: TextView ?=null
    private var mobileRating: TextView ?=null

    fun bind(mobile: MobileModel) {
        mobilePic= itemView.findViewById(R.id.faveImage) as ImageView
        mobileName = itemView.findViewById(R.id.favName) as TextView
        mobilePrice = itemView.findViewById(R.id.favPrice) as TextView
        mobileRating = itemView.findViewById(R.id.faveRating) as TextView


            mobilePic?.let {
                Glide
                    .with(itemView.context)
                    .load(mobile.thumbImageURL)
                    .centerCrop()
                    .into(it)
            }
            mobileName?.text = mobile.name
            mobilePrice?.text = mobile.price.toString()
            mobileRating?.text = "Rating: ${mobile.rating}"



    }

}

