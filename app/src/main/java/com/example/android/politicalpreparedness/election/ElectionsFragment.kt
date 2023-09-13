package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsFragment: Fragment() {

    // TODO: Declare ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {

        val binding: FragmentElectionBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_election, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = ElectionDatabase.getInstance(application).electionDao

        val viewModelFactory = ElectionsViewModelFactory(dataSource, application)

        val electionsViewModel =
            ViewModelProvider(this, viewModelFactory)[ElectionsViewModel::class.java]

        binding.electionsViewModel = electionsViewModel

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ElectionListAdapter(ElectionListener { election ->
            electionsViewModel.onUpcomingClicked(election)
        })

        binding.upcomingElectionsRV.adapter = adapter

        electionsViewModel.upcomingElections.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }

        val adapter2 = ElectionListAdapter(ElectionListener { election ->
            electionsViewModel.onSavedClicked(election)
        })

        binding.savedElectionsRV.adapter = adapter2

        electionsViewModel.savedElections.observe(viewLifecycleOwner) {
            it?.let {
                adapter2.submitList(it)
            }
        }

        electionsViewModel.navigateTo.observe(viewLifecycleOwner) {
            it?.let {
                electionsViewModel.navigateCompleted()
                findNavController().navigate(it)
            }
        }

        return binding.root
    }

    // TODO: Refresh adapters when fragment loads
}