package fpt.adtrue.horoscope

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    override fun onStop() {
        super.onStop()
        Log.e("MainActivity","___________________ onStop() _________")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity","___________________ onDestroy() _________")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("MainActivity","___________________ onRestart() _________")
    }

    override fun onResume() {
        super.onResume()
        Log.e("MainActivity","___________________ onResume() _________")

    }

    override fun onStart() {
        super.onStart()
        Log.e("MainActivity","___________________ onStart() _________")
    }
}