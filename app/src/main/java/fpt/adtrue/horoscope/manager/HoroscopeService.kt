package fpt.adtrue.horoscope.manager

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import fpt.adtrue.horoscope.R

class HoroscopeService: Service(){

    private val mManager = MusicManager(this)

    class MyBinder(val service: HoroscopeService) : Binder()

    override fun onBind(intent: Intent?): IBinder {
        return MyBinder(this)
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(broadcastReceiver, IntentFilter("TRACKS_TRACKS"))
    }

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.extras!!.getString("actionName")!!) {
                "PLAY" -> {
                    mManager.setData(R.raw.bg)
                }
                "REPLAY" -> {
                    mManager.play()
                }
                "STOP" -> {
                    mManager.pause()
                }

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }


}