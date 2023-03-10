package com.example.eventplanner.ui.addevent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eventplanner.R
import com.example.eventplanner.databinding.FragmentAddEventBinding
import com.example.eventplanner.data.db.models.Event
import com.example.eventplanner.ui.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEventFragment : Fragment() {

    private lateinit var binding: FragmentAddEventBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var temperature: Double
        var weather: String

        binding.cancelBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
        }

        binding.saveBtn.setOnClickListener {
            val city = binding.enterCityEt.text.trim().toString()
            val date = binding.enterDateEt.text.trim().toString()
            viewModel.city.postValue(city)
            viewModel.date.postValue(date)

            viewModel.getWeather(city)

            viewModel.weatherLiveData.observe(viewLifecycleOwner) {
                temperature = it.current.temp_c
                weather = it.current.condition.text

                Log.d("eventItem", "temperature = $temperature")
                Log.d("eventItem", "weather = $weather")

                val event = Event(
                    city = city,
                    title = binding.enterTitleEt.text.trim().toString(),
                    description = binding.enterDescriptionEt.text.trim().toString(),
                    temperature = temperature,
                    weather = weather,
                    date = date
                )
                viewModel.insertEvent(event)
                Log.d("eventItem", "eventItem = $event")
                Snackbar.make(view, "event added successfully", Snackbar.LENGTH_SHORT).show()

                clearEditText()

                findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
            }
        }
    }

    private fun clearEditText() {
        binding.enterCityEt.text.clear()
        binding.enterTitleEt.text.clear()
        binding.enterDescriptionEt.text.clear()
        binding.enterDateEt.text.clear()
    }

}