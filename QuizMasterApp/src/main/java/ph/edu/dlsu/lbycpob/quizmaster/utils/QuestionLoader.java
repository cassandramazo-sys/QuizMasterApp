package ph.edu.dlsu.lbycpob.quizmaster.utils;

import ph.edu.dlsu.lbycpob.quizmaster.model.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

// QuestionLoader class to load questions from the CSV file
public class QuestionLoader {

    /**
     * Loads questions from a CSV file into an ArrayList
     * Expected CSV format: category,"questionText","option1","option2","option3","option4",correctAnswer,"equation"
     *
     * @param filePath Path to the CSV file
     * @return ArrayList of Question objects
     * @throws IOException           If the file cannot be read
     * @throws CsvException          If CSV parsing fails
     * @throws NumberFormatException If correctAnswer cannot be parsed as integer
     */
    public static ArrayList<Question> loadQuestionsFromCSV(String filePath)
            throws IOException, CsvException, NumberFormatException {

        ArrayList<Question> questions = new ArrayList<>();
        try (InputStream is = QuestionLoader.class.getResourceAsStream(filePath)) {
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(Objects.requireNonNull(is)))) {
                List<String[]> records = csvReader.readAll();

                // Skip header row if present (optional - adjust based on your CSV structure)
                int startIndex = 0;
                if (!records.isEmpty() && records.getFirst()[0].toLowerCase().contains("category")) {
                    startIndex = 1; // Skip header row
                }

                for (int i = startIndex; i < records.size(); i++) {
                    String[] record = records.get(i);

                    // Validate that we have the expected number of columns
                    if (record.length < 8) {
                        System.err.println("Warning: Row " + (i + 1) + " has insufficient columns. Skipping.");
                        continue;
                    }

                    try {
                        // Parse the CSV row according to the specified format
                        String category = record[0].trim();
                        String questionText = record[1];
                        String option1 = record[2];
                        String option2 = record[3];
                        String option3 = record[4];
                        String option4 = record[5];
                        int correctAnswer = Integer.parseInt(record[6].trim());
                        String equation = record[7];

                        // Create the options list
                        List<String> options = Arrays.asList(option1, option2, option3, option4);

                        // Create and add Question object
                        Question question = new Question(category, questionText, options, correctAnswer, equation);
                        questions.add(question);

                    } catch (NumberFormatException e) {
                        System.err.println("Warning: Invalid correct answer format in row " + (i + 1) + ". Skipping.");
                    } catch (Exception e) {
                        System.err.println("Warning: Error parsing row " + (i + 1) + ": " + e.getMessage() + ". Skipping.");
                    }
                }
            }
        }

        return questions;
    }

    /**
     * Alternative method that loads questions and handles exceptions internally
     * Returns empty list if loading fails
     *
     * @param filePath Path to the CSV file
     * @return ArrayList of Question objects (empty if loading fails)
     */
    public static ArrayList<Question> loadQuestionsFromCSVSafe(String filePath) {
        try {
            return loadQuestionsFromCSV(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return new ArrayList<>();
        } catch (CsvException e) {
            System.err.println("Error parsing CSV: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}