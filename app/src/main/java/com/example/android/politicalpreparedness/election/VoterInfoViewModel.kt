package com.example.android.politicalpreparedness.election

import android.app.Application
import android.content.Intent
import android.location.Address
import android.net.Uri
import android.util.Log
import android.widget.Button
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

    private val _buttonText = MutableLiveData<String>()
    val buttonText: LiveData<String>
        get() = _buttonText

    private val _isFollowingLD = MutableLiveData<Boolean>(false)
    val isFollowingLD : LiveData<Boolean>
        get() = _isFollowingLD

    init{
        viewModelScope.launch {
            _election.value = electionsRepository.getElectionById(electionId)

            // Check if the election is followed and set buttonText accordingly
            val isFollowed = _election.value!!.followed
            _buttonText.value = if (isFollowed) "Unfollow" else "Follow"
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


//    private var isFollowing = false
//
//    fun onFollowClicked() {
//        isFollowing = !isFollowing
//        viewModelScope.launch {
//            if(isFollowing) {
//                electionsRepository.markElectionAsFollowed(election.value!!.id)
//                _buttonText.value = "Unfollow"
//            } else {
//                electionsRepository.markElectionAsUnFollowed(election.value!!.id)
//                _buttonText.value = "Follow"
//            }
////            Log.d("VIFVM", "followed value before marking as followed ${election.value!!.followed}")
//        }
//    }

    fun onFollowClicked() {
        _isFollowingLD.value = !_isFollowingLD.value!!
        viewModelScope.launch {
            if(_isFollowingLD.value!!) {
                electionsRepository.markElectionAsFollowed(election.value!!.id)
                _buttonText.value = "Unfollow"
            } else {
                electionsRepository.markElectionAsUnFollowed(election.value!!.id)
                _buttonText.value = "Follow"
            }
//            Log.d("VIFVM", "followed value before marking as followed ${election.value!!.followed}")
        }
    }

}