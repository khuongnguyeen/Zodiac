package fpt.adtrue.horoscope.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import fpt.adtrue.horoscope.databinding.ItemTarotBinding

class TarotAdapter(private val inter: ITarot) :
    RecyclerView.Adapter<TarotAdapter.TarotHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TarotHolder(ItemTarotBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TarotHolder, position: Int) {

        holder.binding.root.setOnClickListener {
            holder.binding.root.isEnabled = false
            val enableButton = Runnable {
                holder.binding.root.isEnabled = true
            }
            Handler(Looper.getMainLooper()).postDelayed(enableButton, 3000)
            inter.onClickItem(position,holder.binding.ivTarotItem)
        }
    }

    override fun getItemCount() = 78

    interface ITarot {
        fun onClickItem(position: Int,iv:ImageView)
    }

    class TarotHolder(val binding: ItemTarotBinding) : RecyclerView.ViewHolder(binding.root)
}