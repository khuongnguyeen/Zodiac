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
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.application.App.Companion.SIGN
import fpt.adtrue.horoscope.application.App.Companion.getViewModel
import fpt.adtrue.horoscope.application.App.Companion.getZodiac
import fpt.adtrue.horoscope.databinding.ActivityWhatIsMySignBinding
import fpt.adtrue.horoscope.databinding.ActivityWhatSignBinding
import java.util.*


class WhatIsMySignActivity : BaseActivity(){

    private lateinit var binding: ActivityWhatSignBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_what_sign)

        binding.wimsValidate.setOnClickListener {
            val day = binding.dp.day
            val mon = binding.dp.month
            SIGN = checkDate(mon, day)
            getViewModel().getHoroscope(getZodiac()[SIGN].name, "yesterday")
            getViewModel().getHoroscope(getZodiac()[SIGN].name, "today")
            getViewModel().getHoroscope(getZodiac()[SIGN].name, "tomorrow")
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivities(arrayOf(intent))

        }

        sttBar(this)

        binding.wimsBack.setOnClickListener {
            onBackPressed()
        }
    }
}