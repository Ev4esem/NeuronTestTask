package com.dagteam.neurontesttask.data.datasources

import android.content.Context
import com.dagteam.neurontesttask.data.entities.PurchasesDTO
import com.dagteam.neurontesttask.data.utils.DateFormatter
import com.dagteam.neurontesttask.domain.entities.Purchase
import kotlinx.serialization.json.Json
import java.io.IOException


class LocalDataSource(private val context: Context) {
    
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getLocalData(fileName: String = "data.json"): List<Purchase> {
        return try {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val container = json.decodeFromString<PurchasesDTO>(jsonString)
            container.data.map { item ->
                Purchase(
                    date = DateFormatter.formatDate(item.date),
                    name = item.name
                )
            }
        } catch (e: IOException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}