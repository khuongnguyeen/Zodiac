package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.SignPagerAdapter
import fpt.adtrue.horoscope.adapter.TarotResultAdapter
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ActivityTarotResultsBinding

class ResultsTarotActivity : Activity(), View.OnTouchListener {

    private lateinit var binding: ActivityTarotResultsBinding
    var loveUpright = ""
    var loveReversed = ""
    var careerUpright = ""
    var futureUpright = ""
    var careerReversed = ""
    var futureReversed = ""
    var layoutManager:CarouselLayoutManager? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tarot_results)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
        layoutManager!!.setPostLayoutListener(CarouselZoomPostLayoutListener())

        binding.rc.layoutManager = layoutManager
        binding.rc.setHasFixedSize(true)
        binding.rc.adapter = TarotResultAdapter()
        binding.rc.addOnScrollListener(CenterScrollListener())


        App.getTarot().forEach {
            if (it.name == App.POSITION_LOVE) {
                loveUpright = it.keywords.love.upright
                loveReversed = it.keywords.love.reversed
            }
            if (it.name == App.POSITION_CAREER) {
                careerUpright = it.keywords.career.upright
                careerReversed = it.keywords.career.reversed
            }
            if (it.name == App.POSITION_FUTURE) {
                futureUpright = it.keywords.future.upright
                futureReversed = it.keywords.future.reversed
            }
        }



        binding.rc.setOnTouchListener(this)





        Utils.sttBar(this)

        binding.compatChoiceBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, StartReadingTarotActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ResultsTarotActivity::class.java))
        }
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_MOVE->{
                if (layoutManager!!.centerItemPosition == 0){
                    binding.tvLoveDetail.text = loveUpright
                    binding.tvReversed.text = loveReversed

                }
                if (layoutManager!!.centerItemPosition == 1){

                    binding.tvLoveDetail.text = careerUpright
                    binding.tvReversed.text = careerReversed
                }
                if (layoutManager!!.centerItemPosition == 2){

                    binding.tvLoveDetail.text = futureUpright
                    binding.tvReversed.text = futureReversed
                }
            }
        }
        return false
    }
}