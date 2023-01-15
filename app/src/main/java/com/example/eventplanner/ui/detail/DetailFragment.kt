package com.example.eventplanner.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventplanner.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()

    }

    private fun initFields() {
        binding.detailTitle.text = arguments?.getString("title") ?: "title"
        binding.detailCity.text = arguments?.getString("city") ?: "city"
        binding.detailDate.text = arguments?.getString("date") ?: "date"
        binding.detailDescription.text = arguments?.getString("description") ?: "description"
    }

}