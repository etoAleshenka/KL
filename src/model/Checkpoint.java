package model;

import java.time.LocalDate;

/** Контрольная точка с дисциплиной, оценкой, весом и учетом попыток. */
public class Checkpoint {
    private final String subject;
    private final Grade grade;
    private final double weight;
    private final int attemptNumber;
    private final LocalDate date;

    /** Конструктор для первой попытки сдачи. */
    public Checkpoint(String subject, Grade grade, double weight) {
        this(subject, grade, weight, 1, LocalDate.now());
    }

    /** Конструктор с полным указанием параметров, включая номер попытки и дату. */
    public Checkpoint(String subject, Grade grade, double weight, int attemptNumber, LocalDate date) {
        if (attemptNumber < 1 || attemptNumber > 3) {
            throw new IllegalArgumentException("Превышено максимальное количество попыток (максимум 3).");
        }
        this.subject = subject;
        this.grade = grade;
        this.weight = weight;
        this.attemptNumber = attemptNumber;
        this.date = date;
    }

    /** Получение названия дисциплины. */
    public String getSubject() {
        return subject;
    }

    /** Получение оценки. */
    public Grade getGrade() {
        return grade;
    }

    /** Получение веса контрольной точки. */
    public double getWeight() {
        return weight;
    }

    /** Получение номера попытки. */
    public int getAttemptNumber() {
        return attemptNumber;
    }

    /** Получение даты сдачи. */
    public LocalDate getDate() {
        return date;
    }
}