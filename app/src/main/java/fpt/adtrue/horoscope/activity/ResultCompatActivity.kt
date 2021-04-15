package fpt.adtrue.horoscope.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.net.Uri.fromParts
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import fpt.adtrue.horoscope.BaseActivity
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.application.App.Companion.HER
import fpt.adtrue.horoscope.application.App.Companion.SIGN
import fpt.adtrue.horoscope.application.App.Companion.getCom
import fpt.adtrue.horoscope.application.App.Companion.getZodiac
import fpt.adtrue.horoscope.databinding.ActivityCompatResultsBinding
import java.util.*

class ResultCompatActivity : BaseActivity() {

    private lateinit var binding: ActivityCompatResultsBinding
    private var mCountDownTimer: CountDownTimer? = null
    private var runnable: Runnable? = null

    @SuppressLint("SetTextI18n", "NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compat_results)

        sttBar(this)
        runnable = Runnable {
            binding.compatBaro.animate()
                .setDuration(30000)
                .rotationBy(360F)
                .setInterpolator(LinearInterpolator())
                .withEndAction(runnable)
                .start()
        }
        runnable!!.run()

        mCountDownTimer = object : CountDownTimer(7000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                binding.rlBackground.visibility = View.GONE
            }
        }.start()
        binding.compatResultsSignLeftImg.setImageResource(getZodiac()[SIGN].image2)
        binding.compatResultsSignRightImg.setImageResource(getZodiac()[HER].image2)
        binding.ringProgress.max = 100


        getCom().forEach {
            if ("${getZodiac()[SIGN].name}:${getZodiac()[HER].name}".toLowerCase(
                    Locale.ROOT
                ).equals(it.key, ignoreCase = false)
            ) {
                binding.compatResultsPercent.text = "${it.compatibility.percent}%"
                binding.ringProgress.progress = it.compatibility.percent

                val progressAnimator = ObjectAnimator.ofInt(binding.ringProgress, "progress", 0, it.compatibility.percent)
                progressAnimator.duration = 2000
                progressAnimator.start()
                binding.tvLove.text = "${it.compatibility.categories[0].percent}%"
                binding.tvSex.text = "${it.compatibility.categories[1].percent}%"
                binding.tvFamily.text = "${it.compatibility.categories[2].percent}%"
                binding.tvFriendship.text = "${it.compatibility.categories[3].percent}%"
                binding.tvBusiness.text = "${it.compatibility.categories[4].percent}%"
                binding.pbLove.progress = it.compatibility.categories[0].percent
                val progressAnimator1 = ObjectAnimator.ofInt(binding.pbLove, "progress", 0, it.compatibility.categories[0].percent)
                progressAnimator1.duration = 2000
                progressAnimator1.start()
                binding.pbSex.progress = it.compatibility.categories[1].percent
                val progressAnimator2 = ObjectAnimator.ofInt(binding.pbSex, "progress", 0, it.compatibility.categories[1].percent)
                progressAnimator2.duration = 2000
                progressAnimator2.start()
                binding.pbFamily.progress = it.compatibility.categories[2].percent
                val progressAnimator3 = ObjectAnimator.ofInt(binding.pbFamily, "progress", 0, it.compatibility.categories[2].percent)
                progressAnimator3.duration = 2000
                progressAnimator3.start()
                binding.pbFriendship.progress = it.compatibility.categories[3].percent
                val progressAnimator4 = ObjectAnimator.ofInt(binding.pbFriendship, "progress", 0,it.compatibility.categories[3].percent)
                progressAnimator4.duration = 2000
                progressAnimator4.start()
                binding.pbBusiness.progress = it.compatibility.categories[4].percent
                val progressAnimator5 = ObjectAnimator.ofInt(binding.pbBusiness, "progress", 0,it.compatibility.categories[4].percent)
                progressAnimator5.duration = 2000
                progressAnimator5.start()
                binding.overall.text = it.compatibility.overall_description
                binding.tvValues.text = it.compatibility.values_description
                binding.tvLoveDetail.text = it.compatibility.love_description
                binding.de.text = it.compatibility.description
            }
        }

        binding.compatResultsAgain.setOnClickListener {
            onBackPressed()
        }


//        compat_results_close
        binding.compatResultsClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}