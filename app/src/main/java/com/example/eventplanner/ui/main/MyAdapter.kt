package com.example.eventplanner.ui.main

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.databinding.EventListItemBinding
import com.example.eventplanner.data.db.models.Event
import com.squareup.picasso.Picasso

class MyAdapter(
    private val listener: EventItemClickListener,
    private val eventClickDeleteInterface: EventClickDeleteInterface,
    private val eventClickUpdateInterface: EventClickUpdateInterface,
    private val eventVisitInterface: EventClickVisitInterface,
): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: EventListItemBinding): RecyclerView.ViewHolder(binding.root)

    private val eventList = ArrayList<Event>()
    private val fullList = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        return MyViewHolder(EventListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEvent = eventList[position]
        holder.binding.apply {
            cardEventTitle.text = currentEvent.title
            cardEventCity.text = currentEvent.city
            cardEventDate.text = currentEvent.date
            temperature.text = currentEvent.temperature.toString()
            when (currentEvent.weather) {
                "cloud" -> Picasso.get().load(R.drawable.cloud).into(weatherIcon)
                "little_rainy" -> Picasso.get().load(R.drawable.little_rainy).into(weatherIcon)
                "little_cloudy" -> Picasso.get().load(R.drawable.little_cloudy).into(weatherIcon)
                "sun" -> Picasso.get().load(R.drawable.sun).into(weatherIcon)
                else -> Picasso.get().load(R.drawable.cloud).into(weatherIcon)
            }

            if (currentEvent.isVisited == true) {
                visitedSwitch.isChecked = true
                holder.itemView.alpha = 0.6F
                holder.binding.visitedTv.text = "Event was visited"
            } else {
                visitedSwitch.isChecked = false
                holder.itemView.alpha = 1F
                holder.binding.visitedTv.text = "Event not visited"
            }

            deleteEvent.setOnClickListener {
                eventClickDeleteInterface.onDeleteIconClick(eventList[position])
            }

            editEvent.setOnClickListener {
                eventClickUpdateInterface.onUpdateIconClick(eventList[position])
            }

            visitedSwitch.setOnCheckedChangeListener { _, isChecked ->
                eventVisitInterface.onVisitIconClick(eventList[position])
                if (isChecked) {
                    holder.itemView.alpha = 0.6F
                    holder.binding.visitedTv.text = "Event was visited"
                } else {
                    holder.itemView.alpha = 1F
                    holder.binding.visitedTv.text = "Event not visited"
                }

                val message = if (isChecked) "Switch1:ON" else "Switch1:OFF"
                Log.d("itemMessage", message)
            }
        }

        holder.itemView.setOnClickListener {
            listener.onItemClicked(eventList[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int = eventList.size

    interface EventItemClickListener {
        fun onItemClicked(event: Event)
    }

    interface EventClickDeleteInterface {
        fun onDeleteIconClick(event: Event)
    }

    interface EventClickUpdateInterface {
        fun onUpdateIconClick(event: Event)
    }

    interface EventClickVisitInterface {
        fun onVisitIconClick(event: Event)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Event>) {
        fullList.clear()
        fullList.addAll(newList)

        eventList.clear()
        eventList.addAll(fullList)
        notifyDataSetChanged()
    }

}