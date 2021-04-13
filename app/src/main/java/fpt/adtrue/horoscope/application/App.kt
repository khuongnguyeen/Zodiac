package fpt.adtrue.horoscope.application

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import fpt.adtrue.horoscope.model.*
import fpt.adtrue.horoscope.viewmodel.HoroscopeViewModel

class App:Application() {

    companion object{
        private  val horoscopeViewModel = HoroscopeViewModel()
        fun getViewModel() = horoscopeViewModel
        private val zodiac = mutableListOf<DataZodiac>()
        fun getZodiac() = zodiac
        private val dataCom = mutableListOf<DataCompatibilityItem>()
        fun getCom() = dataCom
        private val dataSign = mutableListOf<DataSignItem>()
        fun getSign() = dataSign
        private val dataTarot = mutableListOf<DataTarot>()
        fun getTarot() = dataTarot


        private val dataTarot2 = MutableLiveData<DataAmazonaws>()
        fun getTarot2() = dataTarot2
        private val dataTarot3 = MutableLiveData<DataAmazonaws>()
        fun getTarot3() = dataTarot3
        private val dataTarot4 = MutableLiveData<DataAmazonaws>()
        fun getTarot4() = dataTarot4
        var SIGN = 100
        var HER = 100
        var POSITION_LOVE = ""
        var POSITION_CAREER = ""
        var POSITION_FUTURE = ""



    }


}