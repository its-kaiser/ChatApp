package com.example.chatapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etUsername.doAfterTextChanged {
            val userName = it.toString()
            binding.btnProceed.isEnabled = userName.isNotEmpty()
        }

        binding.btnProceed.setOnClickListener {
            val userName = binding.etUsername.text.toString()
            if(userName.isNotEmpty()){
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment(userName))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etUsername.requestFocus()
    }
}