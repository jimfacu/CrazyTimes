# CrazyTimes

CrazyTimes es un ejercicio mobile simple en Android para poder saber el clima de ciudades , hecho con la arquitectura MVVM y usando BindingView.

## Descripción

Mediante Retrofit hacemos peticiones a la API de OpenWeathermap, guardando los resultados para ser mostrados en una interfaz diseñada en base 
a elementos de Material Design.Transformando objetos JSON a objetos JAVA mediante la librería de Google GSON.La informacion se muestra en 
un CardStack , tambien se cuenta con la posibilidad de poder buscar la ciudad que desees y un sector donde se muestra las ultimas 5 ciudades buscadas.
Al seleccionar una ciudad se lleva a una pantalla donde se muestra la localizacion de la ciudad en un map y sus 50 ciudades limitrofes con la posibilidad de poder ver tambien 
su clima.



## Librerías

WEB SERVICES
  * **Retrofit**          'com.squareup.retrofit2:retrofit:2.5.0'
  * **API** **REST** **OpenWeathermap** 
   
MATERIAL DESIGN

  * **RecyclerView**   'com.android.support:recyclerview-v7:29.1.1'
  * **Material**     'com.google.android.material:material:1.0.0'
  
   
EXTRA COMPLEMENTS
   
  * **Glide**            
  * **BindingView**      
# MATERIAL ELEMENTS

    - RecyclerView
