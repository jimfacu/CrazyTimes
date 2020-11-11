package com.example.crazytimes

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crazytimes.Core.Entities.CitiesAroundSearch.CitiesAround
import com.example.crazytimes.Core.Entities.CitiesAroundSearch.ConteinerAroundCities
import com.example.crazytimes.Core.Entities.CityWeathers
import com.example.crazytimes.Core.Entities.ConteinerWheathers
import com.example.crazytimes.Core.Entities.LastCitySearch
import com.example.crazytimes.Core.Interfaces.HomeContract

class ViewModelHome : ViewModel() , HomeContract.ViewModel{

    private val homeInteractor = HomeInteractor(this)

    private val listWheater = MutableLiveData<List<CityWeathers>>()
    private val listAroundCity = MutableLiveData<List<CitiesAround>>()
    private val progressBarShowing = MutableLiveData<Int>()
    private val errorService = MutableLiveData<Boolean>()
    private val errorServiceLastCitiesSearch = MutableLiveData<Boolean>()
    private val citySearch = MutableLiveData<CityWeathers>()
    private val listLastSearch = MutableLiveData<MutableList<LastCitySearch>>()

    fun setListWeather(container:ConteinerWheathers){
        listWheater.value = container.list
    }
    fun setCitySearch(citySearchFromSystem:CityWeathers){
        citySearch.value = citySearchFromSystem
    }

    fun setCitiesAround(container:ConteinerAroundCities){
        listAroundCity.value = container.list
    }

    fun setListLastSearch(listSearchLast:MutableList<LastCitySearch>){
        listLastSearch.value = listSearchLast
    }

    fun setListLastSearchFromService(list:MutableList<LastCitySearch>, context: Context){
        homeInteractor.setLastCitySearch(context,list)
    }

    fun getListLastSearchFromService(context: Context){
        homeInteractor.getLastCitySearch(context)
    }

    fun getListWeathersFromService(){
        progressBarShowing.value = View.VISIBLE
        homeInteractor.getListWeathersInteractor()
    }

    fun getCityDetailFromService(nameCity:String){
        progressBarShowing.value = View.VISIBLE
        homeInteractor.getCityByName(nameCity)
    }

    fun getCityByIdDetailFromService(id:Int){
        progressBarShowing.value = View.VISIBLE
        homeInteractor.getCityById(id)
    }

    fun getCitiesAroundFromService(lat:String, long:String){
        progressBarShowing.value = View.VISIBLE
        homeInteractor.getCitiesAround(lat,long)
    }

    override fun responseListCities(conteinerWheathers: ConteinerWheathers) {
        setListWeather(conteinerWheathers)
        this.progressBarShowing.value = View.GONE
        this.errorService.value = false
    }



    override fun responseCitySearch(city: CityWeathers) {
        setCitySearch(city)
        this.progressBarShowing.value = View.GONE
    }

    override fun responseListCitiesAround(containerAroundCities: ConteinerAroundCities) {
        setCitiesAround(containerAroundCities)
        this.progressBarShowing.value = View.GONE
    }

    override fun responseLastCitySearch(list:MutableList<LastCitySearch>) {
        setListLastSearch(list)
    }

    override fun setLastCitySearch(context: Context, list: MutableList<LastCitySearch>) {
        homeInteractor.setLastCitySearch(context,list)
    }

    override fun responseErrorLasSearchCities() {
        errorServiceLastCitiesSearch.value = true

    }

    override fun responseErrorService(){
        this.errorService.value = true
        this.progressBarShowing.value = View.GONE
    }

    fun getListWeathers() = listWheater

    fun getCitySearch() = citySearch

    fun getCitiesAround() = listAroundCity

    fun getLastCitiesSearch() = listLastSearch

    fun getErrorCitiesSearch() = errorServiceLastCitiesSearch

    fun isErrorService() = errorService

    fun progresBarIsShowing() = progressBarShowing
}