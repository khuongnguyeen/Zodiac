package fpt.adtrue.horoscope.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fpt.adtrue.horoscope.activity.ChoiceCompatActivity
import fpt.adtrue.horoscope.activity.ProfileAstroActivity
import fpt.adtrue.horoscope.activity.StartReadingTarotActivity
import fpt.adtrue.horoscope.adapter.NewApiAdapter
import fpt.adtrue.horoscope.api.HoroscopeApi
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.application.App.Companion.getTarot2
import fpt.adtrue.horoscope.application.App.Companion.getTarot3
import fpt.adtrue.horoscope.application.App.Companion.getTarot4
import fpt.adtrue.horoscope.application.App.Companion.getViewModel
import fpt.adtrue.horoscope.databinding.PagerItemRecyclerviewBinding
import fpt.adtrue.horoscope.model.DataHoroscope

class FragmentHomePager(private val position: Int) : Fragment() {

    private lateinit var binding: PagerItemRecyclerviewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PagerItemRecyclerviewBinding.inflate(inflater, container, false)
        if (position == 0) {
            getTarot2().observe(this, Observer{ ok ->
                ok.daily_horoscope.forEach {
                    if (it.name.equals(App.getZodiac()[App.SIGN].name, true)) {
                        binding.tvDescription.text = it.sign.general
                        binding.rc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                        binding.rc.adapter = NewApiAdapter(it.sign.love,it.sign.work,it.sign.money)
                    }
                }
            })
            getViewModel().data2.observe(this, Observer {
                updateData(it)
            })
        }

        if (position == 1) {
            getTarot3().observe(this, Observer { ok ->
                ok.daily_horoscope.forEach {
                    if (it.name.equals(App.getZodiac()[App.SIGN].name, true)) {
                        binding.tvDescription.text = it.sign.general
                        binding.rc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                        binding.rc.adapter = NewApiAdapter(it.sign.love,it.sign.work,it.sign.money)
                    }
                }
            })
            getViewModel().data.observe(this,Observer {
                updateData(it)
            })
        }

        if (position == 2) {
            getTarot4().observe(this,Observer { ok ->
                ok.daily_horoscope.forEach {
                    if (it.name.equals(App.getZodiac()[App.SIGN].name, true)) {
                        binding.tvDescription.text = it.sign.general
                        binding.rc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                        binding.rc.adapter = NewApiAdapter(it.sign.love,it.sign.work,it.sign.money)
                    }
                }
            })
            getViewModel().data1.observe(this,Observer {
                updateData(it)
            })
        }

        binding.cvBot2.setOnClickListener {
            val intent = Intent(context, ProfileAstroActivity::class.java)
            context!!.startActivities(arrayOf(intent))
            binding.cvBot2.isEnabled = false
            val enableButton = Runnable { binding.cvBot2.isEnabled = true }
            Handler(Looper.myLooper()!!).postDelayed(enableButton, 1000)
        }

        binding.cvBot3.setOnClickListener {
            StartReadingTarotActivity.start(context!!)
            binding.cvBot3.isEnabled = false
            val enableButton = Runnable { binding.cvBot3.isEnabled = true }
            Handler(Looper.myLooper()!!).postDelayed(enableButton, 1000)
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
            Handler(Looper.myLooper()!!).postDelayed(enableButton, 1000)
        }

        binding.dsagafh.setOnClickListener {
            val intent = Intent(context, ChoiceCompatActivity::class.java)
            context!!.startActivities(arrayOf(intent))
            binding.dsagafh.isEnabled = false
            val enableButton = Runnable { binding.dsagafh.isEnabled = true }
            Handler(Looper.myLooper()!!).postDelayed(enableButton, 1000)
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateData(it: DataHoroscope) {
        binding.tvDescriptionCompatibility.text = it.compatibility
        binding.tvDescriptionMood.text = it.mood
        binding.tvDescriptionColor.text = it.color
        binding.tvDescriptionLuckyNumber.text = it.luckyNumber
        binding.tvDescriptionLuckyTime.text = it.luckyTime
    }

}