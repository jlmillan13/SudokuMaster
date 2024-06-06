package com.jlmillan.sudokumaster.ui.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.ui.common.MainActivity
import com.jlmillan.sudokumaster.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        setUpObservers()
        setUpClickListener()
        return binding?.root
    }

    private fun setUpObservers() {
        viewModel.loadingLiveData().removeObservers(this)
        viewModel.loadingLiveData().observe(viewLifecycleOwner) { loading ->
            (activity as? MainActivity)?.showLoading(loading)
        }

        viewModel.loggedLiveData().removeObservers(this)
        viewModel.loggedLiveData().observe(viewLifecycleOwner) { isLogged ->
            if (isLogged) {
                findNavController().navigate(R.id.action_homeFragment_to_mainFragment)
            }
        }
    }

    private fun setUpClickListener() {
        binding?.homeLoginBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        binding?.homeRegisterBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showToolbar(false)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}