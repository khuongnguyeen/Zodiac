package fpt.adtrue.horoscope.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.databinding.ItemRecycleBinding

class StartReadingTarotAdapter(private val inter: ITarot) :
    RecyclerView.Adapter<StartReadingTarotAdapter.TarotHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TarotHolder(ItemRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TarotHolder, position: Int) {
        if (position == 0) {
            holder.binding.ivTop.setImageResource(R.drawable.ic_tarot)
            holder.binding.textBottom.text = "Cards"
            holder.binding.tvCenter.text = "Pull your Cards"
        }
        if (position == 1) {
            holder.binding.ivTop.setImageResource(R.drawable.ic_yes_no)
            holder.binding.textBottom.text = "Coming Soon"
            holder.binding.tvCenter.text = "Get your answer"
        }
        if (position == 2) {
            holder.binding.ivTop.setImageResource(R.drawable.all_card)
            holder.binding.textBottom.text = "Coming Soon"
            holder.binding.tvCenter.text = "Explore Cards"
        }
        holder.binding.root.setOnClickListener {
            holder.binding.root.isEnabled = false
            val enableButton = Runnable {
                holder.binding.root.isEnabled = true
            }
            Handler(Looper.getMainLooper()).postDelayed(enableButton, 1000)
            inter.onClickItem(position)
        }
    }

    override fun getItemCount() = 3

    interface ITarot {
        fun onClickItem(position: Int)
    }

    class TarotHolder(val binding: ItemRecycleBinding) : RecyclerView.ViewHolder(binding.root)
}