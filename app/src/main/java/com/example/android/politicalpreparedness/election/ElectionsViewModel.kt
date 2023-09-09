package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Election
import java.util.Date

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(
    val database: ElectionDao,
    application: Application
): AndroidViewModel(application) {

    val elections = database.getAllElections()

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