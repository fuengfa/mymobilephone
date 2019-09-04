package com.scb.mobilephone.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.scb.mobilephone.*
import com.scb.mobilephone.ui.Activity.OnClickFavListener
import com.scb.mobilephone.ui.Activity.OnSortClickListener
import com.scb.mobilephone.ui.fragment.FavoriteFragment
import com.scb.mobilephone.ui.fragment.MobileFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var listener: OnSortClickListener? = null
    var listener2: OnSortClickListener? = null



    override fun getItem(position: Int): Fragment {
        when(position){
            0 ->{
                var mbfragment = MobileFragment()
                listener = mbfragment
                return mbfragment
            }
            else ->{
                var mfragment = FavoriteFragment()
                listener2 = mfragment
                return mfragment
            }


        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return TAB_TITLES.count()
    }
}