package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.BaseActivity
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.capitalizeString
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityAstroProfileBinding

class ProfileAstroActivity : BaseActivity() {

    private lateinit var binding: ActivityAstroProfileBinding
    var runnable: Runnable? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_astro_profile)
        val s = intent.getStringExtra("horoscope")
        if (s != null) binding.textView6.text = s
        binding.tvSignNameAstro.text = App.getZodiac()[App.SIGN].name
        runnable = Runnable {
            binding.hpBarometre.animate()
                .setDuration(30000)
                .rotationBy(360F)
                .setInterpolator(LinearInterpolator())
                .withEndAction(runnable)
                .start()
        }
        runnable!!.run()

        Utils.sttBar(this)
        binding.hpSign.setOnClickListener {
            val intent = Intent(applicationContext, ChoiceSignProfileAstroActivity::class.java)
            startActivities(arrayOf(intent))
            binding.hpSign.isEnabled = false
            val enableButton = Runnable { binding.hpSign.isEnabled = true }
            Handler(Looper.myLooper()!!).postDelayed(enableButton, 1000)
        }

        binding.hpSign.setImageResource(App.getZodiac()[App.SIGN].image3)
        App.getSign().forEach { ok ->
            if (App.getZodiac()[App.SIGN].name.equals(ok.name, ignoreCase = true)) {
                binding.astroProfileTextSign.text = "${ok.description[0]} \n\n ${ok.description[1]}"
                binding.tvSignColor.text = capitalizeString(ok.color)
                binding.tvSignElement.text =  capitalizeString(ok.element)
                var s = ""
                ok.planets.forEach {
                    s += capitalizeString(it )+ "\n"
                }


                binding.tvSignPlanet.text = s
                binding.tvSignSymbol.text =  capitalizeString(ok.symbol)
                binding.tvSignPhrase.text =  capitalizeString(ok.phrase)
                s = ""
                ok.days.forEach {
                    s +=  capitalizeString(it) + "\n"
                }
                binding.tvSignLuckyDay.text = s
                binding.tvSignPolarity.text =  capitalizeString(ok.polarity)
                binding.tvSignModality.text =  capitalizeString(ok.modality)
                binding.tvSignDates.text = ok.dates
            }
        }

        binding.astroProfileRedirChatButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "walkinsvicky@gmail.com", null
                )
            )
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            intent.putExtra(Intent.EXTRA_TEXT, "Horoscope")
            startActivity(Intent.createChooser(intent, "Choose apps to connect with us :"))
            binding.astroProfileRedirChatButton.isEnabled = false
            val enableButton = Runnable { binding.astroProfileRedirChatButton.isEnabled = true }
            Handler(Looper.myLooper()!!).postDelayed(enableButton, 1000)
        }

        binding.astroProfileBack.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }


}