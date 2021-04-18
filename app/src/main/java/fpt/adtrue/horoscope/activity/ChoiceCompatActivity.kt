package fpt.adtrue.horoscope.activity

import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.databinding.DataBindingUtil
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.gtomato.android.ui.transformer.FlatMerryGoRoundTransformer
import fpt.adtrue.horoscope.BaseActivity
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.SignPagerAdapter
import fpt.adtrue.horoscope.api.Utils.setDataLocal
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.application.App.Companion.HER
import fpt.adtrue.horoscope.application.App.Companion.HIM
import fpt.adtrue.horoscope.application.App.Companion.SIGN
import fpt.adtrue.horoscope.databinding.ActivityCompatChoiceBinding


class ChoiceCompatActivity : BaseActivity() {

    private lateinit var binding: ActivityCompatChoiceBinding
    private var runnable: Runnable? = null
    private var layoutManager:CarouselLayoutManager? =null
    private var layoutManager2:CarouselLayoutManager? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compat_choice)
        runnable = Runnable {
            binding.ivCenter.animate()
                .setDuration(30000)
                .rotationBy(360F)
                .setInterpolator(LinearInterpolator())
                .withEndAction(runnable)
                .start()
        }
        runnable!!.run()
//

        val b = CarouselZoomPostLayoutListener()
//
        layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)

        layoutManager!!.setPostLayoutListener(b)

        binding.compatChoiceLeftViewpager.layoutManager = layoutManager

        binding.compatChoiceLeftViewpager.setHasFixedSize(true)

        binding.compatChoiceLeftViewpager.adapter = SignPagerAdapter()

        val a = CenterScrollListener()

        binding.compatChoiceLeftViewpager.addOnScrollListener(a)

        sttBar(this)

        layoutManager2 = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)

        layoutManager2!!.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.compatChoiceRightViewpager.layoutManager = layoutManager2

        binding.compatChoiceRightViewpager.setHasFixedSize(true)

        binding.compatChoiceRightViewpager.adapter = SignPagerAdapter()

        binding.compatChoiceRightViewpager.addOnScrollListener(CenterScrollListener())

        binding.compatChoiceBack.setOnClickListener { onBackPressed() }

        binding.compatChoiceBtn.setOnClickListener {
            SIGN = layoutManager!!.centerItemPosition
            HER = layoutManager2!!.centerItemPosition
            setDataLocal(SIGN, applicationContext)
            val intent = Intent(applicationContext, ResultCompatActivity::class.java)
            startActivities(arrayOf(intent))
        }

        binding.compatChoiceHim.setOnClickListener {
            HIM = false
            val intent = Intent(applicationContext, WhatIsMySignActivity::class.java)
            startActivities(arrayOf(intent))
        }

        binding.compatChoiceHer.setOnClickListener {
            val intent = Intent(applicationContext, ChoiceSignHerActivity::class.java)
            startActivities(arrayOf(intent))
        }


    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        layoutManager?.scrollToPosition(SIGN)
        if (HER != 100) {
            layoutManager2?.scrollToPosition(HER)
        }
    }
}