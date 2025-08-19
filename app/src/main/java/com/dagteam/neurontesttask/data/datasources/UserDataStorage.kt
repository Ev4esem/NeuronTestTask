package com.dagteam.neurontesttask.data.datasources

import android.content.Context
import android.content.SharedPreferences
import com.dagteam.neurontesttask.domain.entities.UserData
import kotlinx.serialization.json.Json
import androidx.core.content.edit

class UserDataStorage(context: Context) {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("user_data_prefs", Context.MODE_PRIVATE)
    
    private val json = Json { ignoreUnknownKeys = true }
    
    companion object {
        private const val USER_DATA_KEY = "user_data"
    }
    
    fun saveUserData(userData: UserData) {
        val userDataJson = json.encodeToString(userData)
        sharedPreferences.edit {
            putString(USER_DATA_KEY, userDataJson)
        }
    }
    
    fun getUserData(): UserData {
        val userDataJson = sharedPreferences.getString(USER_DATA_KEY, null)
        return if (userDataJson != null) {
            try {
                json.decodeFromString<UserData>(userDataJson)
            } catch (e: Exception) {
                UserData.Empty
            }
        } else {
            UserData.Empty
        }
    }
}