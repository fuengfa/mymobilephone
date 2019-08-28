package com.scb.mobilephone

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MobileActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_SONG = "song"

        private const val DATE_FORMAT_ISO_8601 = "YYYY-MM-dd'T'HH:mm:ss'Z'"
        private const val DATE_FORMAT_DATE_ONLY = "YYYY-MM-dd"

        fun startActivity(context: Context, song: MobileModel? = null) =
            context.startActivity(Intent(context, MobileActivity::class.java))
    }

    private lateinit var ivSongArtWork: ImageView
    private lateinit var tvSongName: TextView
    private lateinit var tvSongDescription: TextView
    private lateinit var tvSongPrice: TextView
    private lateinit var tvSongRating: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        tvSongName = findViewById(R.id.mobileModel)

        val song = intent.getParcelableExtra<MobileModel>(EXTRA_SONG) ?: return
        showSongInformation(song)
    }

    private fun showSongInformation(song: MobileModel) {
        tvSongName.text = song.name
    }
}
