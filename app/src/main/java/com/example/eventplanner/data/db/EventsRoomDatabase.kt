package com.example.eventplanner.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eventplanner.data.db.dao.EventsDao
import com.example.eventplanner.data.db.models.Event

@Database(entities = [Event::class], version = 2, exportSchema = false)
abstract class EventsRoomDatabase: RoomDatabase() {

    abstract fun getEventDao(): EventsDao

    companion object {
        @Volatile
        private var instance: EventsRoomDatabase ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context: Context): EventsRoomDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                EventsRoomDatabase::class.java,
                "database"
            ).build()
        }
    }
}