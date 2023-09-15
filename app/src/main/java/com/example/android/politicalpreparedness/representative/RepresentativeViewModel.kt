package com.example.android.politicalpreparedness.representative

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import com.example.android.politicalpreparedness.representative.model.Representative
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RepresentativeViewModel(application: Application): ViewModel() {

    private val database = ElectionDatabase.getInstance(application)
    private val electionsRepository = ElectionsRepository(database)
    var state: MutableLiveData<String> = MutableLiveData()
    var representatives: MutableLiveData<List<Representative>> = MutableLiveData()
    private var error: MutableLiveData<String> = MutableLiveData()

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
