package com.example.crazytimes.Core.Entities.CitiesAroundSearch

import android.os.Parcelable
import com.example.crazytimes.Core.Entities.Coord
import com.example.crazytimes.Core.Entities.Main
import com.example.crazytimes.Core.Entities.Sys
import com.example.crazytimes.Core.Entities.Weather
import kotlinx.android.parcel.Parcelize

@Parcelize
class CitiesAround(val id:Int,val name:String ,val coord: Coord,val main: Main,val sys: Sys ,val weather:List<Weather>): Parcelable {


}