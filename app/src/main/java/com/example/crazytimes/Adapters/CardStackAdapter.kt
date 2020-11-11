package com.example.crazytimes.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crazytimes.Core.Entities.CityWeathers
import com.example.crazytimes.R
import com.example.crazytimes.databinding.ItemCardBinding
import java.net.URL

class CardStackAdapter(var list:List<CityWeathers>,val context: Context,val cardSelected:CardSelected): RecyclerView.Adapter<CardStackAdapter.CardStackHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCardBinding.inflate(inflater)
        return CardStackHolder(
            binding
        )
    }

    override fun getItemCount(): Int = list.size


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: CardStackHolder, position: Int) = holder.bind(list[position],context,cardSelected)

    class CardStackHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(cityWeathers:CityWeathers, context: Context, cardSelected: CardSelected) {
            with(binding) {
                textViewNameCity.text = cityWeathers.name
                textViewMinT.text = cityWeathers.main.temp_min.toString().substring(0,2)+"°"
                textViewMaxT.text = cityWeathers.main.temp_max.toString().substring(0,2)+"°"
                Glide.with(binding.root).load("http://openweathermap.org/img/wn/"+cityWeathers.weather[0].icon+"@2x.png").into(imageViewIcon)

                when(cityWeathers.weather[0].icon){
                    "01d"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.tarde) }
                    "01n"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.cielo_luna)}
                    "02d"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.cielo_nubes_sol)}
                    "03d"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.cielo_nubes_sol)}
                    "03n"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.cielo_nubes_luna)}
                    "02n"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.cielo_nubes_luna)}
                    "04n"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.cielo_nubes_luna)}
                    "09d"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.lluvia_dia)}
                    "09n"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.lluvia_noche)}
                    "10d"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.lluvia_dia)}
                    "10n"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.sol)}
                    "11d"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.lluvia_dia)}
                    "11n"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.lluvia_noche)}
                    "13d"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.nieve)}
                    "13n"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.nieve)}
                    "50d"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.niebla)}
                    "50n"->{cardViewFondo.background = context.applicationContext.getDrawable(R.drawable.niebla)}
                }
            }
            itemView.setOnClickListener {
                cardSelected.onCardSelected(cityWeathers.id)
            }
        }
    }

    interface CardSelected{
        fun onCardSelected(id:Int)
    }
}