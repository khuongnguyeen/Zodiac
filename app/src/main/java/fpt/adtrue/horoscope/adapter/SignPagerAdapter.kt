package fpt.adtrue.horoscope.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ItemSignBinding

class SignPagerAdapter :
    RecyclerView.Adapter<SignPagerAdapter.TarotHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TarotHolder(ItemSignBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TarotHolder, position: Int) {
        holder.binding.hihihi.setImageResource(App.getZodiac()[position].image2)
    }

    override fun getItemCount() = 12


    class TarotHolder(val binding: ItemSignBinding) : RecyclerView.ViewHolder(binding.root){

    }
}