package com.igdev.secondhand.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.igdev.secondhand.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {

    companion object {
        private val DATASTORELOGIN = "login_preferences"
        private val ID = intPreferencesKey("id_key")
        private val USERNAME = stringPreferencesKey("username_key")
        private val EMAIL = stringPreferencesKey("email_key")
        private val PASSWORD = stringPreferencesKey("password_key")
        private val NAMA = stringPreferencesKey("nama_key")
        private val IMAGE_KEY = stringPreferencesKey("image_key")
        private val TOKEN = stringPreferencesKey("acces_token")
        private val Context.datastore by preferencesDataStore(name = DATASTORELOGIN)

        const val DEF_ID = -1
        const val DEF_NAMA = "default_nama"
        const val DEF_EMAIL = "default_email@gmail.com"
        const val DEF_USERNAME = "default_username"
        const val DEF_PASSWORD = "default_password"
        const val DEF_IMAGE = "no_image"
        const val DEF_TOKEN = "def_token"
    }


    suspend fun setToken(user: User) {
        context.datastore.edit {
            it[TOKEN] = user.token
        }
    }

    suspend fun getToken(): Flow<User> {
        return context.datastore.data.map {
            User(
                it[TOKEN] ?: DEF_TOKEN
            )
        }
    }

    suspend fun delete() {
        context.datastore.edit {
            it.clear()
        }
    }

}