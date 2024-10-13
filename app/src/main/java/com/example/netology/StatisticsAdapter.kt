package com.example.netology

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.netology.database.GameStatistics

class StatisticsAdapter(private var statisticsList: List<GameStatistics>) : RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val totalClicks: TextView = view.findViewById(R.id.text_total_clicks)
        val mouseClicks: TextView = view.findViewById(R.id.text_mouse_clicks)
        val hitPercentage: TextView = view.findViewById(R.id.text_hit_percentage)
        val duration: TextView = view.findViewById(R.id.text_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_statistic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val statistics = statisticsList[position]
        holder.totalClicks.text = "Total Clicks: ${statistics.totalClicks}"
        holder.mouseClicks.text = "Mouse Clicks: ${statistics.mouseClicks}"
        holder.hitPercentage.text = "Hit Percentage: ${statistics.hitPercentage}%"
        holder.duration.text = "Duration: ${statistics.duration / 1000} seconds"
    }

    override fun getItemCount() = statisticsList.size

    fun updateData(newStatisticsList: List<GameStatistics>) {
        statisticsList = newStatisticsList
        notifyDataSetChanged()
    }
}
