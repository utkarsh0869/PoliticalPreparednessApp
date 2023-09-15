package com.example.android.politicalpreparedness.election

import android.app.Application
import android.content.Intent
import android.location.Address
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlin.properties.Delegates

class VoterInfoViewModel(
    application: Application,
    electionId: Int,
) : AndroidViewModel(application) {

    private val database = ElectionDatabase.getInstance(application)
    private val electionsRepository = ElectionsRepository(database)

    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private val _buttonText = MutableLiveData<String>()
    val buttonText: LiveData<String>
        get() = _buttonText

    private var isFollowed by Delegates.notNull<Boolean>()

    init{
        viewModelScope.launch {
            _election.value = electionsRepository.getElectionById(electionId)

            isFollowed = _election.value!!.followed
            _buttonText.value = if (isFollowed) "Unfollow" else "Follow"
        }
    }

    private val _electionDetails = MutableLiveData<VoterInfoResponse>()

    val electionDetails: LiveData<VoterInfoResponse>
        get() = _electionDetails

    fun loadDetails(address: Address) {
        viewModelScope.launch {
            val exactAddress = "${address.getAddressLine(0)}"
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
            e.printStackTrace()
        }
    }

    fun onFollowClicked() {
        isFollowed = !isFollowed
        viewModelScope.launch {
            if(isFollowed) {
                electionsRepository.markElectionAsFollowed(election.value!!.id)
                _buttonText.value = "Unfollow"
            } else {
                electionsRepository.markElectionAsUnFollowed(election.value!!.id)
                _buttonText.value = "Follow"
            }
        }
    }

}