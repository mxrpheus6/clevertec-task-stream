package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();

        List<Animal> thirdZoo = animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14)
                .limit(7)
                .toList();

        thirdZoo.forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();

        List<String> filteredAnimals = animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .map(animal -> {
                    String newBread = animal.getBread();
                    if (animal.getGender().equals("Female")) {
                        newBread = newBread.toUpperCase();
                    }
                    return newBread;
                })
                .toList();

        filteredAnimals.forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();

        List<String> distinctOrigins = animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.startsWith("A"))
                .distinct()
                .toList();

        distinctOrigins.forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();

        Long count = animals.stream()
                .filter(animal -> animal.getGender().equals("Female"))
                .count();

        System.out.println(count);
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();

        boolean isAnyHungarian = animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> animal.getOrigin().equals("Hungarian"));

        System.out.println(isAnyHungarian);
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();

        boolean isAllMaleOrFemale = animals.stream()
                .map(Animal::getGender)
                .allMatch(gender -> gender.equals("Male") || gender.equals("Female"));

        System.out.println(isAllMaleOrFemale);
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();

        boolean isNoneMatchOceania = animals.stream()
                .map(Animal::getOrigin)
                .noneMatch(origin -> origin.equals("Oceania"));

        System.out.println(isNoneMatchOceania);
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();

        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .max(Comparator.comparingInt(Animal::getAge))
                .ifPresentOrElse(
                animal -> System.out.println(animal.getAge()),
                () -> System.out.println("Error in task8")
        );
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();

        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .mapToInt(chars -> chars.length)
                .min()
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Error in task9")
                );
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();

        int sumAge = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();

        System.out.println(sumAge);
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();

        double indonesianAvgAge = animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge)
                .average()
                .orElse(0.0);

        System.out.println(indonesianAvgAge);
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();

        persons.stream()
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
                .forEach(System.out::println);
        System.out.print("------------------------------------------");
    }

    public static void task13() {
        List<House> houses = Util.getHouses();

        List<House> hospitalHouses = houses.stream()
                .filter(house -> house.getBuildingType().equals("Hospital"))
                .toList();

        List<Person> hospitalEvacuees = hospitalHouses.stream()
                .flatMap(house -> house.getPersonList().stream())
                .toList();

        List<Person> ageEvacuees = houses.stream()
                .filter(house -> !hospitalHouses.contains(house))
                .flatMap(house -> house.getPersonList().stream())
                .filter(person -> {
                    long age = ChronoUnit.YEARS.between(
                            person.getDateOfBirth(),
                            LocalDate.now()
                    );
                    if (person.getGender().equals("Female")) {
                        return age <= 18 || age >= 60;
                    } else {
                        return age <= 18 || age >= 65;
                    }
                })
                .toList();

        List<Person> totalEvacuees = Stream.concat(hospitalEvacuees.stream(), ageEvacuees.stream())
                .limit(500)
                .toList();

        totalEvacuees.forEach(System.out::println);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();

        List<Car> turkmenistanCars = cars.stream()
                .filter(car -> car.getCarMake().equals("Jaguar") ||
                        car.getColor().equals("White"))
                .toList();

        List<Car> remainingCars = cars.stream()
                .filter(car -> !turkmenistanCars.contains(car))
                .toList();

        List<Car> uzbekistanCars = remainingCars.stream()
                .filter(car -> car.getMass() < 1500 &&
                        List.of("BMW", "Lexus", "Chrysler", "Toyota").contains(car.getCarMake()))
                .toList();

        remainingCars = remainingCars.stream()
                .filter(car -> !uzbekistanCars.contains(car))
                .toList();

        List<Car> kazakhstanCars = remainingCars.stream()
                .filter(car -> car.getColor().equals("Black") && car.getMass() > 4000 ||
                        List.of("GMC", "Dodge").contains(car.getCarMake()))
                .toList();

        remainingCars = remainingCars.stream()
                .filter(car -> !kazakhstanCars.contains(car))
                .toList();

        List<Car> kyrgyzstanCars = remainingCars.stream()
                .filter(car -> car.getReleaseYear() < 1992 ||
                        List.of("Civic", "Cherokee").contains(car.getCarModel()))
                .toList();

        remainingCars = remainingCars.stream()
                .filter(car -> !kyrgyzstanCars.contains(car))
                .toList();

        List<Car> russianCars = remainingCars.stream()
                .filter(car -> !List.of("Yellow", "Red", "Green", "Blue").contains(car.getColor()) ||
                        car.getPrice() > 40000)
                .toList();

        remainingCars = remainingCars.stream()
                .filter(car -> !russianCars.contains(car))
                .toList();

        List<Car> mongolianCars = remainingCars.stream()
                .filter(car -> car.getVin().contains("59"))
                .toList();

        double turkmenistanCost = turkmenistanCars.stream().mapToDouble(car -> car.getMass() / 1000 * 7.14).sum();
        double uzbekistanCost = uzbekistanCars.stream().mapToDouble(car -> car.getMass() / 1000 * 7.14).sum();
        double kazakhstanCost = kazakhstanCars.stream().mapToDouble(car -> car.getMass() / 1000 * 7.14).sum();
        double kyrgyzstanCost = kyrgyzstanCars.stream().mapToDouble(car -> car.getMass() / 1000 * 7.14).sum();
        double russiaCost = russianCars.stream().mapToDouble(car -> car.getMass() / 1000 * 7.14).sum();
        double mongoliaCost = mongolianCars.stream().mapToDouble(car -> car.getMass() / 1000 * 7.14).sum();

        System.out.println("Turkmenistan: " + turkmenistanCost + " $");
        System.out.println("Uzbekistan: " + uzbekistanCost + " $");
        System.out.println("Kazakhstan: " + kazakhstanCost + " $");
        System.out.println("Kyrgyzstan: " + kyrgyzstanCost + " $");
        System.out.println("Russia: " + russiaCost + " $");
        System.out.println("Mongolia: " + mongoliaCost + " $");

        double totalCost = turkmenistanCost + uzbekistanCost + kazakhstanCost + kyrgyzstanCost + russiaCost + mongoliaCost;
        System.out.println("Total cost for logistics agency: " + totalCost + " $");
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();

        double sum = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Flower::getWaterConsumptionPerDay).reversed()
                )
                .filter(flower ->
                    flower.getCommonName().toUpperCase().compareTo("C") <= 0 ||
                    flower.getCommonName().toUpperCase().compareTo("S") >= 0
                )
                .filter(flower -> flower.isShadePreferred() &&
                        (flower.getFlowerVaseMaterial().contains("Aluminum") ||
                         flower.getFlowerVaseMaterial().contains("Glass") ||
                         flower.getFlowerVaseMaterial().contains("Steel")))
                .mapToDouble(flower -> {
                    double plantCost = flower.getPrice();
                    double waterCost = flower.getWaterConsumptionPerDay() * 5 * 365;

                    return plantCost + waterCost;
                })
                .sum();

        System.out.println("Flowers price: " + sum);
    }

    public static void task16() {
        List<Student> students = Util.getStudents();

        students.stream()
                .filter(student -> student.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(System.out::println);
    }

    public static void task17() {
        List<Student> students = Util.getStudents();

        students.stream()
                .map(Student::getGroup)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();

        Map<String, Double> averageAgeByFaculty = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getFaculty,
                        Collectors.averagingInt(Student::getAge)
                ));

        averageAgeByFaculty.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(entry ->
                        System.out.println("Faculty - " + entry.getKey() +
                                " | Avg age - " + entry.getValue()));
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input group: ");
        String group = scanner.nextLine();

        List<Student> filteredStudents = students.stream()
                .filter(student -> student.getGroup().equals(group))
                .filter(student -> examinations.stream()
                        .anyMatch(examination -> examination.getStudentId() == student.getId() &&
                                examination.getExam3() > 4))
                .toList();

        System.out.println("Group " + group + ":");
        filteredStudents.forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();

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
                .orElse("Error in task20");

        System.out.println("Highest 1 exam faculty: " + highestAvg);
    }

    public static void task21() {
        List<Student> students = Util.getStudents();

        Map<String, Long> studentsPerGroup = students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, Collectors.counting()));

        studentsPerGroup.forEach((group, count) ->
                System.out.println("Group: " + group + " Count: " + count));

    }

    public static void task22() {
        List<Student> students = Util.getStudents();

        Map<String, Optional<Student>> minAgesPerFaculty = students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty, Collectors.minBy(
                        Comparator.comparingInt(Student::getAge)
                )));

        minAgesPerFaculty.forEach((faculty, student) ->
                System.out.println("Faculty: " + faculty + " Min age: " +
                        student.map(Student::getAge).orElse(null)));
    }
}
