package ph.edu.dlsu.lbycpob.quizmaster.model;

// QuizModel.java

import ph.edu.dlsu.lbycpob.quizmaster.utils.QuestionLoader;
import java.util.*;
import java.util.stream.Collectors;

public class QuizModel {
    private List<Question> questions;
    private List<Question> mistakeQuestions;
    int currentQuestionIndex;
    private int score;
    private int totalQuestions;
    private String currentCategory;
    private UserProgress userProgress;
    private List<String> categories;

    public QuizModel() {
        this.questions = new ArrayList<>();
        this.mistakeQuestions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.userProgress = new UserProgress();
        initializeQuestions();
    }

    private void initializeQuestions() {
        // Algebra questions
        questions.add(new Question("Algebra", "Solve: 2x + 5 = 13",
                Arrays.asList("x = 4", "x = 3", "x = 5", "x = 6"), 0, "2x + 5 = 13"));
        questions.add(new Question("Algebra", "What is the value of x in: 3x - 7 = 14?",
                Arrays.asList("x = 7", "x = 5", "x = 9", "x = 6"), 0, "3x - 7 = 14"));
        questions.add(new Question("Algebra", "Simplify: (x + 2)(x - 3)",
                Arrays.asList("x² - x - 6", "x² + x - 6", "x² - 5x + 6", "x² + 5x - 6"), 0, "(x + 2)(x - 3)"));

        // Geometry questions
        questions.add(new Question("Geometry", "Area of a circle with radius 5",
                Arrays.asList("25π", "10π", "50π", "5π"), 0, "A = πr²"));
        questions.add(new Question("Geometry", "Sum of angles in a triangle",
                Arrays.asList("180°", "360°", "90°", "270°"), 0, "∠A + ∠B + ∠C = 180°"));
        questions.add(new Question("Geometry", "Pythagorean theorem: a² + b² = ?",
                Arrays.asList("c²", "2c", "c", "c³"), 0, "a² + b² = c²"));

        // Calculus questions
        questions.add(new Question("Calculus", "Derivative of x²",
                Arrays.asList("2x", "x", "x²", "2x²"), 0, "d/dx(x²) = 2x"));
        questions.add(new Question("Calculus", "∫x dx = ?",
                Arrays.asList("x²/2 + C", "x + C", "x²", "2x + C"), 0, "∫x dx"));
        questions.add(new Question("Calculus", "Limit of (sin x)/x as x→0",
                Arrays.asList("1", "0", "∞", "undefined"), 0, "lim(x→0) sin(x)/x"));

        Collections.shuffle(questions);

        // Load from file
        questions.addAll(QuestionLoader.loadQuestionsFromCSVSafe("/questions.csv"));
        initializeCategories();
    }

    private void initializeCategories() {
        categories = questions.stream()
                .map(Question::getCategory)  // or obj -> obj.getName()
                .distinct()
                .collect(Collectors.toList());
    }

    public void startQuiz(String category) {
        this.currentCategory = category;
        if ("All".equals(category)) {
            Collections.shuffle(questions);
        } else {
            questions = questions.stream()
                    .filter(q -> q.getCategory().equals(category))
                    .collect(Collectors.toList());
            Collections.shuffle(questions);
        }
        showQuestions();
        this.totalQuestions = questions.size();
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void showQuestions() {
        for (Question question : questions) {
            System.out.println(question);
        }
    }

    public Question getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public Question getPreviousQuestion() {
        if (currentQuestionIndex <= questions.size()) {
            return questions.get(currentQuestionIndex - 1);
        }
        return null;
    }

    public boolean answerQuestion(int selectedAnswer) {
        if (currentQuestionIndex < questions.size()) {
            Question current = questions.get(currentQuestionIndex);
            boolean correct = current.getCorrectAnswer() == selectedAnswer;

            if (correct) {
                score++;
                userProgress.addCorrectAnswer();
            } else {
                mistakeQuestions.add(current);
                userProgress.addIncorrectAnswer();
            }

            currentQuestionIndex++;
            return correct;
        }
        return false;
    }

    public boolean hasNextQuestion() {
        return currentQuestionIndex < questions.size();
    }

    public boolean hasMistakeQuestions() {
        return !mistakeQuestions.isEmpty();
    }

    public void startMistakeQuiz() {
        questions = new ArrayList<>(mistakeQuestions);
        mistakeQuestions.clear();
        currentQuestionIndex = 0;
        Collections.shuffle(questions);
    }

    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCurrentQuestionNumber() { return currentQuestionIndex + 1; }
    public UserProgress getUserProgress() { return userProgress; }
    public String getCurrentCategory() { return currentCategory; }
    public List<Question> getQuestions() {
        return questions;
    }

    public List<String> getCategories() {
        // Arrays.asList("All", "Algebra", "Geometry", "Calculus");
        return categories;
    }
}