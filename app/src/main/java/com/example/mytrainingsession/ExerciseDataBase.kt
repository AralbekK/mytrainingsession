package com.example.mytrainingsession
/**
 * Класс бд для хранения списка упражнений.
 * companion object чтобы создать статические данныек которым обращаемся напрямую чреез класс
 */
class ExerciseDataBase {
    companion object {
        // Массив упражнений, который хранит объекты типа Exercise.
        val exercises = mutableListOf(
            Exercise(
                name = "Отжимания от пола",
                description = "Поставьте руки на пол на ширине плеч. Опускайте туловище, пока грудь почти не касается пола. Разгибайте руки полностью, поднимая туловище.",
                durationInSeconds = 30,
                R.drawable.pushup
            ),
            Exercise(
                name = "Приседания",
                description = "Встаньте, ноги на ширине плеч. Опустите туловище как можно ниже, сгибая ноги в коленях. Вернитесь в исходное положение.",
                durationInSeconds = 45,
                R.drawable.squats
            ),
            Exercise(
                name = "Планка",
                description = "Примите упор лежа, затем согните руки в локтях и перенесите вес тела на предплечья. Удерживайте это положение отведенное время.",
                durationInSeconds = 45,
                R.drawable.plank
            ),
            Exercise(
                name = "Подтягивания",
                description = "Потянитесь к перекладине, согните руки в локтях и подтянитесь к ней. Опуститесь обратно в исходное положение.",
                durationInSeconds = 30,
                R.drawable.pullups
            ),
            Exercise(
                name = "Берпи",
                description = "Встаньте, ноги на ширине плеч, опуститесь в приседание, положите руки на пол перед собой, а затем подпрыгните, подтягивая колени к груди.",
                durationInSeconds = 45,
                R.drawable.burpees
            ),
            Exercise(
                name = "Приседания с прыжком",
                description = "Встаньте, ноги на ширине плеч. Опустите туловище как можно ниже, сгибая ноги в коленях, затем подпрыгните вверх.",
                durationInSeconds = 30,
                R.drawable.jumping_squats
            ),
            Exercise(
                name = "Отжимания на брусьях",
                description = "Поставьте руки на брусьях на ширине плеч. Опускайте туловище, пока грудь почти не касается брусьев. Разгибайте руки полностью, поднимая туловище.",
                durationInSeconds = 45,
                R.drawable.dips
            ),
            Exercise(
                name = "Бег на месте",
                description = "Станьте на месте, руки согнуты в локтях. Бегите на месте, поднимая колени к груди.",
                durationInSeconds = 30,
                R.drawable.jogging_in_place
            ),
            Exercise(
                name = "Махи руками в стороны",
                description = "Станьте прямо, руки согнуты в локтях. Поднимайте руки в стороны, затем опускайте их обратно.",
                durationInSeconds = 45,
                R.drawable.arm_circles
            )
        )
    }
}