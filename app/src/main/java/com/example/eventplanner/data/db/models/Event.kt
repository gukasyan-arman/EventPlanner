package com.example.eventplanner.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "event_table")
data class Event (
    @ColumnInfo
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo
    val city: String?,
    @ColumnInfo
    val title: String?,
    @ColumnInfo
    val description: String?,
    @ColumnInfo
    var temperature: Double?,
    @ColumnInfo
    var weather: String?,
    @ColumnInfo
    val date: String?,
    @ColumnInfo(name = "is_visited")
    var isVisited: Boolean? = false
)