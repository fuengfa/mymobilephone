package com.scb.mobilephone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileFragment : Fragment(), OnMobileClickListener, OnSortClickListener {
    override fun sortlowtoheight() {
        var list : List<MobileModel> = sortList.sortedBy { it.price }
        setMobileAdapter(list)
    }

    override fun sorthighttolow() {
        var list : List<MobileModel> = sortList.sortedByDescending { it.price }
        setMobileAdapter(list)
    }

    override fun sortrating() {
        var list : List<MobileModel> = sortList.sortedByDescending { it.rating }
        setMobileAdapter(list)
    }

    override fun onClickHeartClick(favImage: ImageView, mobile: MobileModel) {
        if(mobile.fav == 0){
            favImage.setImageResource(R.drawable.ic_favorite_black_24dp)
            mobile.fav = 1
        }else{
            favImage.setImageResource(R.drawable.ic_favorite)
            mobile.fav = 0
        }

    }

    override fun onMobileClick(mobile: MobileModel, _view: View) {

        var intent = Intent(context, MobileDetailActivity::class.java)
        intent.putExtra("mobile", mobile)
        context!!.startActivity(intent)
    }

    companion object {
        private const val EXTRA_SONG = "mobile"
        fun startActivity(context: Context, mobile: MobileModel? = null) =
            context.startActivity(Intent(context, MobileFragment::class.java))
    }
    private lateinit var rvMobile: RecyclerView
    private lateinit var mobileAdapter: MobileAdapter
    private lateinit var sortList: List<MobileModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mobile, container, false)
    }

    private val songListCallback = object : Callback<List<MobileModel>> {
        override fun onFailure(call: Call<List<MobileModel>>, t: Throwable) {
            println("conttext Showtoast")
            context?.showToast("Can not call country list $t")
        }

        override fun onResponse(call: Call<List<MobileModel>>, response: Response<List<MobileModel>>) {
            context?.showToast("Success")

            sortList = response.body()!!
            setMobileAdapter(sortList)
        }
    }

    private fun setMobileAdapter(list: List<MobileModel>){
        mobileAdapter.submitList(list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMobile = view.findViewById(R.id.recyclerView)
        mobileAdapter = MobileAdapter(this)
        rvMobile.adapter = mobileAdapter
        rvMobile.layoutManager = LinearLayoutManager(context)
        rvMobile.itemAnimator = DefaultItemAnimator()

        loadSongs()


    }


    private fun loadSongs()  {
        ApiManager.mobileService.mobile().enqueue(songListCallback)


    }

    private fun setHeartRed(_view: View) {
        val heart: ImageView = _view.findViewById(R.id.mobileHeart)
        heart.setImageDrawable(ContextCompat.getDrawable(_view.context, R.drawable.favorite))
    }

}



