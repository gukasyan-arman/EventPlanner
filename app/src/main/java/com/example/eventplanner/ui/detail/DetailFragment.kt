package com.example.eventplanner.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventplanner.R
import com.example.eventplanner.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

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

    @SuppressLint("SetTextI18n")
    private fun initFields() {
        binding.detailTitle.text = arguments?.getString("title") ?: "title"
        binding.detailCity.text = arguments?.getString("city") ?: "city"
        binding.detailDate.text = arguments?.getString("date") ?: "date"
        binding.detailTemperature.text = arguments?.getDouble("temperature").toString()
        binding.detailDescription.text = arguments?.getString("description") ?: "description"

        if (arguments?.getBoolean("isVisited") == true) {
            binding.detailVisited.text = "Event was visited"
        } else {
            binding.detailVisited.text = "Event not visited"
        }

        when (arguments?.getString("weather")) {
            "Mist" -> Picasso.get().load(R.drawable.cloud).into(binding.detailImage)
            "Rainy" -> Picasso.get().load(R.drawable.little_rainy).into(binding.detailImage)
            "Partly cloudy" -> Picasso.get().load(R.drawable.little_cloudy).into(binding.detailImage)
            "Sunny" -> Picasso.get().load(R.drawable.sun).into(binding.detailImage)
            else -> Picasso.get().load(R.drawable.cloud).into(binding.detailImage)
        }

    }

}