package com.scb.mobilephone.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pixplicity.easyprefs.library.Prefs
import com.scb.mobilephone.*
import com.scb.mobilephone.ui.Activity.OnClickFavListener
import com.scb.mobilephone.ui.Activity.OnSortClickListener
import com.scb.mobilephone.ui.Service.ApiManager
import com.scb.mobilephone.ui.adapter.FavoriteAdapter
import com.scb.mobilephone.ui.model.MobileModel
import com.scb.mobilephone.ui.model.PREFS_KEY_ID
import com.scb.mobilephone.ui.model.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoriteFragment(private val noti: OnClickFavListener) : Fragment(),
    OnSortClickListener {
    override fun sortlowtoheight() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sorthighttolow() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortrating() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun heart() {
       setMobileAdapter(sortList)
    }

    private lateinit var rvMobile: RecyclerView
    private lateinit var moAdapter: FavoriteAdapter
    private lateinit var sortList: List<MobileModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var _view = inflater.inflate(R.layout.fragment_favorite, container, false)
        return _view
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
        var listFav = Prefs.getStringSet(PREFS_KEY_ID, mutableSetOf<String>())
        var sortListFavorite: ArrayList<MobileModel> = arrayListOf()

        for (name in list) {
            if (listFav.contains(name.id.toString())){
                sortListFavorite.add(name)
            }
        }
        moAdapter.submitList(sortListFavorite)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMobile = view.findViewById(R.id.recyclerView)
        moAdapter = FavoriteAdapter()
        rvMobile.adapter = moAdapter
        rvMobile.layoutManager = LinearLayoutManager(context)
        rvMobile.itemAnimator = DefaultItemAnimator()

        loadSongs()
    }

    private fun loadSongs()  {
        ApiManager.mobileService.mobile().enqueue(songListCallback)

    }


}
