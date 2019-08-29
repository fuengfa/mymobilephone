package com.scb.mobilephone

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import android.widget.Toast
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    private lateinit var alertDialog1: AlertDialog
    var values = arrayOf<CharSequence>(" Price low to high ", " Price high to low ", " Rating 5-1 ")

    private lateinit var rvMobile: RecyclerView
    private lateinit var mobileAdapter: MobileAdapter
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
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
                0 ->  {

                    sectionsPagerAdapter.listener!!.sortlowtoheight()}

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
}