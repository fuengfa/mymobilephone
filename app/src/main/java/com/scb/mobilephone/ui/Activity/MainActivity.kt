package com.scb.mobilephone.ui.Activity

import android.content.ContextWrapper
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.pixplicity.easyprefs.library.Prefs
import com.scb.mobilephone.R
import com.scb.mobilephone.ui.adapter.MobileAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnClickFavListener {

    override fun clickHeartfromMainActivity() {
        sectionsPagerAdapter.listener2!!.heart()
    }

    private lateinit var alertDialog1: AlertDialog
    var values = arrayOf<CharSequence>(" Price low to high ", " Price high to low ", " Rating 5-1 ")

    private lateinit var rvMobile: RecyclerView
    private lateinit var mobileAdapter: MobileAdapter
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, this)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        sortIt.setOnClickListener {
            CreateAlertDialogWithRadioButtonGroup() ;
        }

    }

    private fun CreateAlertDialogWithRadioButtonGroup() {
        val builder = AlertDialog.Builder(this@MainActivity)

//        builder.setTitle("Select Your Choice")

        builder.setSingleChoiceItems(values, -1, DialogInterface.OnClickListener { dialog, item ->
            when (item) {
                0 ->  sectionsPagerAdapter.listener!!.sortlowtoheight()

                1 -> sectionsPagerAdapter.listener!!.sorthighttolow()

                2 -> sectionsPagerAdapter.listener!!.sortrating()
            }
            alertDialog1.dismiss()
        })
        alertDialog1 = builder.create()
        alertDialog1.show()


    }
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