package com.scb.mobilephone.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scb.mobilephone.*
import com.scb.mobilephone.ui.Activity.MainActivity
import com.scb.mobilephone.ui.Activity.OnClickFavListener
import com.scb.mobilephone.ui.Activity.OnSortClickListener
import com.scb.mobilephone.ui.Service.ApiManager
import com.scb.mobilephone.ui.adapter.FavoriteAdapter
import com.scb.mobilephone.ui.adapter.OnMobileClickListener
import com.scb.mobilephone.ui.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteFragment: Fragment(),
    OnSortClickListener, OnMobileClickListener , OnClickFavListener{
    override fun clickHeartfromMainActivity(mobileList: List<MobileModel>) {
        Log.d("pfromMainclick","wowwww")
        var m : ArrayList<MobileModel> = arrayListOf()
        var favlist: MobileEntity?
        for (list in sortList) {
            var task2 = Runnable {
                favlist = mDatabaseAdapter?.mobileDao()?.queryMobile(list.id)

                if (favlist != null) {
                    m.add(list)
                }
            }
            cmWorkerThread.postTask(task2)
        }
        moAdapter.submitList(m)
    }

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
            Log.d("pfromResponse","clickHeart")
            sortList = response.body()!!
            var  m: ArrayList<MobileModel> = arrayListOf()
            var favlist: MobileEntity?
            for (list in sortList) {
                var task = Runnable {
                    favlist = mDatabaseAdapter?.mobileDao()?.queryMobile(list.id)
                    if (favlist != null) {
                        m.add(list)
                    }
                }
                cmWorkerThread.postTask(task)
            }
            setMobileAdapter(m)
//            setMobileAdapter(sortList)
        }
    }

    private fun setMobileAdapter(list: List<MobileModel>){
        Log.d("plist-fromsetAdap", list.toString())
        //sortList = list
        moAdapter.submitList(list.filter { it.fav == 1 })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMobile = view.findViewById(R.id.recyclerView)
        moAdapter = FavoriteAdapter(this)
        rvMobile.adapter = moAdapter
        rvMobile.layoutManager = LinearLayoutManager(view.context)
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

    override fun heart(mobileList: List<MobileModel>) {
        Log.d("pfromOnHeartclick","wowwww")
        sortList = mobileList
        setMobileAdapter(sortList)
        sortList = mobileList
    }

    private fun loadSongs()  {
        ApiManager.mobileService.mobile().enqueue(songListCallback)

    }

    override fun onMobileClick(mobile: MobileModel, view: View) {

    }

    override fun onHeartClick(mobile: MobileModel) {
        var task = Runnable {
            if(mobile.fav == 1){
                mDatabaseAdapter?.mobileDao()!!.addMobile(MobileEntity(mobile.id,mobile.name, mobile.description, mobile.brand,
                    mobile.price, mobile.rating, mobile.thumbImageURL, mobile.fav))
            } else {
                mDatabaseAdapter?.mobileDao()!!.deleteMobilebyID(mobile.id)
            }
        }
        cmWorkerThread.postTask(task)

//        sortList.single { it.id == mobile.id }.fav = mobile.fav

        (context as? MainActivity)?.clickHeartfromMainActivity(sortList)
    }

}
