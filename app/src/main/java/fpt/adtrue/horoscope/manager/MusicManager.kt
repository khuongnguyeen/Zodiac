package fpt.adtrue.horoscope.manager

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import fpt.adtrue.horoscope.R

class MusicManager(val context: Context) {


    private var mp: MediaPlayer? = null

    fun setData(path: Int) {
        stop()
        release()
        mp = MediaPlayer.create(context,path)
        play()
        mp?.isLooping = true
    }

    fun play() = mp?.start()

    fun pause() = mp?.pause()

    fun stop() = mp?.stop()

    fun release() = mp?.release()
}