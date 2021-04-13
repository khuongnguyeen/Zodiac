package fpt.adtrue.horoscope.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.SignAdapter
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.setDataLocal
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivitySelectSignBinding

class ChoiceSignProfileAstroActivity: AppCompatActivity(){

    private lateinit var binding: ActivitySelectSignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_sign)
        binding.gridSign.adapter = SignAdapter(context = applicationContext)
        binding.gridSign.setOnItemClickListener { _, _, _, id ->
            App.SIGN = id.toInt()
            setDataLocal(App.SIGN, applicationContext)
            val intent = Intent(applicationContext, ProfileAstroActivity::class.java)
            startActivities(arrayOf(intent))
        }
        sttBar(this)
        binding.choiceSignGoWims.setOnClickListener {
            setDataLocal(App.SIGN, applicationContext)
            val intent = Intent(applicationContext, ChoiceDate::class.java)
            startActivities(arrayOf(intent))
        }

        binding.choiceSignBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

}