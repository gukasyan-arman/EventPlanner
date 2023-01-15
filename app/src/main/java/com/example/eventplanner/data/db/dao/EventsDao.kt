package com.example.eventplanner.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.eventplanner.data.db.models.Event

@Dao
interface EventsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("SELECT * FROM event_table ORDER BY date DESC")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM event_table WHERE id = :id")
    fun getEventById(id: String): LiveData<Event>

    @Query("DELETE FROM event_table")
    suspend fun clearEvents()

    @Query("DELETE FROM event_table WHERE id = :id")
    suspend fun deleteEventById(id: Int)
}