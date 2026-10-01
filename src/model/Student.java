package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Студент с автогенерацией ID и расчетом успеваемости. */
public class Student {
    private final String id;
    private final String fullName;
    private final String group;
    private final List<Checkpoint> checkpoints = new ArrayList<>();

    /** Создание студента с автовыбором группы ПрИнж. */
    public Student(String fullName) {
        this.id = generateStudentId();
        this.group = generateGroup();
        this.fullName = fullName;
    }

    /** Создание студента с точной записью группы (например, ПрИнж-5.1). */
    public Student(String fullName, String group) {
        this.id = generateStudentId();
        this.group = group;
        this.fullName = fullName;
    }

    private String generateStudentId() {
        Random random = new Random();
        return String.format("1625%04d", random.nextInt(10000));
    }

    private String generateGroup() {
        String[] groups = {
                "ПрИнж-5.1", "ПрИнж-5.2",
                "ПрИнж-6.1", "ПрИнж-6.2",
                "ПрИнж-7.1", "ПрИнж-7.2",
                "ПрИнж-8.1", "ПрИнж-8.2"
        };
        return groups[new Random().nextInt(groups.length)];
    }

    /** Добавление контрольной точки в список сдач. */
    public void addCheckpoint(Checkpoint checkpoint) {
        checkpoints.add(checkpoint);
    }

    /** Получение номер зачетки. */
    public String getId() {
        return id;
    }

    /** Получение ФИО студента. */
    public String getFullName() {
        return fullName;
    }

    /** Получение группы. */
    public String getGroup() {
        return group;
    }

    /** Получение всех контрольных точек. */
    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    /** Расчет среднего балла. */
    public double getAverageGrade() {
        if (checkpoints.isEmpty()) return 0.0;
        double sum = 0;
        for (Checkpoint cp : checkpoints) {
            sum += cp.getGrade().getValue();
        }
        return sum / checkpoints.size();
    }

    /** Расчет взвешенного рейтинга. */
    public double getWeightedRating() {
        if (checkpoints.isEmpty()) return 0.0;
        double totalWeightedScore = 0;
        double totalWeight = 0;
        for (Checkpoint cp : checkpoints) {
            totalWeightedScore += cp.getGrade().getValue() * cp.getWeight();
            totalWeight += cp.getWeight();
        }
        return totalWeight == 0 ? 0 : totalWeightedScore / totalWeight;
    }

    /** Проверка наличия задолженностей. */
    public boolean hasDebts() {
        for (Checkpoint cp : checkpoints) {
            if (cp.getGrade() == Grade.UNSATISFACTORY) {
                return true;
            }
        }
        return false;
    }
}