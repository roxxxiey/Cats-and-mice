package com.example.netology

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.netology.database.AppDatabase
import com.example.netology.database.GameStatistics
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private var totalClicks = 0
    private var mouseClicks = 0
    private lateinit var db: AppDatabase
    private val mouseImageViews = mutableListOf<ImageView>()
    private var gameDuration: Long = 0
    private var startTime: Long = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var mouseSpeed: Int = 1000
    private var mouseSize: Int = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        db = AppDatabase.getDatabase(this)

        val mouseCount = intent.getIntExtra("MOUSE_COUNT", 1)


        mouseSpeed = intent.getIntExtra("SPEED", 1000)
        mouseSize = intent.getIntExtra("SIZE", 50)


        for (i in 0 until mouseCount) {
            val mouseImageView = ImageView(this).apply {
                val scaledSize = scaleMouseSize(mouseSize)
                layoutParams = ConstraintLayout.LayoutParams(scaledSize, scaledSize)
                setImageResource(R.drawable.mouseicon)
                contentDescription = getString(R.string.mouse_description)
                setOnClickListener {
                    mouseClicks++
                    totalClicks++
                    Toast.makeText(this@GameActivity, "Попал в мышку!", Toast.LENGTH_SHORT).show()
                }
            }
            mouseImageViews.add(mouseImageView)
            findViewById<ConstraintLayout>(R.id.main).addView(mouseImageView)
        }

        findViewById<ConstraintLayout>(R.id.main).setOnClickListener {
            totalClicks++
            Toast.makeText(this, "Нажатие не по мышке!", Toast.LENGTH_SHORT).show()
        }
        val startButton: Button = findViewById(R.id.button_start_game)


        startButton.setOnClickListener {
            startGame()
        }

        val backMenu: Button = findViewById(R.id.back_menu)

        backMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        handler = Handler()

    }


    private fun scaleMouseSize(progress: Int): Int {

        return 50 + (progress * (150 - 50) / 100)
    }


    private fun scaleDuration(progress: Int): Int {

        return 3000 - (progress * (3000 - 500) / 100)
    }

    private fun startGame() {
        totalClicks = 0
        mouseClicks = 0
        startTime = System.currentTimeMillis()

        moveMice()


        runnable = Runnable {
            moveMice()
            handler.postDelayed(runnable, 500)
        }
        handler.post(runnable)
    }

    private fun moveMice() {
        mouseImageViews.forEach { mouseImageView ->
            moveMouse(mouseImageView)
        }
    }

    private fun moveMouse(mouseImageView: ImageView) {
        val randomX = Random.nextInt(0, resources.displayMetrics.widthPixels - mouseImageView.width)
        val randomY = Random.nextInt(0, resources.displayMetrics.heightPixels - mouseImageView.height)


        mouseImageView.animate()
            .x(randomX.toFloat())
            .y(randomY.toFloat())
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(scaleDuration(mouseSpeed).toLong())
            .start()
    }

    override fun onStop() {
        super.onStop()

        gameDuration = System.currentTimeMillis() - startTime
        saveStatistics()
        handler.removeCallbacks(runnable)
    }

    private fun saveStatistics() {
        val hitPercentage = if (totalClicks > 0) {
            (mouseClicks.toFloat() / totalClicks) * 100
        } else {
            0f
        }

        val gameStatistics = GameStatistics(
            totalClicks = totalClicks,
            mouseClicks = mouseClicks,
            hitPercentage = hitPercentage,
            duration = gameDuration
        )

        lifecycleScope.launch {
            db.gameStatisticsDao().insert(gameStatistics)
            Toast.makeText(this@GameActivity, "Статистика сохранена!", Toast.LENGTH_SHORT).show()
        }
    }

}
