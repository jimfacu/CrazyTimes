package com.example.crazytimes.Core.Service

import com.example.crazytimes.Core.Entities.CitiesAroundSearch.ConteinerAroundCities
import com.example.crazytimes.Core.Entities.CityWeathers
import com.example.crazytimes.Core.Entities.ConteinerWheathers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("group?")
    fun getWeathersId(@Query("id") id:String,@Query("units")units:String,@Query("appid")appid:String) : Call<ConteinerWheathers>

    @GET("weather?")
    fun getWeatherCityName(@Query("q")nameCity:String,@Query("units")units:String,@Query("appid")appid:String): Call<CityWeathers>

    @GET("find?")
    fun getCitiesAroundSearch(@Query("lat")lat:String,@Query("lon")lon:String,@Query("cnt")cnt:Int
                              ,@Query("units")units: String,@Query("appid")appid: String) : Call<ConteinerAroundCities>

    @GET("weather?")
    fun getWeatherCityId(@Query("id")id:Int,@Query("units")units:String ,@Query("appid")appid: String) :Call<CityWeathers>
 }