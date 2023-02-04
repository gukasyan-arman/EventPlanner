package com.example.eventplanner.ui.editevent

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eventplanner.R
import com.example.eventplanner.databinding.FragmentEditEventBinding
import com.example.eventplanner.data.db.models.Event
import com.example.eventplanner.ui.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditEventFragment : Fragment() {

    private lateinit var binding: FragmentEditEventBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEditTextFields()

        var temperature: Double
        var weather: String

        binding.cancelBtn.setOnClickListener {
            findNavController().navigate(R.id.action_editEventFragment_to_mainFragment)
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

                val updatedEvent = Event(
                    id = arguments?.getString("id")!!,
                    city = binding.enterCityEt.text.trim().toString(),
                    title = binding.enterTitleEt.text.trim().toString(),
                    description = binding.enterDescriptionEt.text.trim().toString(),
                    temperature = temperature,
                    weather = weather,
                    date = binding.enterDateEt.text.trim().toString()
                )
                viewModel.updateEvent(updatedEvent)
                Snackbar.make(view, "event updated successfully", Snackbar.LENGTH_SHORT).show()
                clearEditText()
                findNavController().navigate(R.id.action_editEventFragment_to_mainFragment)
            }

        }
    }

    private fun clearEditText() {
        binding.enterCityEt.text.clear()
        binding.enterTitleEt.text.clear()
        binding.enterDescriptionEt.text.clear()
        binding.enterDateEt.text.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun initEditTextFields( ) {
        if (arguments?.getString("city") == "") {
             binding.enterCityEt.hint = "Enter city"
        } else {
            binding.enterCityEt.setText(arguments?.getString("city"))
        }
        if (arguments?.getString("title") == "") {
             binding.enterTitleEt.hint = "Enter title"
        } else {
            binding.enterTitleEt.setText(arguments?.getString("title"))
        }
        if (arguments?.getString("date") == "") {
             binding.enterDateEt.hint = "Enter date and time"
        } else {
            binding.enterDateEt.setText(arguments?.getString("date"))
        }
        if (arguments?.getString("description") == "") {
             binding.enterDescriptionEt.hint = "Enter description (optional)"
        } else {
            binding.enterDescriptionEt.setText(arguments?.getString("description"))
        }
    }
}