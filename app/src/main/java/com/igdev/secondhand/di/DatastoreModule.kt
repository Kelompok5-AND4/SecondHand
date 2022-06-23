package com.igdev.secondhand.di

import android.content.Context
import com.igdev.secondhand.datastore.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
class DatastoreModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = DataStore (context)
}