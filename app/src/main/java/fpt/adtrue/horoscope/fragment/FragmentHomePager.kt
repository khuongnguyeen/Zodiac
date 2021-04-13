package fpt.adtrue.horoscope.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import fpt.adtrue.horoscope.activity.ChoiceCompatActivity
import fpt.adtrue.horoscope.activity.ProfileAstroActivity
import fpt.adtrue.horoscope.activity.StartReadingTarotActivity
import fpt.adtrue.horoscope.api.HoroscopeApi
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.PagerItemRecyclerviewBinding
import fpt.adtrue.horoscope.model.DataHoroscope

class FragmentHomePager(private val position: Int) : Fragment() {

    private lateinit var binding: PagerItemRecyclerviewBinding
    private var runnable: Runnable? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PagerItemRecyclerviewBinding.inflate(inflater, container, false)
        runnable = Runnable {
            binding.hpBarometre.animate()
                .setDuration(30000)
                .rotationBy(360F)
                .setInterpolator(LinearInterpolator())
                .withEndAction(runnable)
                .start()
        }
        runnable!!.run()
        binding.data = App.getViewModel()
        binding.hpSign.setImageResource(App.getZodiac()[App.SIGN].image2)

        if (position == 0) {
            App.getTarot2().observe(this, { ok ->
                ok.daily_horoscope.forEach {
                    if (it.name.equals(App.getZodiac()[App.SIGN].name, true)) {
                        binding.tvDescription.text = it.sign.general
                        binding.tvDescriptLove.text = it.sign.love
                        binding.tvDescriptWork.text = it.sign.work
                        binding.tvDescriptMoney.text = it.sign.money
                    }
                }
            })
            App.getViewModel().data2.observe(this, {
                updateData(it)
            })
        }

        if (position == 1) {
            App.getTarot3().observe(this, { ok ->
                ok.daily_horoscope.forEach {
                    if (it.name.equals(App.getZodiac()[App.SIGN].name, true)) {
                        binding.tvDescription.text = it.sign.general
                        binding.tvDescriptLove.text = it.sign.love
                        binding.tvDescriptWork.text = it.sign.work
                        binding.tvDescriptMoney.text = it.sign.money
                    }
                }
            })
            App.getViewModel().data.observe(this, {
                updateData(it)
            })
        }

        if (position == 2) {
            App.getTarot4().observe(this, { ok ->
                ok.daily_horoscope.forEach {
                    if (it.name.equals(App.getZodiac()[App.SIGN].name, true)) {
                        binding.tvDescription.text = it.sign.general
                        binding.tvDescriptLove.text = it.sign.love
                        binding.tvDescriptWork.text = it.sign.work
                        binding.tvDescriptMoney.text = it.sign.money
                    }
                }
            })
            App.getViewModel().data1.observe(this, {
                updateData(it)
            })
        }

        binding.homepageRedirHoroperso.setOnClickListener {
            val intent = Intent(context, ProfileAstroActivity::class.java)
            context!!.startActivities(arrayOf(intent))
            binding.homepageRedirCompat.isEnabled = false
            val enableButton = Runnable { binding.homepageRedirCompat.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }

        binding.homepageTarot.setOnClickListener {
            StartReadingTarotActivity.start(context!!)
        }

        binding.homepageRedirChatButton.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "walkinsvicky@gmail.com", null
                )
            )
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
            intent.putExtra(Intent.EXTRA_TEXT, "Horoscope")
            startActivity(Intent.createChooser(intent, "Choose apps to connect with us :"))
            binding.homepageRedirChatButton.isEnabled = false
            val enableButton = Runnable { binding.homepageRedirChatButton.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }

        binding.homepageRedirCompat.setOnClickListener {
            val intent = Intent(context, ChoiceCompatActivity::class.java)
            context!!.startActivities(arrayOf(intent))
            binding.homepageRedirCompat.isEnabled = false
            val enableButton = Runnable { binding.homepageRedirCompat.isEnabled = true }
            Handler().postDelayed(enableButton, 1000)
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateData(it: DataHoroscope) {
        binding.homepageRecap.text = "${App.getZodiac()[App.SIGN].name} - ${it.currentDate}"
        binding.tvDescriptionCompatibility.text = it.compatibility
        binding.tvDescriptionMood.text = it.mood
        binding.tvDescriptionColor.text = it.color
        binding.tvDescriptionLuckyNumber.text = it.luckyNumber
        binding.tvDescriptionLuckyTime.text = it.luckyTime
    }

}