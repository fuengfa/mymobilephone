package com.scb.mobilephone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileFragment : Fragment(), OnMobileClickListener {
    override fun onMobileClick(mobile: MobileModel, itemid: Int) {
        var intent = Intent(context, MobileActivity::class.java)
        intent.putExtra("mobile", mobile)
        context!!.startActivity(intent)
    }

    private lateinit var rvSongs: RecyclerView
    private lateinit var songAdapter: MobileAdapter

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
            songAdapter.submitList(response.body()!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSongs = view.findViewById(R.id.recyclerView)
        songAdapter = MobileAdapter(this)
        rvSongs.adapter = songAdapter
        rvSongs.layoutManager = LinearLayoutManager(context)
        rvSongs.itemAnimator = DefaultItemAnimator()

        loadSongs()
    }

    private fun loadSongs()  {
        ApiManager.countryService.countries().enqueue(songListCallback)
//        println("hoiiiiiiiii ${ApiManager.artistService.songs().enqueue(songListCallback)}")
    }

}
