package model;

import org.junit.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Класс для модульного тестирования компонентов журнала успеваемости (JUnit 4).
 */
public class GradebookTest {

    @Test
    public void testGradeValues() {
        assertEquals(5, Grade.EXCELLENT.getValue());
        assertEquals(4, Grade.GOOD.getValue());
        assertEquals(3, Grade.SATISFACTORY.getValue());
        assertEquals(2, Grade.UNSATISFACTORY.getValue());
    }

    @Test
    public void testGradeFromValueValid() {
        assertEquals(Grade.EXCELLENT, Grade.fromValue(5));
        assertEquals(Grade.GOOD, Grade.fromValue(4));
        assertEquals(Grade.SATISFACTORY, Grade.fromValue(3));
        assertEquals(Grade.UNSATISFACTORY, Grade.fromValue(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGradeFromValueInvalid() {
        Grade.fromValue(6);
    }

    @Test
    public void testCheckpointCreationValid() {
        LocalDate date = LocalDate.of(2026, 6, 1);
        Checkpoint cp = new Checkpoint("Программирование", Grade.EXCELLENT, 2.0, 1, date);

        assertEquals("Программирование", cp.getSubject());
        assertEquals(Grade.EXCELLENT, cp.getGrade());
        assertEquals(2.0, cp.getWeight(), 0.001);
        assertEquals(1, cp.getAttemptNumber());
        assertEquals(date, cp.getDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckpointAttemptLimitExceeded() {
        LocalDate date = LocalDate.now();
        new Checkpoint("Программирование", Grade.GOOD, 2.0, 4, date);
    }

    @Test
    public void testStudentCreationWithAutoGroup() {
        Student student = new Student("Владислав Лисаев");
        assertEquals("Владислав Лисаев", student.getFullName());
        assertNotNull(student.getGroup());
    }

    @Test
    public void testStudentCreationWithCustomGroup() {
        Student student = new Student("Святослав Колонистов", "ПрИнж-7.1");
        assertEquals("Святослав Колонистов", student.getFullName());
        assertEquals("ПрИнж-7.1", student.getGroup());
    }

    @Test
    public void testStudentAverageGrade() {
        Student student = new Student("Максим Квасов");
        student.addCheckpoint(new Checkpoint("Программирование", Grade.EXCELLENT, 2.0, 1, LocalDate.now()));
        student.addCheckpoint(new Checkpoint("Базы данных", Grade.GOOD, 1.0, 1, LocalDate.now()));

        assertEquals(4.5, student.getAverageGrade(), 0.01);
    }

    @Test
    public void testStudentAverageGradeEmpty() {
        Student student = new Student("Максим Квасов");
        assertEquals(0.0, student.getAverageGrade(), 0.001);
    }

    @Test
    public void testStudentWeightedRating() {
        Student student = new Student("Максим Квасов");
        student.addCheckpoint(new Checkpoint("Программирование", Grade.EXCELLENT, 2.0, 1, LocalDate.now()));
        student.addCheckpoint(new Checkpoint("Базы данных", Grade.GOOD, 3.0, 1, LocalDate.now()));

        // Исправлено: 22.0 / 5.0 (сумма весов) = 4.4
        assertEquals(4.4, student.getWeightedRating(), 0.001);
    }

    @Test
    public void testStudentHasDebtsWithRetake() {
        Student student = new Student("Святослав Колонистов");
        student.addCheckpoint(new Checkpoint("Программирование", Grade.UNSATISFACTORY, 2.0, 1, LocalDate.of(2026, 6, 1)));
        assertTrue(student.hasDebts());

        student.addCheckpoint(new Checkpoint("Программирование", Grade.EXCELLENT, 2.0, 2, LocalDate.of(2026, 6, 10)));
        assertFalse(student.hasDebts());
    }

    @Test
    public void testStudentIsExcellent() {
        Student student = new Student("Владислав Лисаев");
        student.addCheckpoint(new Checkpoint("Программирование", Grade.EXCELLENT, 2.0, 1, LocalDate.now()));
        student.addCheckpoint(new Checkpoint("Базы данных", Grade.EXCELLENT, 1.0, 1, LocalDate.now()));

        assertTrue(student.isExcellent());
    }

    @Test
    public void testServiceGroupAverage() {
        GradebookService service = new GradebookService();
        Student s1 = new Student("Владислав Лисаев", "ПрИнж-7.1");
        s1.addCheckpoint(new Checkpoint("Программирование", Grade.EXCELLENT, 1.0, 1, LocalDate.now()));

        Student s2 = new Student("Максим Квасов", "ПрИнж-7.1");
        s2.addCheckpoint(new Checkpoint("Программирование", Grade.GOOD, 1.0, 1, LocalDate.now()));

        service.addStudent(s1);
        service.addStudent(s2);

        assertEquals(4.5, service.getGroupAverage("ПрИнж-7.1"), 0.01);
    }

    @Test
    public void testServiceExcellentAndDebtorsLists() {
        GradebookService service = new GradebookService();

        Student excellentStudent = new Student("Владислав Лисаев", "ПрИнж-7.1");
        excellentStudent.addCheckpoint(new Checkpoint("Программирование", Grade.EXCELLENT, 1.0, 1, LocalDate.now()));

        Student debtorStudent = new Student("Максим Квасов", "ПрИнж-7.1");
        debtorStudent.addCheckpoint(new Checkpoint("Программирование", Grade.UNSATISFACTORY, 1.0, 1, LocalDate.now()));

        service.addStudent(excellentStudent);
        service.addStudent(debtorStudent);

        List<Student> excellentList = service.getExcellentStudents();
        List<Student> debtorList = service.getDebtors();

        assertTrue(excellentList.contains(excellentStudent));
        assertFalse(excellentList.contains(debtorStudent));

        assertTrue(debtorList.contains(debtorStudent));
        assertFalse(debtorList.contains(excellentStudent));
    }

    @Test
    public void testServiceSubjectAverage() {
        GradebookService service = new GradebookService();
        Student s1 = new Student("Владислав Лисаев", "ПрИнж-7.1");
        s1.addCheckpoint(new Checkpoint("Программирование", Grade.EXCELLENT, 1.0, 1, LocalDate.now()));

        Student s2 = new Student("Максим Квасов", "ПрИнж-7.1");
        s2.addCheckpoint(new Checkpoint("Программирование", Grade.GOOD, 1.0, 1, LocalDate.now()));

        service.addStudent(s1);
        service.addStudent(s2);

        assertEquals(4.5, service.getSubjectAverage("Программирование"), 0.01);
    }
}