package com.example.mytrainingsession

import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private val viewModel: WorkoutViewModel by viewModels()

    private lateinit var startButton: Button
    private lateinit var titleTV: TextView
    private lateinit var exerciseTV: TextView
    private lateinit var descriptionTV: TextView
    private lateinit var completeButton: Button
    private lateinit var timerTV: TextView
    private lateinit var imageView: ImageView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Установка окна без ActionBar
        WindowCompat.setDecorFitsSystemWindows(window, true)

        // Привязка виджетов интерфейса по их идентификаторам
        startButton = findViewById(R.id.startButtonBTN)
        titleTV = findViewById(R.id.titleTV)
        exerciseTV = findViewById(R.id.exerciseTV)
        descriptionTV = findViewById(R.id.descriptionTV)
        completeButton = findViewById(R.id.completeButtonBTN)
        timerTV = findViewById(R.id.timerTV)
        imageView = findViewById(R.id.imageViewIV)
        toolbar = findViewById(R.id.toolbar)

        // Установка Toolbar
        setSupportActionBar(toolbar)

        // Управление отступами под системными окнами
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
            val systemBars = insets.systemWindowInsetTop
            val additionalOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics).toInt()
            toolbar.updatePadding(top = systemBars + additionalOffset)
            insets
        }

        // Наблюдение за изменениями в ViewModel
        viewModel.exerciseIndex.observe(this, Observer { index ->
            // Обновление индекса упражнения
        })

        viewModel.currentExercise.observe(this, Observer { exercise ->
            exercise?.let {
                exerciseTV.text = it.name
                descriptionTV.text = it.description
                imageView.setImageResource(it.gifImage)
            }
        })

        viewModel.timerSecondsLeft.observe(this, Observer { seconds ->
            timerTV.text = formatTime(seconds)
        })

        viewModel.isCompleteButtonEnabled.observe(this, Observer { isEnabled ->
            completeButton.isEnabled = isEnabled
        })

        viewModel.startButtonText.observe(this, Observer { text ->
            startButton.text = text
        })

        viewModel.titleText.observe(this, Observer { text ->
            titleTV.text = text
        })

        viewModel.exerciseText.observe(this, Observer { text ->
            exerciseTV.text = text
        })

        viewModel.descriptionText.observe(this, Observer { text ->
            descriptionTV.text = text
        })

        viewModel.timerText.observe(this, Observer { text ->
            timerTV.text = text
        })

        viewModel.isTimerRunning.observe(this, Observer { isRunning ->
            if (!isRunning) {
                imageView.setImageResource(0)
            }
        })

        // Установка обработчиков нажатий на кнопки
        startButton.setOnClickListener {
            viewModel.startWorkout()
        }

        completeButton.setOnClickListener {
            viewModel.completeExercise()
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity() // Завершение всех активностей и выход из приложения
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Метод formatTime перемещен в MainActivity
    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}