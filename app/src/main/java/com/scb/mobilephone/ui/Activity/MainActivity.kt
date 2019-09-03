package com.scb.mobilephone.ui.Activity

import android.app.AlertDialog
import android.content.ContextWrapper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.pixplicity.easyprefs.library.Prefs
import com.scb.mobilephone.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), OnClickFavListener, MainInterface {
    override fun sortLowToHeight() {
        sectionsPagerAdapter.listener?.sortlowtoheight()
        alertDialog1.dismiss()
    }

    override fun sortHightToLow() {
        sectionsPagerAdapter.listener?.sorthighttolow()
        alertDialog1.dismiss()
    }

    override fun sortRating() {
        sectionsPagerAdapter.listener?.sortrating()
        alertDialog1.dismiss()
    }

    private val presenter: MainPresenter = MainPresenter(this)

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var alertDialog1: AlertDialog
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init()
        initialValue()
        createSectionPageAgapter()
        alertDialog1 = presenter.creatDialog(this)
        sortIt.setOnClickListener { alertDialog1.show() }
    }

    fun initialValue(){
        viewPager = findViewById(R.id.view_pager)
        tabs = findViewById(R.id.tabs)
    }

    override fun createPref() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

    fun createSectionPageAgapter() {
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, this)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun clickHeartfromMainActivity() {
        sectionsPagerAdapter.listener2!!.heart()
    }
}

interface MainInterface{
    fun createPref()
    fun sortLowToHeight()
    fun sortHightToLow()
    fun sortRating()
}

interface OnSortClickListener{
    fun sortlowtoheight()
    fun sorthighttolow()
    fun sortrating()
    fun heart()
}

interface OnClickFavListener{
    fun clickHeartfromMainActivity()
}

