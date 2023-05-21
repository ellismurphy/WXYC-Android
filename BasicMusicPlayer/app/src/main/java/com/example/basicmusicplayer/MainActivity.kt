package com.example.basicmusicplayer

import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.webkit.WebViewClient
import android.media.MediaPlayer
import android.media.AudioAttributes
import android.provider.MediaStore.Audio
import android.widget.ProgressBar
import android.view.View
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.io.IOException

//define main activity class and define properties
class MainActivity : AppCompatActivity() {
    private lateinit var loadingIndicator: ProgressBar;
    private lateinit var btnPlayAudio : Button;
    private lateinit var btnPauseAudio : Button;
    private var mediaPlayer : MediaPlayer? = null;



    // initializes the activity and sets the layout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialization of properties
        loadingIndicator = findViewById(R.id.loading_indicator)
        loadingIndicator.visibility = View.INVISIBLE
        btnPauseAudio = findViewById(R.id.btnPauseAudio)
        btnPlayAudio = findViewById(R.id.btnPlayAudio)

        // listens for play button to be clicked. if clicked, playAudio() is executed
        btnPlayAudio.setOnClickListener {
            playAudio()
        }
        // listens for play button to be clicked. if clicked, pauseAudio() is executed
        btnPauseAudio.setOnClickListener {
            pauseAudio()
        }
    }

    private fun playAudio() {
        val wxycURL = "http://audio-mp3.ibiblio.org:8000/wxyc-alt.mp3"

        mediaPlayer = MediaPlayer()

        //sets the AudioAttributes to be used for the mediaPlayer
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        //the double exclamation marks call the non-null assertion operator
        //meaning that it asserts a nullable expression is not null
        mediaPlayer!!.setAudioAttributes(audioAttributes)

        // try and catch statements to catch potential errors in running the media player
        try {
            loadingIndicator.visibility = View.VISIBLE
            mediaPlayer!!.setDataSource(wxycURL)
            mediaPlayer!!.prepareAsync()
            mediaPlayer!!.setOnPreparedListener { mp ->
                // Called when the audio is prepared and ready to play
                mp.start()
                loadingIndicator.visibility = View.GONE // Hide the loading indicator
                Toast.makeText(this, "Audio started playing", Toast.LENGTH_LONG).show()
            }
        }catch (e : IOException){
            e.printStackTrace()
        }
    }

    private fun pauseAudio() {
        if (mediaPlayer!!.isPlaying){
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
            Toast.makeText(this, "Audio has been paused", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Audio has not played", Toast.LENGTH_LONG).show()
        }
    }
}