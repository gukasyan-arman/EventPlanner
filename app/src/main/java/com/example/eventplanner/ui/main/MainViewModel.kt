package com.example.eventplanner.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.eventplanner.data.db.EventsRoomDatabase
import com.example.eventplanner.data.db.repository.Repository
import com.example.eventplanner.data.db.models.Event
import com.example.eventplanner.data.network.WeatherRepository
import com.example.eventplanner.data.network.models.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (application: Application, private val weatherRepository: WeatherRepository) : ViewModel() {
    
    //db
    private val repository: Repository
    val allEvents: LiveData<List<Event>>

    //network
    private val _resp = MutableLiveData<Weather>()
    val weatherResp: LiveData<Weather>
        get() = _resp

    init {
        val dao = EventsRoomDatabase.invoke(application).getEventDao()
        repository = Repository(dao)
        allEvents = repository.allEvents
//        getWeather()
    }

    fun updateEvent(event: Event) = viewModelScope.launch (Dispatchers.IO) {
        repository.updateEvent(event)
    }

    fun deleteEvent(event: Event) = viewModelScope.launch (Dispatchers.IO) {
        repository.deleteEvent(event)
    }

     fun insertEvent(event: Event) = viewModelScope.launch (Dispatchers.IO) {
         repository.insertEvent(event)
    }

    fun deleteEventById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteEventById(id)
    }

    fun clearEvent() = viewModelScope.launch(Dispatchers.IO) {
        repository.clearEvent()
    }

//    private fun getWeather() = viewModelScope.launch (Dispatchers.IO) {
//        weatherRepository.getWeather(event.value?.city.toString()).let { response ->
//            if (response.isSuccessful) {
//                _resp.postValue(response.body())
//            } else {
//                Log.d("getWeather", "getWeather() method error: ${response.message()}")
//            }
//        }
//    }

}