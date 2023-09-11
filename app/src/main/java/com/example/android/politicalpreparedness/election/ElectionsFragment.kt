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

        binding.lifecycleOwner = this

        val adapter = ElectionListAdapter(ElectionListener { election ->
            electionsViewModel.onElectionClicked(election)
        })

        binding.upcomingElectionsRV.adapter = adapter

        electionsViewModel.navigateToVoterInfoFragment.observe(viewLifecycleOwner) { election ->
            election?.let {
                this.findNavController().navigate(
                    ElectionsFragmentDirections
                        .actionElectionsFragmentToVoterInfoFragment(it.id, it.division)
                )
                electionsViewModel.onVoterInfoFragmentNavigated()
            }
        }

        electionsViewModel.elections.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
        // TODO: Add ViewModel values and create ViewModel

        // TODO: Add binding values

        // TODO: Link elections to voter info

        // TODO: Initiate recycler adapters

        // TODO: Populate recycler adapters
        return binding.root
    }

    // TODO: Refresh adapters when fragment loads
}