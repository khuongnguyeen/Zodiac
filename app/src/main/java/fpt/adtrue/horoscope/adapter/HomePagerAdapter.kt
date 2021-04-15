package fpt.adtrue.horoscope.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fpt.adtrue.horoscope.fragment.FragmentHomePager

@Suppress("DEPRECATION")
class HomePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
        when (position) {

            0 -> {
                return FragmentHomePager(0)
            }
            1 -> {
                return FragmentHomePager(1)
            }
            else -> return FragmentHomePager(2)
        }
    }


    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Yesterday"
            1 -> "Today"
            else -> "Tomorrow"
        }
    }
}