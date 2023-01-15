package com.example.eventplanner.data.db.repository

import androidx.lifecycle.LiveData
import com.example.eventplanner.data.db.dao.EventsDao
import com.example.eventplanner.data.db.models.Event
import kotlinx.coroutines.flow.Flow

class Repository(private val eventsDao: EventsDao) {
    val allEvents: LiveData<List<Event>> = eventsDao.getAllEvents()

    suspend fun insertEvent(event: Event) {
        eventsDao.insertEvent(event)
    }

    suspend fun deleteEvent(event: Event) {
        eventsDao.deleteEvent(event)
    }

    suspend fun updateEvent(event: Event) {
        eventsDao.updateEvent(event)
    }

    suspend fun deleteEventById(id: Int) = eventsDao.deleteEventById(id)

    suspend fun clearEvent() = eventsDao.clearEvents()

    suspend fun getEvent(id: String): LiveData<Event> {
        return eventsDao.getEventById(id)
    }

}