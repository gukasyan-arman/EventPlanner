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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (application: Application, private val weatherRepository: WeatherRepository) : ViewModel() {
    
    //db
    private val repository: Repository
    val allEvents: LiveData<List<Event>>
    val city: MutableLiveData<String>
    val date: MutableLiveData<String>

    //network
    private val _weatherLiveData = MutableLiveData<Weather>()
    val weatherLiveData: LiveData<Weather>
        get() = _weatherLiveData

    init {
        val dao = EventsRoomDatabase.invoke(application).getEventDao()
        repository = Repository(dao)
        allEvents = repository.allEvents
        city = MutableLiveData()
        date = MutableLiveData()
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

    fun clearEvent() = viewModelScope.launch(Dispatchers.IO) {
        repository.clearEvent()
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    fun getWeather(city: String) = viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
        val response = weatherRepository.getWeather(city)
        if (response.isSuccessful) {
            response.body().let {res ->
                _weatherLiveData.postValue(res)
                Log.d("eventItem", "Response success")
            }
        } else {
            Log.d("eventItem", "Response error")
        }
    }
}