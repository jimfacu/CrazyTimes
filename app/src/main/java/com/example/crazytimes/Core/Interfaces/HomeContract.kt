package com.example.crazytimes.Core.Interfaces

import android.content.Context
import com.example.crazytimes.Adapters.AdapterRecyclerLastSearch
import com.example.crazytimes.Core.Entities.CitiesAroundSearch.ConteinerAroundCities
import com.example.crazytimes.Core.Entities.CityWeathers
import com.example.crazytimes.Core.Entities.ConteinerWheathers
import com.example.crazytimes.Core.Entities.LastCitySearch

interface HomeContract {

    interface Interactor{
        fun getListWeathersInteractor()
        fun getCityByName(nameCity:String)
        fun getCitiesAround(lan:String ,lon:String)
        fun getCityById(id:Int)
        fun setLastCitySearch(context: Context,list:MutableList<LastCitySearch>)
        fun getLastCitySearch(context: Context)
    }

    interface ViewModel{
        fun responseListCities(conteinerWheathers: ConteinerWheathers)
        fun responseCitySearch(city:CityWeathers)
        fun responseListCitiesAround(containerAroundCities: ConteinerAroundCities)
        fun responseLastCitySearch(list:MutableList<LastCitySearch>)
        fun setLastCitySearch(context: Context,list: MutableList<LastCitySearch>)
        fun responseErrorLasSearchCities()
        fun responseErrorService()
    }

}