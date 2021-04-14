package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.BaseActivity
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.checkDate
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityWhatIsMySignBinding
import java.util.*


class WhatIsMySignActivity : BaseActivity(){

    private lateinit var binding: ActivityWhatIsMySignBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_what_is_my_sign)

        binding.wimsValidate.setOnClickListener {
            val day = binding.dp.day
            val mon = binding.dp.month
            App.SIGN = checkDate(mon, day)
            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name, "yesterday")
            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name, "today")
            App.getViewModel().getHoroscope(App.getZodiac()[App.SIGN].name, "tomorrow")
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivities(arrayOf(intent))

        }

        Utils.sttBar(this)

        binding.wimsBack.setOnClickListener {
            onBackPressed()
        }
    }
}