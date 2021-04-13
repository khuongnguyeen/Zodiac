package fpt.adtrue.horoscope.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.ItemTarotCardBinding

class TarotResultAdapter :
    RecyclerView.Adapter<TarotResultAdapter.TarotHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TarotHolder(
            ItemTarotCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TarotHolder, position: Int) {
        if (position == 0) {
            App.getTarot().forEach {
                if (it.name == App.POSITION_LOVE) {
                    holder.binding.ivCareer.setImageResource(it.img!!)
                }
            }
            holder.binding.txtCareer.text = "LOVE"
        }
        if (position == 1) {
            App.getTarot().forEach {
                if (it.name == App.POSITION_CAREER) {
                    holder.binding.ivCareer.setImageResource(it.img!!)
                }

            }
            holder.binding.txtCareer.text = "CAREER"
        }
        if (position == 2) {
            App.getTarot().forEach {
                if (it.name == App.POSITION_FUTURE) {
                    holder.binding.ivCareer.setImageResource(it.img!!)
                }
            }
            holder.binding.txtCareer.text = "FUTURE"
        }
    }

    override fun getItemCount() = 3


    class TarotHolder(val binding: ItemTarotCardBinding) : RecyclerView.ViewHolder(binding.root)
}