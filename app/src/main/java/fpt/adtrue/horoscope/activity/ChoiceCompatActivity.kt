package fpt.adtrue.horoscope.activity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import fpt.adtrue.horoscope.BaseActivity
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.SignPagerAdapter
import fpt.adtrue.horoscope.api.Utils.setDataLocal
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.application.App.Companion.HER
import fpt.adtrue.horoscope.application.App.Companion.SIGN
import fpt.adtrue.horoscope.databinding.ActivityCompatChoiceBinding


class ChoiceCompatActivity : BaseActivity() {

    private lateinit var binding: ActivityCompatChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compat_choice)


        val layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
        layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.compatChoiceLeftViewpager.layoutManager = layoutManager
        binding.compatChoiceLeftViewpager.setHasFixedSize(true)
        binding.compatChoiceLeftViewpager.adapter = SignPagerAdapter()
        binding.compatChoiceLeftViewpager.addOnScrollListener(CenterScrollListener())
        sttBar(this)
        val layoutManager2 = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
        layoutManager2.setPostLayoutListener(CarouselZoomPostLayoutListener())
        binding.compatChoiceRightViewpager.layoutManager = layoutManager2
        binding.compatChoiceRightViewpager.setHasFixedSize(true)
        binding.compatChoiceRightViewpager.adapter = SignPagerAdapter()
        binding.compatChoiceRightViewpager.addOnScrollListener(CenterScrollListener())
        binding.compatChoiceBack.setOnClickListener { onBackPressed() }
        binding.compatChoiceBtn.setOnClickListener {
            SIGN = layoutManager.centerItemPosition
            HER = layoutManager2.centerItemPosition
            setDataLocal(SIGN, applicationContext)
            val intent = Intent(applicationContext, ResultCompatActivity::class.java)
            startActivities(arrayOf(intent))
        }
        binding.compatChoiceGoWims.setOnClickListener {
            setDataLocal(SIGN, applicationContext)
            val intent = Intent(applicationContext, ChoiceSignHerActivity::class.java)
            startActivities(arrayOf(intent))
        }
    }
}