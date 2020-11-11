package com.example.crazytimes


import android.content.Context
import android.util.Log
import com.example.crazytimes.Core.Entities.CitiesAroundSearch.ConteinerAroundCities
import com.example.crazytimes.Core.Entities.CityWeathers
import com.example.crazytimes.Core.Entities.ConteinerWheathers
import com.example.crazytimes.Core.Entities.LastCitySearch
import com.example.crazytimes.Core.Interfaces.HomeContract
import com.example.crazytimes.Core.Service.ServiceApi
import com.example.crazytimes.Core.Service.ServiceRetrofit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val BoxIds = "524901,703448,2643743,3435910,3936456,3941584,5115985"
private const val Metric = "metric"
private const val AppId ="4902272ee855013552303c24c6dc5ec2"


class HomeInteractor(val viewModelHome: HomeContract.ViewModel): HomeContract.Interactor{




    override fun getListWeathersInteractor() {
        val request = ServiceRetrofit.buildService(ServiceApi::class.java)
        val call = request.getWeathersId(BoxIds, Metric, AppId)
        call.enqueue(object : Callback<ConteinerWheathers>{


            override fun onResponse(call: Call<ConteinerWheathers>,response: Response<ConteinerWheathers>) {
                if(response.isSuccessful){
                    viewModelHome.responseListCities(response.body()!!)
                }else{
                    viewModelHome.responseErrorService()
                }
            }

            override fun onFailure(call: Call<ConteinerWheathers>, t: Throwable) {
                viewModelHome.responseErrorService()
            }
        })
    }

    override fun getCityByName(nameCity: String) {
        val request = ServiceRetrofit.buildService(ServiceApi::class.java)
        val call = request.getWeatherCityName(nameCity, Metric, AppId)
        call.enqueue(object : Callback<CityWeathers>{


            override fun onResponse(call: Call<CityWeathers>,response: Response<CityWeathers>) {
                if(response.isSuccessful){
                    viewModelHome.responseCitySearch(response.body()!!)
                }else{
                    viewModelHome.responseErrorService()
                }
            }

            override fun onFailure(call: Call<CityWeathers>, t: Throwable) {
                viewModelHome.responseErrorService()
            }
        })
    }

    override fun getCitiesAround(lan: String, lon: String) {
        val request = ServiceRetrofit.buildService(ServiceApi::class.java)
        val call = request.getCitiesAroundSearch(lan,lon,50, Metric, AppId)
        call.enqueue(object : Callback<ConteinerAroundCities>{


            override fun onResponse(call: Call<ConteinerAroundCities>,response: Response<ConteinerAroundCities>) {
                if(response.isSuccessful){
                    viewModelHome.responseListCitiesAround(response.body()!!)
                }else{
                    viewModelHome.responseErrorService()
                }
            }

            override fun onFailure(call: Call<ConteinerAroundCities>, t: Throwable) {
                viewModelHome.responseErrorService()
            }
        })
    }

    override fun getCityById(id:Int) {
        val request = ServiceRetrofit.buildService(ServiceApi::class.java)
        val call = request.getWeatherCityId(id, Metric, AppId)
        call.enqueue(object : Callback<CityWeathers>{


            override fun onResponse(call: Call<CityWeathers>,response: Response<CityWeathers>) {
                if(response.isSuccessful){
                    viewModelHome.responseCitySearch(response.body()!!)
                }else{
                    viewModelHome.responseErrorService()
                }
            }

            override fun onFailure(call: Call<CityWeathers>, t: Throwable) {
                viewModelHome.responseErrorService()
            }
        })
    }

    override fun setLastCitySearch(context: Context, list:MutableList<LastCitySearch>) {
        val sharedPreferences = context.getSharedPreferences(context.resources.getString(R.string.com_example_myapp_PREFERENCE_FILE_KEY),Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(list)

        editor.putString(context.resources.getString(R.string.ListLastSearch),json)
        editor.apply()
    }

    override fun getLastCitySearch(context: Context) {
        val sharedPreferences = context.getSharedPreferences(context.resources.getString(R.string.com_example_myapp_PREFERENCE_FILE_KEY),Context.MODE_PRIVATE)
        val serializedObject = sharedPreferences.getString(context.resources.getString(R.string.ListLastSearch),null)
        if(serializedObject != null) {
            val gson = Gson()
            val type = object : TypeToken<List<LastCitySearch?>?>() {}.type
            val list:MutableList<LastCitySearch> = gson.fromJson(serializedObject, type)
            viewModelHome.responseLastCitySearch(list)
        }else{
            viewModelHome.responseErrorLasSearchCities()
        }
    }


}