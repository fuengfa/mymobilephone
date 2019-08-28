package com.scb.mobilephone

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.scb.mobilephone.ui.main.SectionsPagerAdapter
import android.widget.Toast
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var alertDialog1: AlertDialog
    var values = arrayOf<CharSequence>(" First Item ", " Second Item ", " Third Item ")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
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

        builder.setTitle("Select Your Choice")

        builder.setSingleChoiceItems(values, -1, DialogInterface.OnClickListener { dialog, item ->
            when (item) {
                0 ->

                    Toast.makeText(this@MainActivity, "First Item Clicked", Toast.LENGTH_LONG).show()
                1 ->

                    Toast.makeText(this@MainActivity, "Second Item Clicked", Toast.LENGTH_LONG).show()
                2 ->

                    Toast.makeText(this@MainActivity, "Third Item Clicked", Toast.LENGTH_LONG).show()
            }
            alertDialog1.dismiss()
        })
        alertDialog1 = builder.create()
        alertDialog1.show()

    }
}