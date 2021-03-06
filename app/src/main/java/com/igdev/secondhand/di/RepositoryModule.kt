package com.igdev.secondhand.di

import com.igdev.secondhand.database.DbHelper
import com.igdev.secondhand.database.MyDatabase
import com.igdev.secondhand.datastore.DataStore
import com.igdev.secondhand.repository.Repository
import com.igdev.secondhand.service.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(apiHelper: ApiHelper, dataStore: DataStore,dbHelper: DbHelper,database: MyDatabase) = Repository(apiHelper,dataStore,dbHelper,database)
}