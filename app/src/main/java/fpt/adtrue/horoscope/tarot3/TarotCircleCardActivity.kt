package fpt.adtrue.horoscope.tarot3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.adapter.TarotAdapter
import fpt.adtrue.horoscope.api.Utils
import fpt.adtrue.horoscope.databinding.ActivityTarotCircleCardBinding
import fpt.adtrue.horoscope.databinding.ActivityTarotDemoBinding


class TarotCircleCardActivity : AppCompatActivity(){

    private lateinit var binding: ActivityTarotCircleCardBinding
//    private lateinit var binding: ActivityTarotDemoBinding
    private var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_tarot_demo)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tarot_circle_card)
        mContext = this
        val enableButton = Runnable { binding.cardLayout.startExpendCard() }
        Handler(Looper.getMainLooper()).postDelayed(enableButton, 500)

        Utils.sttBar(this)

        binding.compatChoiceBack.setOnClickListener {
            onBackPressed()
        }

//        val layoutManager = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true)
//        layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
//        layoutManager.maxVisibleItems = 78
//
//        binding.rc.layoutManager = layoutManager
//        binding.rc.setHasFixedSize(true)
//        binding.rc.adapter = TarotAdapter(this)
//        binding.rc.addOnScrollListener(CenterScrollListener())



    }



    companion object { fun start(context: Context) { context.startActivity(
        Intent(
            context,
            TarotCircleCardActivity::class.java
        )
    ) } }
}