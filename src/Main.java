package model;

import java.time.LocalDate;

/** Главный класс консольного приложения журнала успеваемости
 *
 */
public class Main {
    public static void main(String[] args) {
        GradebookService service = new GradebookService();

        // Создаем студентов группы ПрИнж-7.1
        Student student1 = new Student("Владислав Лисаев", "ПрИнж-7.1");
        Student student2 = new Student("Святослав Колонистов", "ПрИнж-7.1");
        Student student3 = new Student("Максим Квасов", "ПрИнж-7.1");

        // Владислав — отличник
        student1.addCheckpoint(new Checkpoint("Программирование", Grade.EXCELLENT, 2.0, 1, LocalDate.of(2026, 6, 1)));
        student1.addCheckpoint(new Checkpoint("Базы данных", Grade.EXCELLENT, 1.5, 1, LocalDate.of(2026, 6, 3)));

        // Святослав — первая попытка 1 июня (неуд), пересдача 10 июня (отлично)
        student2.addCheckpoint(new Checkpoint("Программирование", Grade.UNSATISFACTORY, 2.0, 1, LocalDate.of(2026, 6, 1)));
        student2.addCheckpoint(new Checkpoint("Программирование", Grade.EXCELLENT, 2.0, 2, LocalDate.of(2026, 6, 10)));
        student2.addCheckpoint(new Checkpoint("Базы данных", Grade.GOOD, 1.5, 1, LocalDate.of(2026, 6, 3)));

        // Максим — должник
        student3.addCheckpoint(new Checkpoint("Программирование", Grade.UNSATISFACTORY, 2.0, 1, LocalDate.of(2026, 6, 1)));

        service.addStudent(student1);
        service.addStudent(student2);
        service.addStudent(student3);

        // Вывод общей аналитики и средних баллов по предметам
        System.out.println("=== АНАЛИТИКА ЖУРНАЛА УСПЕВАЕМОСТИ ===");
        System.out.println("Средний балл группы ПрИнж-7.1: " + String.format("%.2f", service.getGroupAverage("ПрИнж-7.1")));
        System.out.println("Средний балл по дисциплине Программирование: " + String.format("%.2f", service.getSubjectAverage("Программирование")));
        System.out.println("Средний балл по дисциплине Базы данных: " + String.format("%.2f", service.getSubjectAverage("Базы данных")));

        // Вывод средних баллов каждого студента
        System.out.println("\nСтуденты и их средние баллы:");
        System.out.println("- Владислав Лисаев: " + String.format("%.2f", student1.getAverageGrade()));
        System.out.println("- Святослав Колонистов: " + String.format("%.2f", student2.getAverageGrade()));
        System.out.println("- Максим Квасов: " + String.format("%.2f", student3.getAverageGrade()));

        // Вывод списка отличников
        System.out.println("\nОтличники:");
        for (Student s : service.getExcellentStudents()) {
            System.out.println("- " + s.getFullName() + " (" + s.getGroup() + ")");
        }

        // Вывод списка должников (с учетом пересдач)
        System.out.println("\nДолжники (с учетом пересдач):");
        for (Student s : service.getDebtors()) {
            System.out.println("- " + s.getFullName() + " (" + s.getGroup() + ")");
        }

        // Ведомость по Программированию на основной день (1 июня)
        System.out.println("\nВедомость по Программированию на основную дату (2026-06-01):");
        for (String record : service.getStatementBySubjectAndDate("Программирование", LocalDate.of(2026, 6, 1))) {
            System.out.println(record);
        }

        // Ведомость по Программированию на день пересдачи (10 июня)
        System.out.println("\nВедомость по Программированию на дату пересдачи (2026-06-10):");
        for (String record : service.getStatementBySubjectAndDate("Программирование", LocalDate.of(2026, 6, 10))) {
            System.out.println(record);
        }
    }
}