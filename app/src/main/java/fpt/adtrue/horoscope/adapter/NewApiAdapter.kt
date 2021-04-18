package fpt.adtrue.horoscope.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.databinding.ItemApiTwoBinding

class NewApiAdapter (val love:String,val work:String,val money:String) :
    RecyclerView.Adapter<NewApiAdapter.HoroHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HoroHolder(ItemApiTwoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HoroHolder, position: Int) {
        when(position){
            1->{
                holder.binding.tvDescriptLove.text = work
                holder.binding.textView9.setImageResource(R.drawable.ic_work)
            }
            2->{
                holder.binding.tvDescriptLove.text = money
                holder.binding.textView9.setImageResource(R.drawable.ic_money)
            }
            else->{
                holder.binding.tvDescriptLove.text = love
                holder.binding.textView9.setImageResource(R.drawable.ic_love)
            }

        }




    }

    override fun getItemCount() = 3


    class HoroHolder(val binding: ItemApiTwoBinding) : RecyclerView.ViewHolder(binding.root)
}