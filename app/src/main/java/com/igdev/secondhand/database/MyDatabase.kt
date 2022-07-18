package com.igdev.secondhand.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.igdev.secondhand.model.PagingProduct.Data

@Database(entities = [SearchHistoryEntity::class,Data::class,RemoteKeys::class], version = 1, exportSchema = false)
abstract class MyDatabase :RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun productDao(): ProductDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}