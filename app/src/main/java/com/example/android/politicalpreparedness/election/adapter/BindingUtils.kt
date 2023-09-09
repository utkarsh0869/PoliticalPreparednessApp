package com.example.android.politicalpreparedness.election.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.politicalpreparedness.network.models.Election

@BindingAdapter("electionTitleTV")
fun TextView.setElectionTitle(item: Election?) {
    item?.let {
        text = it.name
    }
}

@BindingAdapter("electionDateAndTimeTV")
fun TextView.setElectionDateAndTimeTV(item: Election?) {
    item?.let {
        text = it.electionDay.toString()
    }
}