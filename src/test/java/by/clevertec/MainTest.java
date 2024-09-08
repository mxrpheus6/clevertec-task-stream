package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Examination;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

class MainTest {

    @Nested
    class AnimalTests {
        private List<Animal> animals;

        @BeforeEach
        void setUp() {
            animals = new ArrayList<>();
            animals.add(new Animal(1, "Tiger", 15, "Japanese", "Male"));
            animals.add(new Animal(2, "Lion", 35, "Australian", "Female"));
            animals.add(new Animal(3, "Elephant", 40, "African", "Male"));
            animals.add(new Animal(4, "Panda", 25, "Chinese", "Female"));
            animals.add(new Animal(5, "Wolf", 19, "Hungarian", "Male"));
            animals.add(new Animal(6, "Kangaroo", 10, "Japanese", "Female"));
            animals.add(new Animal(7, "Penguin", 5, "Oceania", "Male"));
            animals.add(new Animal(8, "Koala", 30, "Australian", "Female"));
        }

        @Test
        void testTask1() {
            List<Animal> result = animals.stream()
                    .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                    .sorted(Comparator.comparingInt(Animal::getAge))
                    .skip(14)
                    .limit(7)
                    .toList();

            Assertions.assertEquals(0, result.size());
        }

        @Test
        void testTask2() {
            List<String> result = animals.stream()
                    .filter(animal -> animal.getOrigin().equals("Japanese"))
                    .map(animal -> {
                        String newBread = animal.getBread();
                        if (animal.getGender().equals("Female")) {
                            newBread = newBread.toUpperCase();
                        }
                        return newBread;
                    })
                    .toList();

            Assertions.assertEquals(List.of("Tiger", "KANGAROO"), result);
        }

        @Test
        void testTask3() {
            List<String> result = animals.stream()
                    .filter(animal -> animal.getAge() > 30)
                    .map(Animal::getOrigin)
                    .filter(origin -> origin.startsWith("A"))
                    .distinct()
                    .toList();

            Assertions.assertEquals(List.of("Australian", "African"), result);
        }

        @Test
        void testTask4() {
            Long result = animals.stream()
                    .filter(animal -> animal.getGender().equals("Female"))
                    .count();

            Assertions.assertEquals(4, result);
        }

        @Test
        void testTask5() {
            boolean result = animals.stream()
                    .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                    .anyMatch(animal -> animal.getOrigin().equals("Hungarian"));

            Assertions.assertFalse(result);
        }

        @Test
        void testTask6() {
            boolean result = animals.stream()
                    .map(Animal::getGender)
                    .allMatch(gender -> gender.equals("Male") || gender.equals("Female"));

            Assertions.assertTrue(result);
        }

        @Test
        void testTask7() {
            boolean result = animals.stream()
                    .map(Animal::getOrigin)
                    .noneMatch(origin -> origin.equals("Oceania"));

            Assertions.assertFalse(result);
        }

        @Test
        void testTask8() {
            Optional<Animal> result = animals.stream()
                    .sorted(Comparator.comparing(Animal::getBread))
                    .limit(100)
                    .max(Comparator.comparingInt(Animal::getAge));
            if (result.isEmpty()) {
                Assertions.fail();
            }

            Assertions.assertEquals(40, result.get().getAge());
        }

        @Test
        void testTask9() {
            OptionalInt result = animals.stream()
                    .map(Animal::getBread)
                    .map(String::toCharArray)
                    .mapToInt(chars -> chars.length)
                    .min();

            if (result.isEmpty()) {
                Assertions.fail();
            }

            Assertions.assertEquals(4, result.getAsInt());
        }

        @Test
        void testTask10() {
            int result = animals.stream()
                    .mapToInt(Animal::getAge)
                    .sum();


            Assertions.assertEquals(179, result);
        }

        @Test
        void testTask11() {
            double result = animals.stream()
                    .filter(animal -> animal.getOrigin().equals("Japanese"))
                    .mapToInt(Animal::getAge)
                    .average()
                    .orElse(0.0);

            Assertions.assertEquals(12.5, result);
        }
    }

    @Nested
    class PersonTests {
        private List<Person> persons;

