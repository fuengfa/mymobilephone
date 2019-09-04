package com.scb.mobilephone.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephone.*
import com.scb.mobilephone.ui.Activity.OnClickFavListener
import com.scb.mobilephone.ui.Activity.OnSortClickListener
import com.scb.mobilephone.ui.Service.ApiManager
import com.scb.mobilephone.ui.adapter.FavoriteAdapter
import com.scb.mobilephone.ui.adapter.OnMobileClickListener
import com.scb.mobilephone.ui.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment() : Fragment(),
    OnSortClickListener, OnMobileClickListener {

    private lateinit var rvMobile: RecyclerView
    private lateinit var moAdapter: FavoriteAdapter
    private lateinit var sortList: List<MobileModel>
    private var mDatabaseAdapter: AppDatbase? = null
    private var cmWorkerThread: CMWorkerThread = CMWorkerThread("favorite").also {
        it.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var _view = inflater.inflate(R.layout.fragment_favorite, container, false)
        return _view
    }

    private val songListCallback = object : Callback<List<MobileModel>> {
        override fun onFailure(call: Call<List<MobileModel>>, t: Throwable) {
            context?.showToast("Can not call country list $t")
        }

        override fun onResponse(call: Call<List<MobileModel>>, response: Response<List<MobileModel>>) {
            context?.showToast("Success")
            sortList = response.body()!!
            var newFavList:ArrayList<MobileModel>
            for (list in sortList){
                var task = Runnable {
                    var favlist = mDatabaseAdapter?.mobileDao()!!.queryMobile(list.id)
                    Log.d("fav", favlist.toString())
                    if(favlist != null){
                        Log.d("fav", "----------------------" + favlist.toString())
                        newFavList.add(favlist.toString())

                    }
                }
                cmWorkerThread.postTask(task)
            }
            setMobileAdapter(sortList)

        }
    }

    private fun setMobileAdapter(list: List<MobileModel>){
        moAdapter.submitList(list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMobile = view.findViewById(R.id.recyclerView)
        moAdapter = FavoriteAdapter(this)
        rvMobile.adapter = moAdapter
        rvMobile.layoutManager = LinearLayoutManager(context)
        rvMobile.itemAnimator = DefaultItemAnimator()

        val callback = CustomItemTouchHelperCallback(moAdapter)
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvMobile)
        mDatabaseAdapter = AppDatbase.getInstance(view.context).also {
            it.openHelper.readableDatabase
        }

        loadSongs()
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

    override fun heart() {
        setMobileAdapter(sortList)
    }


    private fun loadSongs()  {
        ApiManager.mobileService.mobile().enqueue(songListCallback)

    }

    override fun onMobileClick(mobile: MobileModel, view: View) {

    }

    override fun onHeartClick(favImage: ImageView, mobile: MobileModel) {

    }


}
