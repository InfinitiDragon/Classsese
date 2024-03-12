import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<String> students = readFromFile("./students.txt");
        List<String> topics = readFromFile("./topics.txt");
        List<String> assignedTopics = new ArrayList<>();

        System.out.println("Загальна кількість студентів: " + students.size());

        if (topics.size() < students.size()) {
            throw new RuntimeException("Недостатньо тем в файлі topics.txt");
        }

        System.out.println("Кількість студентів без теми: " + students.size());

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Collections.shuffle(students);

        int topicsAssigned = 0;
        for (String student : students) {
            if (topicsAssigned >= topics.size()) {
                System.out.println("Недостатньо тем для всіх студентів.");
                break;
            }

            String topic = topics.get(topicsAssigned);
            assignedTopics.add(topic);

            System.out.println("Студент " + student + " отримав тему: " + topic);
            System.out.println("---------------------------------------");
            System.out.println("Кількість тем, що залишилось: " + (topics.size() - topicsAssigned - 1));
            
            topicsAssigned++;
        }

        if (assignedTopics.isEmpty()) {
            System.out.println("Файл student-topic.txt створено і залишений порожнім.");
        } else {
            try {
                writeToFile("./student-topic.txt", assignedTopics, students);
                System.out.println("Дані успішно додано до файлу student-topic.txt");
            } catch (IOException e) {
                System.err.println("Помилка при записі у файл: " + e.getMessage());
            }
        }
    }

    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Помилка при читанні з файлу: " + e.getMessage());
        }
        return lines;
    }

    public static void writeToFile(String filename, List<String> assignedTopics, List<String> students) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < assignedTopics.size(); i++) {
                String topic = assignedTopics.get(i);
                String student = (i < students.size()) ? students.get(i) : "No student assigned";

                writer.write("{\n");
                writer.write("  Name: " + student + "\n");
                writer.write("  \n");
                writer.write("  Topic: " + topic + "\n");
                writer.write("}\n\n");
            }
        }
    }
}
