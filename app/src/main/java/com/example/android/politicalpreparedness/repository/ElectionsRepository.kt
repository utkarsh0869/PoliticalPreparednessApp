package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching elections from the network and storing then on disk. Like an API for our
 * datasource.
 */
class ElectionsRepository(private val database: ElectionDatabase) {

    /**
     * A list of elections that can be shown on the screen.
     */
    val elections: LiveData<List<Election>> = database.electionDao.getAllElections()

    /**
     * Method to refresh the offline cache.
     */
    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            try {
                val election = CivicsApi.retrofitService.getElections().elections
                database.electionDao.insertAll(election)
            } catch (err : Exception) {
                Log.e("ElectionsRepo", err.message.toString())
            }

        }
    }
}