        @BeforeEach
        void setUp() {
            persons = new ArrayList<>();
            persons.add(new Person(1, "John", "Doe", LocalDate.of(2000, 5, 15), "john.doe@example.com", "Male", 1, "New York", "Engineer"));
            persons.add(new Person(2, "Jane", "Smith", LocalDate.of(2002, 3, 22), "jane.smith@example.com", "Female", 2, "Los Angeles", "Doctor"));
            persons.add(new Person(3, "Emily", "Jones", LocalDate.of(1988, 12, 5), "emily.jones@example.com", "Female", 1, "Chicago", "Teacher"));
            persons.add(new Person(4, "Michael", "Brown", LocalDate.of(1990, 7, 29), "michael.brown@example.com", "Male", 3, "Houston", "Artist"));
            persons.add(new Person(5, "Sarah", "Wilson", LocalDate.of(2000, 11, 10), "sarah.wilson@example.com", "Female", 2, "Phoenix", "Nurse"));
            persons.add(new Person(6, "David", "Taylor", LocalDate.of(1997, 6, 16), "david.taylor@example.com", "Male", 1, "Philadelphia", "Scientist"));
            persons.add(new Person(7, "Laura", "Anderson", LocalDate.of(1985, 9, 25), "laura.anderson@example.com", "Female", 3, "San Antonio", "Chef"));
            persons.add(new Person(8, "James", "Thomas", LocalDate.of(1993, 4, 18), "james.thomas@example.com", "Male", 2, "San Diego", "Writer"));
        }

        @Test
        void testTask12() {
            List<Person> result = persons.stream()
                    .filter(person -> person.getGender().equals("Male"))
                    .filter(person -> {
                        long age = ChronoUnit.YEARS.between(
                                person.getDateOfBirth(),
                                LocalDate.now()
                        );
                        return age >= 18 && age <= 27;
                    })
                    .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                    .limit(200)
                    .toList();

            List<Person> expected = Arrays.asList(
                    new Person(1, "John", "Doe", LocalDate.of(2000, 5, 15), "john.doe@example.com", "Male", 1, "New York", "Engineer"),
                    new Person(6, "David", "Taylor", LocalDate.of(1997, 6, 16), "david.taylor@example.com", "Male", 1, "Philadelphia", "Scientist")
            );

            Assertions.assertEquals(expected, result);
        }

        @Nested
        class StudentTests {
            private List<Student> students;
            private List<Examination> examinations;

            @BeforeEach
            void setUp() {
                students = new ArrayList<>();
                examinations = new ArrayList<>();

                students.add(new Student(1, "Smith", 17, "Engineering", "A"));
                students.add(new Student(2, "Johnson", 19, "Mathematics", "B"));
                students.add(new Student(3, "Williams", 16, "Engineering", "C"));
                students.add(new Student(4, "Brown", 17, "Chemistry", "D"));
                students.add(new Student(5, "Jones", 18, "Physics", "A"));

                examinations.add(new Examination(1, 1, 85, 90, 88));
                examinations.add(new Examination(2, 2, 75, 80, 78));
                examinations.add(new Examination(3, 3, 92, 95, 94));
                examinations.add(new Examination(4, 4, 82, 88, 80));
                examinations.add(new Examination(5, 5, 89, 92, 91));
            }

            @Test
            void testTask16() {
                List<Student> result = students.stream()
                        .filter(student -> student.getAge() < 18)
                        .sorted(Comparator.comparing(Student::getSurname))
                        .toList();

                result.forEach(System.out::println);

                Assertions.assertEquals(3, result.size());
                Assertions.assertEquals("Brown", result.get(0).getSurname());
                Assertions.assertEquals("Smith", result.get(1).getSurname());
                Assertions.assertEquals("Williams", result.get(2).getSurname());
            }

            @Test
            void testTask17() {
                List<String> result = students.stream()
                        .map(Student::getGroup)
                        .distinct()
                        .toList();

                Assertions.assertEquals(4, result.size());
                Assertions.assertEquals("A", result.get(0));
                Assertions.assertEquals("B", result.get(1));
                Assertions.assertEquals("C", result.get(2));
                Assertions.assertEquals("D", result.get(3));
            }

            @Test
            void testTask20() {
                Map<String, Double> facultyAvgGrades = students.stream()
                        .collect(Collectors.groupingBy(
                                Student::getFaculty,
                                Collectors.averagingDouble(student -> {
                                    return examinations.stream()
                                            .filter(examination -> examination.getStudentId() == student.getId())
                                            .findFirst()
                                            .map(Examination::getExam1)
                                            .orElse(0);
                                })
                        ));

                String highestAvg = facultyAvgGrades.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);

                Assertions.assertNotEquals(null, highestAvg);
                Assertions.assertEquals("Physics", highestAvg);
            }

            @Test
            void testTask21() {
                Map<String, Long> result = students.stream()
                        .collect(Collectors.groupingBy(Student::getGroup, Collectors.counting()));

                Assertions.assertEquals(4, result.size());
                Assertions.assertEquals(2, result.get("A"));
            }
        }
    }
}
