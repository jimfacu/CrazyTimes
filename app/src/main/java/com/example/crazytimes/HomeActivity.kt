package com.example.crazytimes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.example.crazytimes.Adapters.AdapterRecyclerLastSearch
import com.example.crazytimes.Adapters.CardStackAdapter
import com.example.crazytimes.Core.Entities.ConteinerWheathers
import com.example.crazytimes.Core.Entities.LastCitySearch
import com.example.crazytimes.Core.Interfaces.HomeContract
import com.example.crazytimes.Core.Network.Network
import com.example.crazytimes.databinding.ActivityMainBinding
import com.yuyakaido.android.cardstackview.*
import java.util.*
import kotlin.collections.ArrayList


private const val NameCity = "NameOfCity"
private const val ListLastSearchs = "ListLastSearch"
private const val Bundlee = "Bundle"

class HomeActivity : AppCompatActivity() , CardStackAdapter.CardSelected , AdapterRecyclerLastSearch.LastCitySelected {

    private lateinit var manager:CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter
    private lateinit var cardStackView: CardStackView


    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModelHome: ViewModelHome
    private lateinit var adapterLastSearch:AdapterRecyclerLastSearch

    private lateinit var  listLastSearch:MutableList<LastCitySearch>
    private val Detail_Activity_Code = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewModelHome = ViewModelProviders.of(this@HomeActivity).get(ViewModelHome::class.java)
        }
        val view = binding.root
        setContentView(view)
        initCardStack()
        init()
        setUpObservers()
        viewModelHome.getListLastSearchFromService(this)
        if(Network.InternetAvailable(this)){
            viewModelHome.getListWeathersFromService()
        }else{
            Toast.makeText(this,getString(R.string.Error_De_Conexion) , Toast.LENGTH_SHORT).show()
        }

        binding.searchViewHome.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModelHome.getCityDetailFromService(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
    }


    private fun setUpObservers(){

        viewModelHome.getListWeathers().observe(this, Observer {
            adapter = CardStackAdapter(it,this,this)
            cardStackView = binding.cardStack
            cardStackView.layoutManager = manager
            cardStackView.adapter = adapter
            cardStackView.itemAnimator = DefaultItemAnimator()
        })

        viewModelHome.getCitySearch().observe(this, Observer {
            val i = Intent(this,DetailCity::class.java)
            var bundle = Bundle()
            bundle.putParcelable(NameCity,it)
            bundle.putParcelableArrayList(ListLastSearchs , listLastSearch as java.util.ArrayList<out Parcelable>)
            i.putExtra(Bundlee,bundle)
            startActivityForResult(i,Detail_Activity_Code)
        })

        viewModelHome.getLastCitiesSearch().observe(this, Observer {

            if (it != null) {
                listLastSearch = it
                adapterLastSearch = AdapterRecyclerLastSearch(this, it,this)
                binding.recyclerViewLastSearch.adapter = adapterLastSearch
            }
        } )

        viewModelHome.getErrorCitiesSearch().observe(this, Observer {
            Toast.makeText(this,getString(R.string.Sin_resultados_Ultimas_Busqueda) , Toast.LENGTH_SHORT).show()
            listLastSearch = ArrayList()
        })

        viewModelHome.isErrorService().observe(this, Observer {
            Toast.makeText(this, getString(R.string.Ocurrio_un_Problema),Toast.LENGTH_SHORT).show()
        })

    }

    private fun init() {
        val gridLayoutManager =
            GridLayoutManager(applicationContext, 1, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewLastSearch.layoutManager = gridLayoutManager
        binding.recyclerViewLastSearch.setHasFixedSize(true)
        binding.searchViewHome.queryHint = getString(R.string.Buscador)
    }

    private fun initCardStack() {
        manager  = CardStackLayoutManager(this,object : CardStackListener{
            override fun onCardDisappeared(view: View?, position: Int) {
                Log.d("D","none")

            }

            override fun onCardDragging(direction: Direction?, ratio: Float) {
                Log.d("D","none")

            }

            override fun onCardSwiped(direction: Direction?) {
               when(direction){
                   Direction.Right -> {Log.d("D","none")
                   }
                   Direction.Left -> Log.d("D","none")
                   Direction.Top -> Log.d("D","none")
                   Direction.Bottom -> Log.d("D","none")
               }
            }

            override fun onCardCanceled() {
                Log.d("D","none")

            }

            override fun onCardAppeared(view: View?, position: Int) {
                Log.d("D","none")

            }

            override fun onCardRewound() {
                Log.d("D","none")
            }

        })
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.FREEDOM)
        manager.setCanScrollHorizontal(true)
        manager.setSwipeableMethod(SwipeableMethod.Manual)
        manager.setOverlayInterpolator(LinearInterpolator())
    }

    override fun onCardSelected(id:Int) {
        viewModelHome.getCityByIdDetailFromService(id)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Detail_Activity_Code){
            if(resultCode== Activity.RESULT_OK){
                viewModelHome.getListLastSearchFromService(this)
            }
        }
    }
}

