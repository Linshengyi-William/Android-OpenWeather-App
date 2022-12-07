package com.example.android.roomyweather.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.roomyweather.R
import com.example.android.roomyweather.data.FiveDayForecast
import com.example.android.roomyweather.data.ForcastCityEntity

import com.example.android.roomyweather.util.getTempUnitsDisplay
import com.example.android.roomyweather.util.openWeatherEpochToDate

class DrawerCityAdapter(private val onClick: (ForcastCityEntity) -> Unit)
        : RecyclerView.Adapter<DrawerCityAdapter.ViewHolder>() {
    var forcastCityList: List<ForcastCityEntity> = listOf()
    fun updateCityList(newCityList: List<ForcastCityEntity>?) {
        forcastCityList = newCityList ?: listOf()
        Log.d("redCheck","getItemCount is:"+this.forcastCityList.size)
        Log.d("redCheck",
            "In updateCityList, the foecastCityList is: "+forcastCityList.toString())
        notifyDataSetChanged()
    }
    override fun getItemCount() = this.forcastCityList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("redCheck","onCreateViewHolder in DrawerCityAdapter")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.drawer_city_item, parent, false)
        return ViewHolder(view,onClick)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("redCheck","onBindViewHolder in DrawerCityAdapter")
        holder.bind(forcastCityList[position])
    }

    class ViewHolder(itemView: View, val onClick: (ForcastCityEntity) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val cityNameTV: TextView = itemView.findViewById(R.id.tv_city_name)
        private var currentForcastCityEntity: ForcastCityEntity? = null
        init {
            itemView.setOnClickListener {
                currentForcastCityEntity?.let(onClick)
            }
        }
        fun bind(forcastCityEntity: ForcastCityEntity) {
            currentForcastCityEntity = forcastCityEntity
            cityNameTV.text = forcastCityEntity.name
            Log.d("redCheck", "cityNameTV.text is: "+cityNameTV.text)

        }
    }
}