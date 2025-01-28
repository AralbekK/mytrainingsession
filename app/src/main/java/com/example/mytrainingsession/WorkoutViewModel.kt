package com.example.mytrainingsession

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutViewModel : ViewModel() {
    private val _exercises = MutableLiveData<List<Exercise>>(ExerciseDataBase.exercises)
    val exercises: LiveData<List<Exercise>> get() = _exercises

    private val _exerciseIndex = MutableLiveData<Int>(0)
    val exerciseIndex: LiveData<Int> get() = _exerciseIndex

    private val _currentExercise = MutableLiveData<Exercise>()
    val currentExercise: LiveData<Exercise> get() = _currentExercise

    private val _timerSecondsLeft = MutableLiveData<Int>()
    val timerSecondsLeft: LiveData<Int> get() = _timerSecondsLeft

    private val _isTimerRunning = MutableLiveData<Boolean>(false)
    val isTimerRunning: LiveData<Boolean> get() = _isTimerRunning

    private val _isCompleteButtonEnabled = MutableLiveData<Boolean>(false)
    val isCompleteButtonEnabled: LiveData<Boolean> get() = _isCompleteButtonEnabled

    private val _startButtonText = MutableLiveData<String>("Начало тренировки")
    val startButtonText: LiveData<String> get() = _startButtonText

    private val _titleText = MutableLiveData<String>("Тренировка")
    val titleText: LiveData<String> get() = _titleText

    private val _exerciseText = MutableLiveData<String>("Упражнение")
    val exerciseText: LiveData<String> get() = _exerciseText

    private val _descriptionText = MutableLiveData<String>("")
    val descriptionText: LiveData<String> get() = _descriptionText

    private val _timerText = MutableLiveData<String>("Отсавшееся время")
    val timerText: LiveData<String> get() = _timerText

    private var timer: CountDownTimer? = null

    fun startWorkout() {
        _exerciseIndex.value = 0
        _titleText.value = "Начало тренировки"
        _startButtonText.value = "Процесс тренировки..."
        _isTimerRunning.value = true
        startNextExercise()
    }

    fun completeExercise() {
        timer?.cancel()
        _isCompleteButtonEnabled.value = false
        startNextExercise()
    }

    private fun startNextExercise() {
        val index = _exerciseIndex.value ?: 0
        val exercisesList = _exercises.value ?: emptyList()

        if (index < exercisesList.size) {
            val exercise = exercisesList[index]
            _currentExercise.value = exercise
            _exerciseText.value = exercise.name
            _descriptionText.value = exercise.description
            _timerSecondsLeft.value = exercise.durationInSeconds
            _timerText.value = formatTime(exercise.durationInSeconds)

            timer = object : CountDownTimer(
                exercise.durationInSeconds * 1000L,
                1000
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    val secondsLeft = (millisUntilFinished / 1000).toInt()
                    _timerSecondsLeft.value = secondsLeft
                    _timerText.value = formatTime(secondsLeft)
                }

                override fun onFinish() {
                    _timerText.value = "Упражнение завершено"
                    _isCompleteButtonEnabled.value = true
                    _exerciseIndex.value = index + 1
                }
            }.start()
        } else {
            // Завершение тренировки, если все упражнения выполнены
            _exerciseText.value = "Тренировка завершена"
            _descriptionText.value = ""
            _timerText.value = ""
            _isCompleteButtonEnabled.value = false
            _startButtonText.value = "Начать снова"
            _isTimerRunning.value = false
        }
    }

    // Метод formatTime остается приватным
    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}