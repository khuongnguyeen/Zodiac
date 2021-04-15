package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.GONE
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fpt.adtrue.horoscope.BaseActivity
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.HoroscopeApi
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.createRetrofit2
import fpt.adtrue.horoscope.api.Utils.getDataLocal
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.application.App.Companion.SETTING
import fpt.adtrue.horoscope.application.App.Companion.SIGN
import fpt.adtrue.horoscope.application.App.Companion.getTarot2
import fpt.adtrue.horoscope.application.App.Companion.getTarot3
import fpt.adtrue.horoscope.application.App.Companion.getTarot4
import fpt.adtrue.horoscope.application.App.Companion.getViewModel
import fpt.adtrue.horoscope.application.App.Companion.getZodiac
import fpt.adtrue.horoscope.broadcast.NotificationActionService
import fpt.adtrue.horoscope.databinding.ActivityStartBinding
import fpt.adtrue.horoscope.fragment.ChooseSign
import fpt.adtrue.horoscope.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.*
import java.lang.reflect.Type
import java.util.*
import java.util.Calendar.*


@Suppress("NAME_SHADOWING")
class StartActivity : BaseActivity() {

    private lateinit var binding: ActivityStartBinding
    private val horoscopeApi2: HoroscopeApi = createRetrofit2()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataLocal(applicationContext)
        if (!SETTING) {
            val intent = Intent(applicationContext, NotificationActionService::class.java).setAction("PLAY")
            applicationContext!!.sendBroadcast(intent)
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        getSign()
        getCompatibility()
        sttBar(this)
        getDaSi()
        getTarot()
        addImgTarot()
        val enableButton = Runnable {
            if (SIGN == 100) {
                binding.logo.visibility = GONE
                val fr1 = ChooseSign()
                val active: Fragment = fr1
                val manager = supportFragmentManager
                manager.beginTransaction().hide(active).show(fr1).commit()
                manager.beginTransaction().add(R.id.content, fr1, "1").commit()
            } else {
                getViewModel().getHoroscope(getZodiac()[SIGN].name, "yesterday")
                getViewModel().getHoroscope(getZodiac()[SIGN].name, "today")
                getViewModel().getHoroscope(getZodiac()[SIGN].name, "tomorrow")
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivities(arrayOf(intent))
            }
        }
        Handler().postDelayed(enableButton, 2000)
        val calendar = getInstance(TimeZone.getDefault()) as Calendar
        var mon = "${calendar.get(MONTH) + 1}"
        var day = "${calendar.get(DAY_OF_MONTH)}"
        var yesterday = "${calendar.get(DAY_OF_MONTH) - 1}"
        var tomorrow = "${calendar.get(DAY_OF_MONTH) + 1}"
        val res = calendar.getActualMaximum(DATE)
        if (day.toInt() == res) {
            tomorrow = "1"
            mon = "${calendar.get(MONTH) + 2}"
        }
        if (day.toInt() == 1) {
            yesterday = "${calendar.getActualMaximum(DATE - 1)}"
            mon = "${calendar.get(MONTH)}"
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
        getAmazon(yesterday, mon, calendar.get(YEAR), getTarot2())
        getAmazon(day, mon, calendar.get(YEAR), getTarot3())
        getAmazon(tomorrow, mon, calendar.get(YEAR), getTarot4())
//        val notifyMe = NotifyMe.Builder(applicationContext)
//        notifyMe.title("Horoscope")
//        notifyMe.content(String content)
//        notifyMe.color(Int red,Int green,Int blue,Int alpha)
//        notifyMe.led_color(Int red,Int green,Int blue,Int alpha)
//        notification pops up
//        notifyMe.time(Calendar time)
//        notifyMe.delay(Int delay)
//        notifyMe.large_icon(Int resource)
//        notifyMe.rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
//        notifyMe.addAction(Intent intent,String text)
    }

    @SuppressLint("CheckResult")
    fun getAmazon(day: String, mon: String, year: Int, data: MutableLiveData<DataAmazonaws>) {
        getViewModel().isLoading.set(true)
        horoscopeApi2.getDataAmazon(day, mon, year)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    data.value = it
                    Log.e("getAmazon__________", Gson().toJson(it))
                    getViewModel().isLoading.set(false)
                },
                {
                    Log.e("getAmazon", Gson().toJson(it))

                    getViewModel().isLoading.set(false)
                })
    }


    private fun getCompatibility() {
        val iS: InputStream = resources.openRawResource(R.raw.zodiac_compatibility)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        iS.use { iS ->
            val reader: Reader = BufferedReader(InputStreamReader(iS, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        val jsonString: String = writer.toString()
        if (jsonString != "") {
            val gSon = Gson()
            val type: Type = object : TypeToken<MutableList<DataCompatibilityItem?>?>() {}.type
            App.getCom().clear()
            App.getCom().addAll(gSon.fromJson<MutableList<DataCompatibilityItem>>(jsonString, type))
        }
    }

    private fun addImgTarot() {
        App.getTarot().forEach {
            val a = it.name.toLowerCase(Locale.ROOT).replace(" ", "_")
            val resources: Resources = applicationContext.resources
            val resourceId: Int = resources.getIdentifier(a, "drawable", applicationContext.packageName)
            it.img = resourceId
        }
    }

    private fun getSign() {
        val iS: InputStream = resources.openRawResource(R.raw.zodiac_signs)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        iS.use { iS ->
            val reader: Reader = BufferedReader(InputStreamReader(iS, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        val jsonString: String = writer.toString()
        if (jsonString != "") {
            val gSon = Gson()
            val type: Type = object : TypeToken<MutableList<DataSignItem?>?>() {}.type
            App.getSign().clear()
            App.getSign().addAll(gSon.fromJson<MutableList<DataSignItem>>(jsonString, type))
        }
    }

    private fun getTarot() {
        val iS: InputStream = resources.openRawResource(R.raw.translations_en_tarot)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        iS.use { iS ->
            val reader: Reader = BufferedReader(InputStreamReader(iS, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }
        val jsonString: String = writer.toString()
        if (jsonString != "") {
            val gSon = Gson()
            val type: Type = object : TypeToken<MutableList<DataTarot?>?>() {}.type
            App.getTarot().clear()
            App.getTarot().addAll(gSon.fromJson<MutableList<DataTarot>>(jsonString, type))
        }
        Log.e("Start Activity", "tc tr")
    }

    private fun getDaSi() {
        val v1 = DataZodiac("Aries", R.drawable.s1_wh, R.drawable.s1_hp, R.drawable.s1_or)
        val v2 = DataZodiac("Taurus", R.drawable.s2_wh, R.drawable.s2_hp, R.drawable.s2_or)
        val v3 = DataZodiac("Gemini", R.drawable.s3_wh, R.drawable.s3_hp, R.drawable.s3_or)
        val v4 = DataZodiac("Cancer", R.drawable.s4_wh, R.drawable.s4_hp, R.drawable.s4_or)
        val v5 = DataZodiac("Leo", R.drawable.s5_wh, R.drawable.s5_hp, R.drawable.s5_or)
        val v6 = DataZodiac("Virgo", R.drawable.s6_wh, R.drawable.s6_hp, R.drawable.s6_or)
        val v7 = DataZodiac("Libra", R.drawable.s7_wh, R.drawable.s7_hp, R.drawable.s7_or)
        val v8 = DataZodiac("Scorpio", R.drawable.s8_wh, R.drawable.s8_hp, R.drawable.s8_or)
        val v9 = DataZodiac("Sagittarius", R.drawable.s9_wh, R.drawable.s9_hp, R.drawable.s9_or)
        val v10 = DataZodiac("Capricorn", R.drawable.s10_wh, R.drawable.s10_hp, R.drawable.s10_or)
        val v11 = DataZodiac("Aquarius", R.drawable.s11_wh, R.drawable.s11_hp, R.drawable.s11_or)
        val v12 = DataZodiac("Pisces", R.drawable.s12_wh, R.drawable.s12_hp, R.drawable.s12_or)
        getZodiac().add(v1)
        getZodiac().add(v2)
        getZodiac().add(v3)
        getZodiac().add(v4)
        getZodiac().add(v5)
        getZodiac().add(v6)
        getZodiac().add(v7)
        getZodiac().add(v8)
        getZodiac().add(v9)
        getZodiac().add(v10)
        getZodiac().add(v11)
        getZodiac().add(v12)
    }

    override fun onBackPressed() {}

}