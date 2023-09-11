package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase.Companion.getInstance
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(
    val electionDao: ElectionDao,
    application: Application
): AndroidViewModel(application) {

    private val database = getInstance(application)
    private val electionsRepository = ElectionsRepository(database)

    init {
        viewModelScope.launch {
            electionsRepository.refreshElections()
        }
    }

    val elections = electionsRepository.elections
    //TODO: Create live data val for upcoming elections
    private var upcomingElection = MutableLiveData<Election?>()

    //TODO: Create live data val for saved elections
    private var savedElection = MutableLiveData<Election?>()

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info
    private val _navigateToVoterInfoFragment = MutableLiveData<Election?>()
    val navigateToVoterInfoFragment : MutableLiveData<Election?>
        get() = _navigateToVoterInfoFragment

    fun onVoterInfoFragmentNavigated() {
        _navigateToVoterInfoFragment.value = null
    }

    fun onElectionClicked(election: Election) {
        upcomingElection.value = election
    }

}