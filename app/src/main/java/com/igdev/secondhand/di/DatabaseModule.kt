package com.igdev.secondhand.di

import android.content.Context
import androidx.room.Room
import com.igdev.secondhand.database.DbHelper
import com.igdev.secondhand.database.MyDatabase
import com.igdev.secondhand.database.SearchHistoryDao
import com.igdev.secondhand.service.ApiHelper
import com.igdev.secondhand.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): MyDatabase = Room.databaseBuilder(appContext,MyDatabase::class.java,"secondhand_db").fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesSearchHistoryDao(database: MyDatabase) = database.searchHistoryDao()

    @Singleton
    @Provides
    fun provideDbHelper(database: MyDatabase): DbHelper {
        return DbHelper(database.searchHistoryDao())
    }

}