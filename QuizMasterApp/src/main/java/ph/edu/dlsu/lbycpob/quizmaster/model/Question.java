package ph.edu.dlsu.lbycpob.quizmaster.model;

import java.util.ArrayList;
import java.util.List;

// Question.java
public class Question {
   // TODO: Add the data attributes

    public Question(String category, String questionText, List<String> options, int correctAnswer, String equation) {
        this.category = category;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.equation = equation;
    }

    // Getters
    public String getCategory() {
        return category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getEquation() {
        return equation;
    }

    // Setters
    // TODO:

    @Override
    public String toString() {
        return " "; // TODO:
    }
}

