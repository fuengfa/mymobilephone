package com.scb.mobilephone

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MobileAdapter(private val listener: OnMobileClickListener)
    : RecyclerView.Adapter<MobileViewHolder>() {
    val mobiles: List<MobileModel>
        get() = _mobiles

    private var _mobiles: List<MobileModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MobileViewHolder(parent)

    override fun onBindViewHolder(holder: MobileViewHolder, position: Int) {
        holder.bind(_mobiles[position], listener)
    }

    override fun getItemCount(): Int {
        return mobiles.size
    }

    fun submitList(list: List<MobileModel>) {
        _mobiles = list
        println("Listttttttttt ${list.toString()}")
        notifyDataSetChanged()
    }

}

class MobileViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.mobilephone,
            parent, false)
) {
    private val mobilePic: ImageView = itemView.findViewById(R.id.mobilePicture)
    private val mobileName: TextView = itemView.findViewById(R.id.mobileModel)
    private val mobileDescription: TextView = itemView.findViewById(R.id.mobileDescription)
    private val mobilePrice: TextView = itemView.findViewById(R.id.mobilePrice)
    private val mobileRating: TextView = itemView.findViewById(R.id.mobileRating)
    private val mobileHaert: ImageView = itemView.findViewById(R.id.mobileHeart)

    fun bind(mobile: MobileModel, listener: OnMobileClickListener) {
        Glide
            .with(itemView.context)
            .load(mobile.thumbImageURL)
            .centerCrop()
            .into(mobilePic)
        mobileName.text = mobile.name
        mobileDescription.text = mobile.description
        mobilePrice.text = "Price: ${mobile.price}"
        mobileRating.text = "Rating: ${mobile.rating}"
        itemView.setOnClickListener { listener.onMobileClick(mobile,itemView.id) }

    }

}

interface OnMobileClickListener {
    fun onMobileClick(mobile: MobileModel, itemid: Int)

}

