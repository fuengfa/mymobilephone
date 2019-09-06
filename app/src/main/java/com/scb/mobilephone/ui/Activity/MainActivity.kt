package com.scb.mobilephone.ui.Activity

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.scb.mobilephone.R
import com.scb.mobilephone.ui.model.AppDatbase
import com.scb.mobilephone.ui.model.CMWorkerThread
import com.scb.mobilephone.ui.model.MobileModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), MainInterface {

    private lateinit var mCMWorkerThread: CMWorkerThread
    private val presenter: MainPresenter = MainPresenter(this)
    private var mDatabaseAdapter: AppDatbase? = null
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var alertDialog1: AlertDialog
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialValue()
        createSectionPageAgapter()
        sortIt.setOnClickListener {
            alertDialog1.show()
        }
        setupDatabase()
        setupWorkerThread()
    }

    private fun setupWorkerThread() {
        mCMWorkerThread = CMWorkerThread("scb_database").also {
            it.start()
        }
    }

    private fun setupDatabase() {
        mDatabaseAdapter = AppDatbase.getInstance(this).also {
            it.openHelper.readableDatabase
        }
    }

    override fun sortLowToHeight() {
        sectionsPagerAdapter.listener?.sortlowtoheight()
        sectionsPagerAdapter.listener2?.sortlowtoheight()
        alertDialog1.dismiss()
    }

    override fun sortHightToLow() {
        sectionsPagerAdapter.listener?.sorthighttolow()
        sectionsPagerAdapter.listener2?.sorthighttolow()
        alertDialog1.dismiss()
    }

    override fun sortRating() {
        sectionsPagerAdapter.listener?.sortrating()
        sectionsPagerAdapter.listener2?.sortrating()
        alertDialog1.dismiss()
    }

    fun initialValue(){
        alertDialog1 = presenter.creatDialog(this)
        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)
    }


    fun createSectionPageAgapter() {
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    fun clickHeartfromMainActivity(mobileList: List<MobileModel>) {
        sectionsPagerAdapter.listener2?.heart(mobileList)
        sectionsPagerAdapter.listener?.heart(mobileList)
    }
}

interface MainInterface{
    fun sortLowToHeight()
    fun sortHightToLow()
    fun sortRating()
}

interface OnSortClickListener{
    fun sortlowtoheight()
    fun sorthighttolow()
    fun sortrating()
    fun heart(mobileList: List<MobileModel>)
}

interface OnClickFavListener{
    fun clickHeartfromMainActivity(mobileList: List<MobileModel>)
}

