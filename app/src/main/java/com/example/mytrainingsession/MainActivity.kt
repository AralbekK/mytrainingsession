package com.example.mytrainingsession

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Главная активность, отображающая информацию о тренировке и управляемая логикой времени и текущим упражнением.
 */
class MainActivity : AppCompatActivity() {

    // Список всех упражнений, загруженных из базы данных упражнений
    private val exercises = ExerciseDataBase.exercises

    // Виджеты интерфейса, используемые в приложении
    private lateinit var startButton: Button
    private lateinit var titleTV: TextView
    private lateinit var exerciseTV: TextView
    private lateinit var descriptionTV: TextView
    private lateinit var completeButton: Button
    private lateinit var timerTV: TextView
    private lateinit var imageView: ImageView

    // Индекс текущего упражнения и ссылка на это упражнение
    private var exerciseIndex = 0
    private lateinit var currentExercise: Exercise
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Привязка виджетов интерфейса по их идентификаторам
        startButton = findViewById(R.id.startButtonBTN)
        titleTV = findViewById(R.id.titleTV)
        exerciseTV = findViewById(R.id.exerciseTV)
        descriptionTV = findViewById(R.id.descriptionTV)
        completeButton = findViewById(R.id.completeButtonBTN)
        timerTV = findViewById(R.id.timerTV)
        imageView = findViewById(R.id.imageViewIV)

        // Установка обработчиков нажатий на кнопки
        startButton.setOnClickListener {
            startWorkout()
        }

        completeButton.setOnClickListener {
            completeExercise()
        }
    }

    /**
     * Окончание текущего упражнения
     */
    private fun completeExercise() {
        timer.cancel()
        completeButton.isEnabled = false
        startNextExercise()
    }

    /**
     * Начало тренировки: инициализация первого упражнения и настройкиUI
     */
    private fun startWorkout() {
        exerciseIndex = 0
        titleTV.text = "Начало тренировки"
        startButton.isEnabled = false
        startButton.text = "Процесс тренировки..."
        startNextExercise()
    }

    /**
     * Запуск следующего упражнения из списка, или завершение тренировки, если все упражнения выполнены
     */
    private fun startNextExercise() {
        if (exerciseIndex < exercises.size) {
            currentExercise = exercises[exerciseIndex]
            exerciseTV.text = currentExercise.name
            descriptionTV.text = currentExercise.description
            imageView.setImageResource(currentExercise.gifImage)
            timerTV.text = formatTime(currentExercise.durationInSeconds)

            // Настройка и запуск таймера для текущего упражнения
            timer = object : CountDownTimer(
                currentExercise.durationInSeconds * 1000L,
                1000
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    timerTV.text = formatTime((millisUntilFinished / 1000).toInt())
                }

                override fun onFinish() {
                    timerTV.text = "Упражнение завершено"
                    completeButton.isEnabled = true
                    imageView.setImageResource(0)
                }
            }.start()

            exerciseIndex++
        } else {
            // Завершение тренировки, если все упражнения выполнены
            exerciseTV.text = "Тренировка завершена"
            descriptionTV.text = ""
            timerTV.text = ""
            completeButton.isEnabled = false
            startButton.isEnabled = true
            startButton.text = "Начать снова"
        }
    }

    /**
     * Форматирование времени в минутах и секундах из общего количества секунд
     */
    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}
