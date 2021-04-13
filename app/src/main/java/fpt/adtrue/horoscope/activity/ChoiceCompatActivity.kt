package fpt.adtrue.horoscope.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.SignPagerAdapter
import fpt.adtrue.horoscope.api.Utils.setDataLocal
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityCompatChoiceBinding


class ChoiceCompatActivity : AppCompatActivity() {

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



//
//        binding.compatChoiceLeftViewpager.adapter = SignPagerAdapter(supportFragmentManager)
//        binding.compatChoiceLeftViewpager.currentItem = App.SIGN

        sttBar(this)

//        binding.compatChoiceLeftNext.setOnClickListener {
//            if (binding.compatChoiceLeftViewpager.currentItem == 11) binding.compatChoiceLeftViewpager.currentItem =
//                0
//            else binding.compatChoiceLeftViewpager.currentItem =
//                binding.compatChoiceLeftViewpager.currentItem + 1
//        }
//
//        binding.compatChoiceLeftPrev.setOnClickListener {
//            if (binding.compatChoiceLeftViewpager.currentItem == 0) binding.compatChoiceLeftViewpager.currentItem =
//                11
//            else binding.compatChoiceLeftViewpager.currentItem =
//                binding.compatChoiceLeftViewpager.currentItem - 1
//        }





        val layoutManager2 = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
        layoutManager2.setPostLayoutListener(CarouselZoomPostLayoutListener())
        binding.compatChoiceRightViewpager.layoutManager = layoutManager2
        binding.compatChoiceRightViewpager.setHasFixedSize(true)
        binding.compatChoiceRightViewpager.adapter = SignPagerAdapter()
        binding.compatChoiceRightViewpager.addOnScrollListener(CenterScrollListener())



//
//        binding.compatChoiceRightViewpager.adapter = SignPagerAdapter(supportFragmentManager)
//        if (App.HER != 100) binding.compatChoiceRightViewpager.currentItem = App.HER
//        binding.compatChoiceRightNext.setOnClickListener {
//            if (binding.compatChoiceRightViewpager.currentItem == 11) binding.compatChoiceRightViewpager.currentItem =
//                0
//            else binding.compatChoiceRightViewpager.currentItem =
//                binding.compatChoiceRightViewpager.currentItem + 1
//        }
//
//        binding.compatChoiceRightPrev.setOnClickListener {
//            if (binding.compatChoiceRightViewpager.currentItem == 0) binding.compatChoiceRightViewpager.currentItem =
//                11
//            else binding.compatChoiceRightViewpager.currentItem =
//                binding.compatChoiceRightViewpager.currentItem - 1
//            print("a")
//        }

        binding.compatChoiceBack.setOnClickListener {
            onBackPressed()
        }

        binding.compatChoiceBtn.setOnClickListener {
            App.SIGN = layoutManager.centerItemPosition
            App.HER = layoutManager2.centerItemPosition
            setDataLocal(App.SIGN, applicationContext)
            val intent = Intent(applicationContext, ResultCompatActivity::class.java)
            startActivities(arrayOf(intent))
        }

//        compat_choice_go_wims
        binding.compatChoiceGoWims.setOnClickListener {
            setDataLocal(App.SIGN, applicationContext)
            val intent = Intent(applicationContext, ChoiceSignHerActivity::class.java)
            startActivities(arrayOf(intent))
        }

    }



}