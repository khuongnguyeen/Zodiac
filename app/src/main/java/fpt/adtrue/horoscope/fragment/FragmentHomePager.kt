package fpt.adtrue.horoscope.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import fpt.adtrue.horoscope.activity.ChoiceCompatActivity
import fpt.adtrue.horoscope.activity.ProfileAstroActivity
import fpt.adtrue.horoscope.activity.StartReadingTarotActivity
import fpt.adtrue.horoscope.api.HoroscopeApi
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.PagerItemRecyclerviewBinding
import fpt.adtrue.horoscope.model.DataAmazonaws
import fpt.adtrue.horoscope.model.DataHoroscope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class FragmentHomePager(private val position: Int) : Fragment() {

    private lateinit var binding: PagerItemRecyclerviewBinding
    private var runnable: Runnable? = null
    val data = MutableLiveData<DataAmazonaws>()
    val data1 = MutableLiveData<DataAmazonaws>()
    val data2 = MutableLiveData<DataAmazonaws>()
    private val horoscopeApi2: HoroscopeApi = Utils.createRetrofit2()
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


        val calendar = Calendar.getInstance(TimeZone.getDefault()) as Calendar
        var mon = "${calendar.get(Calendar.MONTH) + 1}"
        var day = "${calendar.get(Calendar.DAY_OF_MONTH)}"

        var yesterday = "${calendar.get(Calendar.DAY_OF_MONTH) - 1}"
        var tomorrow = "${calendar.get(Calendar.DAY_OF_MONTH) + 1}"

        val res = calendar.getActualMaximum(Calendar.DATE)
        if (day.toInt() == res) {
            tomorrow = "1"
        }
        if (day.toInt() == 1) {
            yesterday = "${calendar.getActualMaximum(Calendar.DATE - 1)}"
        }
        if (mon.toInt() < 10) {
            mon = "0$mon"
        }
        if (day.toInt() < 10) {
            day = "0$day"
        }
        if (yesterday.toInt() < 10) {
            yesterday = "0$yesterday"
        }
        if (tomorrow.toInt() < 10) {
            tomorrow = "0$tomorrow"
        }













        if (position == 0) {

            getAmazon(yesterday, mon, calendar.get(Calendar.YEAR), data)
            data.observe(this, {
                it.daily_horoscope.forEach {
                    if (it.name.equals(App.getZodiac()[App.SIGN].name, true)) {
                        binding.tvDescription.text = it.sign.general
                        binding.tvDescriptLove.text = it.sign.love
                        binding.tvDescriptWork.text = it.sign.work
                        binding.tvDescriptMoney.text = it.sign.money
                    }
                }


            })


            print("ok")
            App.getViewModel().data2.observe(this, {
                updateData(it)
            })
        }
        if (position == 1) {
            getAmazon(day, mon, calendar.get(Calendar.YEAR), data1)
            data1.observe(this, {
                it.daily_horoscope.forEach {
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
            getAmazon(tomorrow, mon, calendar.get(Calendar.YEAR), data2)
            data2.observe(this, {
                it.daily_horoscope.forEach {
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

//        homepage_redir_compat
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


    @SuppressLint("CheckResult")
    fun getAmazon(day: String, mon: String, year: Int, data: MutableLiveData<DataAmazonaws>) {
        horoscopeApi2.getDataAmazon(day, mon, year)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    data.value = it
                    Log.e("getAmazon__________", Gson().toJson(it))
                },
                {
                    Log.e("getAmazon", Gson().toJson(it))
                })
    }

}