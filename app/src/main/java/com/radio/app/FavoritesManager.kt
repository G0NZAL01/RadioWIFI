package com.radio.app

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class FavoritesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("radio_data", Context.MODE_PRIVATE)

    fun isFavorite(id: String): Boolean = prefs.getBoolean("fav_$id", false)

    fun toggleFavorite(id: String): Boolean {
        val current = isFavorite(id)
        if (current) {
            removeFavorite(id)
        } else {
            addFavorite(id)
        }
        return !current
    }

    private fun addFavorite(id: String) {
        prefs.edit().putBoolean("fav_$id", true).apply()
        val order = getFavoriteOrder().toMutableList()
        if (id !in order) order.add(id)
        saveFavoriteOrder(order)
    }

    fun removeFavorite(id: String) {
        prefs.edit().remove("fav_$id").apply()
        val order = getFavoriteOrder().toMutableList()
        order.remove(id)
        saveFavoriteOrder(order)
        unHideStation(id)
    }

    fun getFavoriteIds(): Set<String> =
        prefs.all.filter { it.key.startsWith("fav_") && it.value == true }
            .map { it.key.removePrefix("fav_") }.toSet()

    fun getFavoriteIdsOrdered(): List<String> {
        val order = getFavoriteOrder()
        val allFavs = getFavoriteIds()
        val ordered = order.filter { it in allFavs }
        val missing = allFavs - ordered.toSet()
        return ordered + missing.toList()
    }

    fun setFavoriteOrder(ids: List<String>) {
        saveFavoriteOrder(ids)
    }

    private fun getFavoriteOrder(): List<String> {
        val json = prefs.getString("favorite_order", "[]") ?: "[]"
        val arr = JSONArray(json)
        return (0 until arr.length()).map { arr.getString(it) }
    }

    private fun saveFavoriteOrder(ids: List<String>) {
        val arr = JSONArray(ids)
        prefs.edit().putString("favorite_order", arr.toString()).apply()
    }

    fun isHidden(id: String): Boolean = id in getHiddenIds()

    fun hideStation(id: String) {
        val hidden = getHiddenIds().toMutableSet()
        hidden.add(id)
        saveHiddenIds(hidden)
    }

    fun unHideStation(id: String) {
        val hidden = getHiddenIds().toMutableSet()
        hidden.remove(id)
        saveHiddenIds(hidden)
    }

    fun getHiddenIds(): Set<String> {
        val json = prefs.getString("hidden_ids", "[]") ?: "[]"
        val arr = JSONArray(json)
        return (0 until arr.length()).map { arr.getString(it) }.toSet()
    }

    private fun saveHiddenIds(ids: Set<String>) {
        val arr = JSONArray(ids.toList())
        prefs.edit().putString("hidden_ids", arr.toString()).apply()
    }

    fun getCustomStations(): List<RadioStation> {
        val json = prefs.getString("custom_stations", "[]") ?: "[]"
        val arr = JSONArray(json)
        val list = mutableListOf<RadioStation>()
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            list.add(RadioStation(
                id = obj.getString("id"),
                name = obj.getString("name"),
                url = obj.getString("url"),
                category = "Personalizadas"
            ))
        }
        return list
    }

    fun addCustomStation(name: String, url: String): Boolean {
        val current = getCustomStations()
        if (current.any { it.url == url }) return false
        val id = "custom_${System.currentTimeMillis()}"
        val newStation = RadioStation(id, name, url, "Personalizadas")
        val all = current + newStation
        saveCustomStations(all)
        addFavorite(id)
        return true
    }

    fun removeCustomStation(id: String) {
        val current = getCustomStations().filter { it.id != id }
        saveCustomStations(current)
        removeFavorite(id)
    }

    private fun saveCustomStations(stations: List<RadioStation>) {
        val arr = JSONArray()
        stations.forEach { s ->
            arr.put(JSONObject().apply {
                put("id", s.id)
                put("name", s.name)
                put("url", s.url)
            })
        }
        prefs.edit().putString("custom_stations", arr.toString()).apply()
    }
}
