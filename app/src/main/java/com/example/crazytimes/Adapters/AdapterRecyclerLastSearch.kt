package com.example.crazytimes.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crazytimes.Core.Entities.LastCitySearch
import com.example.crazytimes.databinding.CellLastCitiesSearchBinding

class AdapterRecyclerLastSearch(val context: Context,val list:List<LastCitySearch>,val cityLastSelected:LastCitySelected) : RecyclerView.Adapter<AdapterRecyclerLastSearch.LastSearchHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastSearchHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CellLastCitiesSearchBinding.inflate(inflater)
        return LastSearchHolder(binding,context,cityLastSelected)
    }

    override fun getItemCount() :Int = list.size

    override fun onBindViewHolder(holder: LastSearchHolder, position: Int) = holder.bind(list[position])


    class LastSearchHolder(val binding: CellLastCitiesSearchBinding, val context: Context,val lastCitySelected: LastCitySelected) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: LastCitySearch) {
            with(binding) {
              textViewLastCitySearch.text = item.name
            }
            itemView.setOnClickListener {
                lastCitySelected.onCardSelected(item.id)
            }
            }

        }
    interface LastCitySelected{
        fun onCardSelected(id:Int)
    }
}