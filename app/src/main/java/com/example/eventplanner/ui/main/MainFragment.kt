package com.example.eventplanner.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventplanner.R
import com.example.eventplanner.databinding.FragmentMainBinding
import com.example.eventplanner.data.db.models.Event
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), MyAdapter.EventItemClickListener,
    MyAdapter.EventClickDeleteInterface, MyAdapter.EventClickUpdateInterface, MyAdapter.EventClickVisitInterface {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var eventAdapter: MyAdapter
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        viewModel.allEvents.observe(viewLifecycleOwner) {list ->
            list.let {
                eventAdapter.updateList(it)
            }
            Log.d("allEvents", "$list")
        }

        binding.addEventBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment)
        }

    }

    private fun initAdapter() {
        eventAdapter = MyAdapter(this, this, this, this)
        binding.eventRv.setHasFixedSize(true)
        binding.eventRv.layoutManager = LinearLayoutManager(requireContext())
        binding.eventRv.adapter = eventAdapter
    }

    override fun onItemClicked(event: Event) {
        val bundle = Bundle()
        bundle.putString("id", event.id)
        bundle.putString("title", event.title)
        bundle.putString("city", event.city)
        bundle.putString("description", event.description)
        bundle.putString("date", event.date)
        bundle.putDouble("temperature", event.temperature!!)
        bundle.putBoolean("isVisited", event.isVisited!!)
        bundle.putString("weather", event.weather)
        Toast.makeText(requireContext(), "$event click", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }

    override fun onDeleteIconClick(event: Event) {
        viewModel.deleteEvent(event)
        Toast.makeText(requireContext(), "Event <${event.title}> deleted", Toast.LENGTH_LONG).show()
    }

    override fun onUpdateIconClick(event: Event) {
        val bundle = Bundle()
        bundle.putString("id", event.id)
        bundle.putString("title", event.title)
        bundle.putString("city", event.city)
        bundle.putString("description", event.description)
        bundle.putString("date", event.date)
        Toast.makeText(requireContext(), "$event update", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_mainFragment_to_editEventFragment, bundle)
    }

    override fun onVisitIconClick(event: Event) {
        val bundle = Bundle()
        viewModel.updateEvent(
            event = Event(
                id = event.id,
                isVisited = !event.isVisited!!,
                city = event.city,
                title = event.title,
                description = event.description,
                temperature = event.temperature,
                weather = event.weather,
                date = event.date
            ))
        bundle.putBoolean("isVisited", event.isVisited!!)
        Toast.makeText(requireContext(), "Event <${event.title}> visited", Toast.LENGTH_SHORT).show()
    }

}