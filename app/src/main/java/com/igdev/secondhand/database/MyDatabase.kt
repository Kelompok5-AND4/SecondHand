package com.igdev.secondhand.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchHistoryEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase :RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}