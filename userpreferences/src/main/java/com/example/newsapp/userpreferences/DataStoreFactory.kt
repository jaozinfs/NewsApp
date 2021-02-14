
package com.example.newsapp.userpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataStoreFactoryImpl(
    private val context: Context
) : DataStoreFactory, KoinComponent {
    override fun createDataStore(fileName: String): DataStore<Preferences> =
        context.createDataStore(fileName)

}

interface DataStoreFactory {
    fun createDataStore(fileName: String): DataStore<Preferences>
}