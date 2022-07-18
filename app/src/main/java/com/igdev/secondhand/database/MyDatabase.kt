package com.igdev.secondhand.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchHistoryEntity::class, WishlistEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase :RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun wishListDao() :WishlistDao
}