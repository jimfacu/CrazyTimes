package com.example.crazytimes.Core.Entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
class ConteinerWheathers(val cnt:Int, val list: ArrayList<CityWeathers>?):Parcelable {
}