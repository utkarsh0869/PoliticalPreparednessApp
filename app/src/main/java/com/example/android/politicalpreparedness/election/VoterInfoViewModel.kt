package com.example.android.politicalpreparedness.election

import android.app.Application
import android.content.Intent
import android.location.Address
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse


class VoterInfoViewModel(
    private val dataSource: ElectionDao,
    application: Application,
    electionId: Int,
) : AndroidViewModel(application) {

    private val database = ElectionDatabase.getInstance(application)
    private val electionsRepository = ElectionsRepository(database)

    private val _election = MutableLiveData<Election>()

    val election: LiveData<Election>
        get() = _election

    init{
        viewModelScope.launch {
            _election.value = electionsRepository.getElectionById(electionId)
        }
    }

    private val _electionDetails = MutableLiveData<VoterInfoResponse>()

    val electionDetails: LiveData<VoterInfoResponse>
        get() = _electionDetails

    fun loadDetails(address: Address) {
        viewModelScope.launch {
            val exactAddress = "${address?.getAddressLine(0)}"
            val response = electionsRepository.getElectionDetails(election.value!!.id, exactAddress)
            _electionDetails.value = response
        }
    }

    fun onOpenWebUrlClicked(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            getApplication<Application>().startActivity(intent)
        } catch (e: Exception) {
            // Handle any exceptions that may occur while opening the URL
            e.printStackTrace()
        }
    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}