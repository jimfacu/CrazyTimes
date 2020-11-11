package com.example.crazytimes.Core.Entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Weather(val id:Int ,val description:String , val icon:String) :Parcelable {

}