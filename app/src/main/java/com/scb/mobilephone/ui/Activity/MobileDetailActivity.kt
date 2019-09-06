package com.scb.mobilephone.ui.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.scb.mobilephone.ui.Service.ApiManager
import com.scb.mobilephone.ui.model.Pictures
import com.scb.mobilephone.R
import com.scb.mobilephone.ui.adapter.PhotoPagerAdapter
import com.scb.mobilephone.ui.model.MobileModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileDetailActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_SONG = "mobile"
        fun startActivity(context: Context, mobile: MobileModel? = null) =
            context.startActivity(Intent(context, MobileDetailActivity::class.java))
    }

    private lateinit var detailImage: ImageView
    private lateinit var detailName: TextView
    private lateinit var detailDes: TextView
    private lateinit var detailprice: TextView
    private lateinit var detailRating: TextView
    private lateinit var imageSlider: ViewPager
    private var pictures: ArrayList<Pictures> = ArrayList<Pictures>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mobile_detail)
        //actionbar
        val actionbar = supportActionBar
        //set back button
        imageSlider = findViewById(R.id.viewpager)
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        detailName = findViewById(R.id.detailName)
        detailRating = findViewById(R.id.detailRating)
        detailDes = findViewById(R.id.detailDes)
        detailprice = findViewById(R.id.detailPrice)


        val mobile = intent.getParcelableExtra<MobileModel>(EXTRA_SONG) ?: return
        showSongInformation(mobile)
    }

    private val pictureCallback = object : Callback<List<Pictures>> {
        override fun onFailure(call: Call<List<Pictures>>, t: Throwable) {
//            context?.showToast("Can not call country list $t")
            Log.d("fuengfa","size : empty")
        }

        override fun onResponse(call: Call<List<Pictures>>, response: Response<List<Pictures>>) {
//            context?.showToast("Success")
            pictures.clear()
            pictures.addAll(response.body()!!)
            Log.d("fuengfa","size : ${pictures.size}")
//            Log.d("fuengfa","size : ${newimageUrls.size}")
//            imageSlider.adapter = SliderAdapter(
//                this@MobileDetailActivity,
//                PicassoImageLoaderFactory(),
//                imageUrls = newimageUrls
//            )
//            Log.d("Print url", newimageUrls.toString())

            imageSlider.adapter = PhotoPagerAdapter(supportFragmentManager, pictures)
        }
    }


    private fun loadPictures(mobile: MobileModel) {
        ApiManager.mobileService.pictures(mobile.id).enqueue(pictureCallback)
//        addNewList(pictures)
    }

    private fun showSongInformation(mobile: MobileModel) {
        detailName.text = mobile.name
        detailDes.text = mobile.description
        detailprice.text = "Price: ${mobile.price}"
        detailRating.text = "Rating: ${mobile.rating}"
        loadPictures(mobile)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}