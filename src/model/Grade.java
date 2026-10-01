package model;

/** Перечисление оценок по 5-бальной системе. */
public enum Grade {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    UNSATISFACTORY(2);

    private final int value;

    Grade(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /** Получение оценки по числовому значению. */
    public static Grade fromValue(int value) {
        for (Grade grade : values()) {
            if (grade.value == value) {
                return grade;
            }
        }
        throw new IllegalArgumentException("Неверное значение оценки: " + value + ". Допустимы от 2 до 5.");
    }
}