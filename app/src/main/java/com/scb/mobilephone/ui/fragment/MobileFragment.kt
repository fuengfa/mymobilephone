package com.scb.mobilephone.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephone.*
import com.scb.mobilephone.ui.Activity.MainActivity
import com.scb.mobilephone.ui.Activity.MobileDetailActivity
import com.scb.mobilephone.ui.Activity.OnClickFavListener
import com.scb.mobilephone.ui.Activity.OnSortClickListener
import com.scb.mobilephone.ui.Service.ApiManager
import com.scb.mobilephone.ui.adapter.MobileAdapter
import com.scb.mobilephone.ui.adapter.OnMobileClickListener
import com.scb.mobilephone.ui.model.MobileModel
import com.scb.mobilephone.ui.model.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileFragment() : Fragment(),
    OnMobileClickListener, OnSortClickListener {

    companion object {
        private const val EXTRA_SONG = "mobile"
        fun startActivity(context: Context, mobile: MobileModel? = null) =
            context.startActivity(Intent(context, MobileFragment::class.java))
    }
    private lateinit var myMobileList: MobileModel
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
    override fun heart() {
        setMobileAdapter(sortList)
    }

    override fun sortlowtoheight() {
        var list: List<MobileModel> = sortList.sortedBy { it.price }
        setMobileAdapter(list)
    }

    override fun sorthighttolow() {
        var list: List<MobileModel> = sortList.sortedByDescending { it.price }
        setMobileAdapter(list)
    }

    override fun sortrating() {
        var list: List<MobileModel> = sortList.sortedByDescending { it.rating }
        setMobileAdapter(list)
    }

    override fun onClickHeartClick(favImage: ImageView, mobile: MobileModel) {
//        if (mobile.fav == 0) {
//            favImage.setImageResource(R.drawable.ic_favorite)
//            mobile.fav = 1
//            var b = Prefs.getStringSet(PREFS_KEY_ID, mutableSetOf<String>())
//            Log.d("fue", b.toString())
//            b.add(mobile.id.toString())
//            Log.d("fue-", b.toString())
//            Prefs.putStringSet(PREFS_KEY_ID, b)
//            var m = Prefs.getStringSet(PREFS_KEY_ID, mutableSetOf<String>())
//            Log.d("fue", m.toString())
//        } else {
//            favImage.setImageResource(R.drawable.ic_favorite_black_24dp)
//            mobile.fav = 0
//            var b = Prefs.getStringSet(PREFS_KEY_ID, mutableSetOf<String>())
//            b.remove(mobile.id.toString())
//        }
        (context as? MainActivity)?.clickHeartfromMainActivity()
    }

    override fun onMobileClick(mobile: MobileModel, _view: View) {

        var intent = Intent(context, MobileDetailActivity::class.java)
        intent.putExtra("mobile", mobile)
        context!!.startActivity(intent)
    }


    private fun setMobileAdapter(list: List<MobileModel>) {
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

//        var b = Prefs.getStringSet(PREFS_KEY_ID, mutableSetOf<String>())
//        Log.d("fue start", b.toString())


    }

    private fun loadSongs() {
        ApiManager.mobileService.mobile().enqueue(songListCallback)


    }

}



