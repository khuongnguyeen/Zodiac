package fpt.adtrue.horoscope.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import fpt.adtrue.horoscope.api.Utils.sttBar
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.application.App.Companion.POSITION_CAREER
import fpt.adtrue.horoscope.application.App.Companion.POSITION_FUTURE
import fpt.adtrue.horoscope.application.App.Companion.POSITION_LOVE
import fpt.adtrue.horoscope.application.App.Companion.getTarot
import fpt.adtrue.horoscope.broadcast.NotificationActionService
import fpt.adtrue.horoscope.databinding.ActivityTarotResultsBinding

@Suppress("DEPRECATION")
class ResultsTarotActivity : Activity(), View.OnTouchListener , TarotResultAdapter.ITarot{

    private lateinit var binding: ActivityTarotResultsBinding
    private var loveUpright = ""
    private var loveReversed = ""
    private var careerUpright = ""
    private var futureUpright = ""
    private var careerReversed = ""
    private var futureReversed = ""
    private var layoutManager:CarouselLayoutManager? = null

    @SuppressLint("ResourceAsColor", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tarot_results)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
        layoutManager!!.setPostLayoutListener(CarouselZoomPostLayoutListener())
        layoutManager!!.maxVisibleItems = 2
        binding.rc.layoutManager = layoutManager
        binding.rc.setHasFixedSize(true)
        binding.rc.adapter = TarotResultAdapter(this)
        binding.rc.addOnScrollListener(CenterScrollListener())
        getTarot().forEach {
            if (it.name == POSITION_LOVE) {
                loveUpright = it.keywords.love.upright
                loveReversed = it.keywords.love.reversed
            }
            if (it.name == POSITION_CAREER) {
                careerUpright = it.keywords.career.upright
                careerReversed = it.keywords.career.reversed
            }
            if (it.name == POSITION_FUTURE) {
                futureUpright = it.keywords.future.upright
                futureReversed = it.keywords.future.reversed
            }
        }

        binding.rc.setOnTouchListener(this)

        sttBar(this)

        binding.compatChoiceBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onClickItem(position: Int) {
        if (position == 0){
            layoutManager?.scrollToPosition(0)
            binding.tvLoveDetail.text = loveUpright
            binding.tvReversed.text = loveReversed
        }
        if (position == 1){
            layoutManager?.scrollToPosition(1)
            binding.tvLoveDetail.text = careerUpright
            binding.tvReversed.text = careerReversed
        }
        if (position == 2){
            layoutManager?.scrollToPosition(2)
            binding.tvLoveDetail.text = futureUpright
            binding.tvReversed.text = futureReversed
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

    @SuppressLint("ClickableViewAccessibility")
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

    override fun onResume() {
        super.onResume()
        val intent = Intent(applicationContext, NotificationActionService::class.java).setAction("REPLAY")
        applicationContext!!.sendBroadcast(intent)
        Log.e("MainActivity","___________________ onResume() _________")
    }

    override fun onPause() {
        super.onPause()
        val intent = Intent(applicationContext, NotificationActionService::class.java).setAction("STOP")
        applicationContext!!.sendBroadcast(intent)
        Log.e("MainActivity","___________________ onPause() _________")
    }
}