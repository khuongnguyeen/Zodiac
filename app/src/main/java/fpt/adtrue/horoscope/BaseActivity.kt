package fpt.adtrue.horoscope

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import fpt.adtrue.horoscope.application.App.Companion.SETTING
import fpt.adtrue.horoscope.broadcast.NotificationActionService
import fpt.adtrue.horoscope.manager.HoroscopeService

open class BaseActivity : AppCompatActivity() {

    private var conn: ServiceConnection? = null

    companion object {
        var service: HoroscopeService? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createConnectService()
    }

    override fun onStop() {
        super.onStop()
        Log.e("MainActivity", "___________________ onStop() _________")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity", "___________________ onDestroy() _________")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("MainActivity", "___________________ onRestart() _________")
    }

    override fun onResume() {
        super.onResume()
        if (!SETTING) {

            val intent = Intent(
                applicationContext,
                NotificationActionService::class.java
            ).setAction("REPLAY")
            applicationContext!!.sendBroadcast(intent)
        }
        Log.e("MainActivity", "___________________ onResume() _________")

    }

    override fun onPause() {
        super.onPause()
        if (!SETTING) {
            val intent =
                Intent(applicationContext, NotificationActionService::class.java).setAction("STOP")
            applicationContext!!.sendBroadcast(intent)
        }

        Log.e("MainActivity", "___________________ onPause() _________")
    }

    override fun onStart() {
        super.onStart()
        Log.e("MainActivity", "___________________ onStart() _________")
    }

    private fun createConnectService() {
        conn = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, binder: IBinder) {
                val myBinder = binder as HoroscopeService.MyBinder
                service = myBinder.service
            }
        }
        val intent = Intent()
        intent.setClass(applicationContext!!, HoroscopeService::class.java)
        applicationContext!!.bindService(intent, conn!!, Context.BIND_AUTO_CREATE)
    }

}