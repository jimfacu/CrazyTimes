package com.example.crazytimes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.crazytimes.Core.Entities.CitiesAroundSearch.CitiesAround
import com.example.crazytimes.Core.Entities.CityWeathers
import com.example.crazytimes.Core.Entities.LastCitySearch
import com.example.crazytimes.Core.Network.ChangeIconBitMap
import com.example.crazytimes.databinding.ActivityDetailCityBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*
import kotlin.collections.HashMap


private const val ListLastSearchs = "ListLastSearch"
private const val NameCityKey= "NameOfCity"
private const val Bundlee ="Bundle"

class DetailCity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailCityBinding
    private lateinit var mapFragment:SupportMapFragment
    private lateinit var googleMap:GoogleMap
    private lateinit var markerActual:Marker

    private lateinit var viewModel:ViewModelHome

    private var idCityActual = 0
    private lateinit var hashMap: HashMap<String,Int>
    private lateinit var listCities:List<CitiesAround>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCityBinding.inflate(layoutInflater).apply{
            viewModel = ViewModelProviders.of(this@DetailCity).get(ViewModelHome::class.java)
        }
        val view = binding.root
        setContentView(view)
        val bundle = intent.getBundleExtra(Bundlee)
        if(bundle!= null){
            var city:CityWeathers = bundle.getParcelable<CityWeathers>(NameCityKey) as CityWeathers
            var listCitySearch:MutableList<LastCitySearch> = bundle.getParcelableArrayList<LastCitySearch>(
                ListLastSearchs)!!

            buildListLastSearch(listCitySearch,city)
            viewModel.getCitiesAroundFromService(city.coord.lat.toString(),city.coord.lon.toString())
            init()
            setInformation(city)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setInformation(city: CityWeathers) {
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it
            idCityActual = city.id
            val location = LatLng(city.coord.lat,city.coord.lon)
            val marker = googleMap.addMarker(MarkerOptions().position(location).title(city.name))

            markerActual = marker
            hashMap[marker.id] = 0

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,12f))
            binding.textViewCitySearchTempMax.text = city.main.temp_max.toString().substring(0,2)+"°"
            binding.textViewCitySearchTempMin.text = city.main.temp_min.toString().substring(0,2)+"°"
            Glide.with(binding.root).load("http://openweathermap.org/img/wn/"+city.weather[0].icon+"@2x.png").into(binding.imageViewIconDetailCity)
            setUpObservers()
            googleMap.setOnMarkerClickListener(clickListener)
        })
    }

    private fun init() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        hashMap = HashMap()
    }


    private fun setUpObservers(){

       viewModel.getCitiesAround().observe(this, Observer {
           listCities = it
           var contador = 1
           for(citiesAround:CitiesAround in it) {
               if (idCityActual != citiesAround.id) {
                   val location = LatLng(citiesAround.coord.lat, citiesAround.coord.lon)
                   val marker:Marker = googleMap.addMarker(
                       MarkerOptions().position(location)
                           .icon(
                               ChangeIconBitMap.bitmapDescriptorFromVector(
                                   this,
                                   R.drawable.show_more
                               )
                           ).title(citiesAround.name)
                   )
                   hashMap[marker.id] = contador
                   contador++
               }

           }
       })
    }

    private val clickListener = GoogleMap.OnMarkerClickListener( object : GoogleMap.OnMarkerClickListener,
            (Marker) -> Boolean {
        override fun onMarkerClick(p0: Marker?): Boolean {
            return false
        }

        @SuppressLint("SetTextI18n")
        override fun invoke(p1: Marker): Boolean {
            if(markerActual.id != p1.id) {
                markerActual.setIcon(ChangeIconBitMap.bitmapDescriptorFromVector(applicationContext, R.drawable.show_more))

                markerActual = p1

                markerActual.setIcon(BitmapDescriptorFactory.defaultMarker())
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerActual.position,10f))

                binding.textViewCitySearchTempMax.text = listCities[hashMap.get(markerActual.id)!!].main.temp_max.toString().substring(0,2)+"°"
                binding.textViewCitySearchTempMin.text = listCities[hashMap.get(markerActual.id)!!].main.temp_min.toString().substring(0,2)+"°"
                Glide.with(binding.root).load("http://openweathermap.org/img/wn/"+listCities[hashMap.get(markerActual.id)!!].weather[0].icon+"@2x.png").into(binding.imageViewIconDetailCity)
            }
            return false
        }
    })

    fun buildListLastSearch(list:MutableList<LastCitySearch>, city:CityWeathers){
        val cityLast = LastCitySearch(city.name,city.id)
        val cityDefault = LastCitySearch("-",0)
        var listTank = mutableListOf(cityDefault,cityDefault,cityDefault,cityDefault,cityDefault)
        if(list.isNotEmpty()) {
            for (i in 0..list.size-1) {
                listTank[i]= list[i]
            }
            for(i in 1..list.size-1){
                list[i] = listTank[i-1]
            }
            list[0] = cityLast
            viewModel.setListLastSearchFromService(list,this)
        }else{
            val cityDefault = LastCitySearch("-",0)
            val newCity = LastCitySearch(city.name,city.id)
            val firstList:MutableList<LastCitySearch> = mutableListOf(newCity,cityDefault,cityDefault,cityDefault,cityDefault)
            viewModel.setListLastSearchFromService(firstList,this)
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        finish()
        super.onBackPressed()


    }
}






