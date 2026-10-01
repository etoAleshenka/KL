package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Сервис управления зачетной книжкой и аналитики успеваемости. */
public class GradebookService {
    private final List<Student> students = new ArrayList<>();

    /** Добавление студента в журнал. */
    public void addStudent(Student student) {
        students.add(student);
    }

    /** Получение всех студентов. */
    public List<Student> getStudents() {
        return students;
    }

    /** Вычисление среднего балла по группе. */
    public double getGroupAverage(String group) {
        double sum = 0;
        int count = 0;
        for (Student student : students) {
            if (student.getGroup().equalsIgnoreCase(group)) {
                sum += student.getAverageGrade();
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    /** Вычисление среднего балла по дисциплине среди всех студентов. */
    public double getSubjectAverage(String subject) {
        double sum = 0;
        int count = 0;
        for (Student student : students) {
            double avg = student.getAverageGradeBySubject(subject);
            if (avg > 0) {
                sum += avg;
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    /** Формирование списка отличников. */
    public List<Student> getExcellentStudents() {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.isExcellent()) {
                result.add(student);
            }
        }
        return result;
    }

    /** Формирование списка должников. */
    public List<Student> getDebtors() {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.hasDebts()) {
                result.add(student);
            }
        }
        return result;
    }

    /** Формирование ведомости по дисциплине на определенную дату. */
    public List<String> getStatementBySubjectAndDate(String subject, LocalDate date) {
        List<String> statement = new ArrayList<>();
        for (Student student : students) {
            for (Checkpoint cp : student.getCheckpoints()) {
                if (cp.getSubject().equalsIgnoreCase(subject) && cp.getDate().equals(date)) {
                    statement.add(student.getFullName() + " (" + student.getGroup() + ") — Оценка: "
                            + cp.getGrade().getValue() + " (Попытка №" + cp.getAttemptNumber() + ")");
                }
            }
        }
        return statement;
    }
}