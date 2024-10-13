package com.example.netology

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }

        enableEdgeToEdge()


        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val spinnerMouseCount: Spinner = findViewById(R.id.spinner_mouse_count)
        val seekBarSpeed: SeekBar = findViewById(R.id.seekbar_speed)
        val seekBarSize: SeekBar = findViewById(R.id.seekbar_size)
        val startButton: Button = findViewById(R.id.button_start_game)


        ArrayAdapter.createFromResource(
            this,
            R.array.mouse_count_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMouseCount.adapter = adapter
        }


        startButton.setOnClickListener {

            val selectedMouseCount = spinnerMouseCount.selectedItem?.toString()?.toIntOrNull()

            if (selectedMouseCount == null) {
                Toast.makeText(this, "Выберите количество мышек", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val speed = seekBarSpeed.progress
            val size = seekBarSize.progress


            if (selectedMouseCount in 1..5) {

                val intent = Intent(this, GameActivity::class.java).apply {
                    putExtra("MOUSE_COUNT", selectedMouseCount)
                    putExtra("SPEED", speed)
                    putExtra("SIZE", size)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Выберите корректное количество мышек (от 1 до 5)", Toast.LENGTH_SHORT).show()
            }
        }

        val scoreButton: Button = findViewById(R.id.button_score)

        scoreButton.setOnClickListener {
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }
    }
}
