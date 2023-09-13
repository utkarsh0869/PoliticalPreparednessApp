package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase.Companion.getInstance
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ElectionsViewModel(
    val electionDao: ElectionDao,
    application: Application
): AndroidViewModel(application) {

    private val database = getInstance(application)
    private val electionsRepository = ElectionsRepository(database)

    private val _navigateTo = MutableLiveData<NavDirections?>()
    val navigateTo: LiveData<NavDirections?> = _navigateTo

    init {
        viewModelScope.launch {
            electionsRepository.refreshElections()
        }
    }

    private val elections = electionsRepository.elections

    val upcomingElections: LiveData<List<Election>> = elections

    val savedElections : LiveData<List<Election>> = electionsRepository.getFollowedElections()

    fun onUpcomingClicked(election: Election) {
        _navigateTo.value = ElectionsFragmentDirections
            .actionElectionsFragmentToVoterInfoFragment(election.id, election.division)
    }

    fun onSavedClicked(election: Election) {
        _navigateTo.value = ElectionsFragmentDirections
            .actionElectionsFragmentToVoterInfoFragment(election.id, election.division)
    }

    fun navigateCompleted() {
        _navigateTo.value = null
    }

}