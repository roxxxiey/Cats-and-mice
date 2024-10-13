package com.example.netology

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netology.database.AppDatabase
import kotlinx.coroutines.launch

class ScoreActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var statisticsAdapter: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_item_statistic)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        recyclerView = findViewById(R.id.recycler_view_statistics)
        statisticsAdapter = StatisticsAdapter(listOf())
        recyclerView.adapter = statisticsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadStatistics()

        val backMenuScope:Button = findViewById(R.id.button_back_menu)
        backMenuScope.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadStatistics() {
        lifecycleScope.launch {
            val database = AppDatabase.getDatabase(this@ScoreActivity)
            val dao = database.gameStatisticsDao()
            val statisticsList = dao.getLast10Games()
            statisticsAdapter.updateData(statisticsList)
        }
    }
}
