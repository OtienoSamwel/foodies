package com.moose.foodies.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.moose.foodies.domain.models.Profile
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PreferencesHelper {
    fun preferences(): Preferences
}

class Preferences @Inject constructor(@ApplicationContext context: Context) {

    private val chefKey = "CHEF_KEY"
    private val updateKey = "UPDATE_KEY"
    private val tokenKey = "ACCESS_TOKEN"
    private val preferences = context.getSharedPreferences("FOODIES_PREFS", Context.MODE_PRIVATE)

    fun getToken() = preferences.getString(tokenKey, null)

    fun setToken(token: String?) = preferences.set(tokenKey, token)

    fun getUpdate() = preferences.getString(updateKey, null)

    fun setUpdate(update: String?) = preferences.set(updateKey, update)

    fun getChef(): Profile = Json.decodeFromString(preferences.getString(chefKey, "")!!)

    fun setChef(chef: Profile) = preferences.set(chefKey, Json.encodeToString(chef))

    operator fun SharedPreferences.set(key: String, value: Any?){
        when (value) {
            is Int -> edit { this.putInt(key, value) }
            is Long -> edit { this.putLong(key, value) }
            is Float -> edit { this.putFloat(key, value) }
            is String? -> edit { this.putString(key, value) }
            is Boolean -> edit { this.putBoolean(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }
}