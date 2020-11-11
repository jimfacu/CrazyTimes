package com.example.crazytimes.Core.Entities

import android.os.Parcel
import android.os.Parcelable
import com.example.crazytimes.Core.Entities.Coord
import com.example.crazytimes.Core.Entities.Main
import com.example.crazytimes.Core.Entities.Weather
import kotlinx.android.parcel.Parcelize

@Parcelize
class CityWeathers(val coord: Coord, val sys: Sys, val weather:List<Weather>, val main: Main, val id:Int, var name:String) :
    Parcelable {


}