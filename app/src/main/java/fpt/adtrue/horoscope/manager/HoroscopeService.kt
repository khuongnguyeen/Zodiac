package fpt.adtrue.horoscope.manager

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class HoroscopeService: Service(){

    private val mManager = MusicManager(this)

    class MyBinder(val service: HoroscopeService) : Binder()

    override fun onBind(intent: Intent?): IBinder {
        return MyBinder(this)
    }


}