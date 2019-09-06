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
import com.scb.mobilephone.ui.adapter.FavoriteAdapter
import com.scb.mobilephone.ui.adapter.OnMobileClickListener
import com.scb.mobilephone.ui.callback.CustomItemTouchHelperCallback
import com.scb.mobilephone.ui.model.*

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

    private fun setMobileAdapter(list: List<MobileModel>){
        sortList = list
        moAdapter.submitList(sortList.filter { it.fav == 1 })
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
        loadFavoritelist()
    }

    private fun loadFavoritelist()  {
        var favList: List<MobileEntity>? = null
        val task = Runnable {
            favList = mDatabaseAdapter?.mobileDao()?.queryMobiles()
            setMobileAdapter(mobileModelMapper(favList ?: listOf()))
        }
        cmWorkerThread.postTask(task)
    }

    private fun mobileModelMapper(entity: List<MobileEntity>): ArrayList<MobileModel> {
        val  mobileModelList = arrayListOf<MobileModel>()

        entity.forEach{
            mobileModelList.add(it.transformToMobileModel())
        }
        return mobileModelList
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
