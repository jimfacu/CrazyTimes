package com.example.crazytimes.Core.Entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Main(val temp:Double ,val temp_min:Double ,val temp_max:Double) :Parcelable{

}