package com.example.android.politicalpreparedness.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
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
                database.electionDao.deleteAll()
                database.electionDao.insertAll(election)
            } catch (err : Exception) {
                Log.e("ElectionsRepo", err.message.toString())
            }
        }
    }

    suspend fun getElectionById(electionId: Int): Election? {
        return database.electionDao.getElectionById(electionId)
    }

    suspend fun getElectionDetails(id: Int, exactAddress: String): VoterInfoResponse {
        return CivicsApi.retrofitService.getVoterInfo(exactAddress, id)
    }

    suspend fun markElectionAsFollowed(electionId: Int) {
        database.electionDao.markAsFollowed(electionId)
        Log.d("ER", "${database.electionDao.getElectionById(electionId)!!.followed}")
    }

    fun getFollowedElections(): LiveData<List<Election>> {
        return database.electionDao.getFollowedElections()
    }

    suspend fun markElectionAsUnFollowed(electionId: Int) {
        database.electionDao.markAsUnFollowed(electionId)
    }

    suspend fun getRepresentativesInfo(address: String) : RepresentativeResponse {
        return CivicsApi.retrofitService.getRepresentatives(address.toString())
    }
}