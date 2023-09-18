package com.example.android.politicalpreparedness.representative

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import com.example.android.politicalpreparedness.representative.model.Representative
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RepresentativeViewModel(application: Application, private val savedStateHandle: SavedStateHandle): ViewModel() {

    private val database = ElectionDatabase.getInstance(application)
    private val electionsRepository = ElectionsRepository(database)
//    var states: MutableLiveData<String> = MutableLiveData()
    var representatives: MutableLiveData<List<Representative>> = MutableLiveData()
    private var error: MutableLiveData<String> = MutableLiveData()

    val addressLine1: MutableLiveData<String> = MutableLiveData()
    val addressLine2: MutableLiveData<String> = MutableLiveData()
    val cityInput: MutableLiveData<String> = MutableLiveData()
    val zipInput: MutableLiveData<String> = MutableLiveData()
    val stateInput: MutableLiveData<String> = MutableLiveData()

    init {
        addressLine1.value = savedStateHandle["addressLine1"]
        addressLine2.value = savedStateHandle["addressLine2"]
        cityInput.value = savedStateHandle["city"]
        stateInput.value = savedStateHandle["state"]
        zipInput.value = savedStateHandle["zip"]
    }
    /**
     *  The following code will prove helpful in constructing a representative from the API.
     *  This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */
    fun findRepresentatives(address: String) {
        viewModelScope.launch {
            try {
                val (offices, officials) = electionsRepository.getRepresentativesInfo(address)
                representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
                savedStateHandle["addressLine1"] = addressLine1.value.toString()
                savedStateHandle["addressLine2"] = addressLine2.value.toString()
                savedStateHandle["city"] = cityInput.value.toString()
                savedStateHandle["state"] = stateInput.value.toString()
                savedStateHandle["zip"] = zipInput.value.toString()
            } catch (e: HttpException) {
                error.value = e.localizedMessage
            }
        }
    }

    fun toFormattedString(line1: String, line2: String, city: String, state: String, zip: String): String {
        var output = "$line1 "
        if (!line2.isNullOrEmpty()) output = output.plus("$line2, $city, $state $zip")
        return output
    }
}
