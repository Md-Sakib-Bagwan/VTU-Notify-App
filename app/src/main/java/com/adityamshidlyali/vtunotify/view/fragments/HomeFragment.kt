package com.adityamshidlyali.vtunotify.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adityamshidlyali.vtunotify.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding: FragmentHomeBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardViewCirculars.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCircularNotificationFragment()
            findNavController().navigate(action)
        }

        binding.cardViewWorkshops.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToWorkshopsFragment()
            findNavController().navigate(action)
        }

        binding.cardViewExamCirculars.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToExamCircularsFragment()
            findNavController().navigate(action)
        }

        binding.cardViewExamTimeTable.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToExamTimeTableFragment()
            findNavController().navigate(action)
        }

        binding.cardViewPlacement.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToPlacementFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}