package com.example.android.politicalpreparedness.representative.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import com.example.android.politicalpreparedness.network.models.Office
import com.example.android.politicalpreparedness.network.models.Official

@Parcelize
data class Representative (
        val official: Official,
        val office: Office
) : Parcelable