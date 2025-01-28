package com.example.mytrainingsession

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Класс, представляющий упражнение.
 *
 * @property name Название упражнения.
 * @property description Описание упражнения.
 * @property durationInSeconds Длительность упражнения в секундах.
 * @property gifImage ID ресурса для гифки.
 */
@Parcelize
data class Exercise(
    val name: String,            // упражнение
    val description: String,     // его описание
    val durationInSeconds: Int,  // его длительность
    val gifImage: Int            // id ресурса для гифки
) : Parcelable {

    companion object {
        const val DEFAULT_DURATION = 60 // Пример константы для стандартной длительности
    }

    init {
        require(name.isNotBlank()) { "Имя упражнения не должно быть пустым" }
        require(description.isNotBlank()) { "Описание упражнения не должно быть пустым" }
        require(durationInSeconds > 0) { "Длительность упражнения должна быть положительной" }
    }
}