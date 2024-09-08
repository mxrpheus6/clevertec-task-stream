package by.clevertec;

import by.clevertec.model.Animal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

class MainTest {

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

        Assertions.assertEquals(40 ,result.get().getAge());
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

        Assertions.assertEquals(4 ,result.getAsInt());
    }

    @Test
    void testTask10() {
        int result = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();


        Assertions.assertEquals(179 ,result);
    }

    @Test
    void testTask11() {
        double result = animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0.0);

        Assertions.assertEquals(12.5 ,result);
    }
}
