package ph.edu.dlsu.lbycpob.quizmaster.view;

// QuizView.java
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import ph.edu.dlsu.lbycpob.quizmaster.model.UserProgress;
import ph.edu.dlsu.lbycpob.quizmaster.model.Question;

import java.util.List;
import java.util.Objects;

public class QuizView {
    private Stage stage;
    private Scene menuScene, quizScene, resultScene;
    private VBox menuRoot, quizRoot, resultRoot;

    // Menu components
    private ComboBox<String> categoryComboBox;
    private Button startButton;
    private Button statsButton;

    // Quiz components
    private Label questionLabel, timerLabel, scoreLabel, equationLabel;
    private ToggleGroup optionsGroup;
    private List<RadioButton> optionButtons;
    private Button submitButton, nextButton;
    private ProgressBar progressBar;

    // Result components
    private Label finalScoreLabel, accuracyLabel, recommendationLabel;
    private TextArea achievementsArea;
    private Button restartButton;
    private Button bonusButton;
    private Button menuButton;

    // Timer
    private Timeline timer;
    private int timeLeft;

    public QuizView(Stage stage) {
        this.stage = stage;
        initializeScenes();
        setupCSS();
    }

    private void initializeScenes() {
        createMenuScene();
        createQuizScene();
        createResultScene();

        stage.setTitle("LBYCPOB Math Quiz Master");
        stage.setScene(menuScene);
        stage.setMinWidth(800);
        stage.setMinHeight(640);
    }

    private void createMenuScene() {
        menuRoot = new VBox(20);
        menuRoot.setAlignment(Pos.CENTER);
        menuRoot.setPadding(new Insets(50));
        menuRoot.getStyleClass().add("menu-root");

        Label titleLabel = new Label(" LBYCPOB Math Quiz Master");
        titleLabel.getStyleClass().add("title-label");

        Label subtitleLabel = new Label("Test your mathematical knowledge!");
        subtitleLabel.getStyleClass().add("subtitle-label");

        categoryComboBox = new ComboBox<>();
        categoryComboBox.getStyleClass().add("category-combo");
        categoryComboBox.setPromptText("Select Category");

        startButton = new Button("Start Quiz");
        startButton.getStyleClass().add("primary-button");

        statsButton = new Button("View Statistics");
        statsButton.getStyleClass().add("secondary-button");

        menuRoot.getChildren().addAll(titleLabel, subtitleLabel, categoryComboBox, startButton, statsButton);
        menuScene = new Scene(menuRoot, 800, 660);
    }

    private void createQuizScene() {
        quizRoot = new VBox(20);
        quizRoot.setPadding(new Insets(30));
        quizRoot.getStyleClass().add("quiz-root");

        // Top section
        HBox topBox = new HBox(20);
        topBox.setAlignment(Pos.CENTER_LEFT);

        scoreLabel = new Label("Score: 0/0");
        scoreLabel.getStyleClass().add("score-label");

        timerLabel = new Label("Time: 30s");
        timerLabel.getStyleClass().add("timer-label");

        topBox.getChildren().addAll(scoreLabel, new Region(), timerLabel);
        HBox.setHgrow(topBox.getChildren().get(1), Priority.ALWAYS);

        progressBar = new ProgressBar(0);
        progressBar.getStyleClass().add("progress-bar");
        progressBar.setPrefWidth(Double.MAX_VALUE);

        // Question section
        VBox questionBox = new VBox(15);
        questionBox.getStyleClass().add("question-box");

        questionLabel = new Label();
        questionLabel.getStyleClass().add("question-label");
        questionLabel.setWrapText(true);

        equationLabel = new Label();
        equationLabel.getStyleClass().add("equation-label");

        // Options section
        VBox optionsBox = new VBox(10);
        optionsGroup = new ToggleGroup();

        optionButtons = List.of(
                new RadioButton(), new RadioButton(),
                new RadioButton(), new RadioButton()
        );

        for (RadioButton button : optionButtons) {
            button.setToggleGroup(optionsGroup);
            button.setMaxWidth(Double.MAX_VALUE); // Allow it to grow fully
            VBox.setVgrow(button, Priority.ALWAYS);
            button.getStyleClass().add("option-button");
            optionsBox.getChildren().add(button);
        }

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        submitButton = new Button("Submit Answer");
        submitButton.getStyleClass().add("primary-button");

        nextButton = new Button("Next Question");
        nextButton.getStyleClass().add("primary-button");
        nextButton.setVisible(false);

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(submitButton, nextButton);

        questionBox.getChildren().addAll(questionLabel, equationLabel);
        quizRoot.getChildren().addAll(topBox, progressBar, questionBox, optionsBox, buttonBox);
        quizScene = new Scene(quizRoot, 800, 700);
    }

    private void createResultScene() {
        resultRoot = new VBox(20);
        resultRoot.setAlignment(Pos.CENTER);
        resultRoot.setPadding(new Insets(50));
        resultRoot.getStyleClass().add("result-root");

        Label titleLabel = new Label(" Quiz Complete!");
        titleLabel.getStyleClass().add("title-label");

        finalScoreLabel = new Label();
        finalScoreLabel.getStyleClass().add("score-label");

        accuracyLabel = new Label();
        accuracyLabel.getStyleClass().add("accuracy-label");

        recommendationLabel = new Label();
        recommendationLabel.getStyleClass().add("recommendation-label");
        recommendationLabel.setWrapText(true);

        achievementsArea = new TextArea();
        achievementsArea.getStyleClass().add("achievements-area");
        achievementsArea.setEditable(false);
        achievementsArea.setPrefRowCount(4);

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        restartButton = new Button("Restart Quiz");
        restartButton.getStyleClass().add("primary-button");

        bonusButton = new Button("Bonus Round");
        bonusButton.getStyleClass().add("secondary-button");

        menuButton = new Button("Main Menu");
        menuButton.getStyleClass().add("secondary-button");

        buttonBox.getChildren().addAll(restartButton, bonusButton, menuButton);

        Label achievementsLabel = new Label(" Achievements:");
        achievementsLabel.getStyleClass().add("achievements-label");
        resultRoot.getChildren().addAll(titleLabel, finalScoreLabel, accuracyLabel,
                recommendationLabel, achievementsLabel, achievementsArea, buttonBox);
        resultScene = new Scene(resultRoot, 800, 660);
    }

    private void setupCSS() {
        String cssFile = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        menuScene.getStylesheets().add(cssFile);
        quizScene.getStylesheets().add(cssFile);
        resultScene.getStylesheets().add(cssFile);
    }

    public void showMenuScene() {
        stage.setScene(menuScene);
    }

    public void showQuizScene() {
        stage.setScene(quizScene);
    }

    public void showResultScene() {
        stage.setScene(resultScene);
    }

    public void updateQuestion(Question question, int questionNum, int total) {
        questionLabel.setText("Question " + questionNum + ": " + question.getQuestionText());
        equationLabel.setText(question.getEquation());

        List<String> options = question.getOptions();
        for (int i = 0; i < optionButtons.size(); i++) {
            optionButtons.get(i).setText(options.get(i));
            optionButtons.get(i).setSelected(false);
        }

        progressBar.setProgress((double) (questionNum - 1) / total);
        optionsGroup.selectToggle(null);
        submitButton.setVisible(true);
        nextButton.setVisible(false);

        // Reset option button styles
        for (RadioButton button : optionButtons) {
            button.getStyleClass().removeAll("correct-option", "incorrect-option");
        }

        startTimer();
    }

    public void updateScore(int score, int total) {
        scoreLabel.setText("Score: " + score + "/" + total);
    }

    public void showAnswerResult(boolean correct, int correctIndex) {
        submitButton.setVisible(false);
        nextButton.setVisible(true);
        stopTimer();

        // Highlight the correct answer
        optionButtons.get(correctIndex).getStyleClass().add("correct-option");

        // If user was wrong, highlight their selection
        if (!correct) {
            RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
            if (selected != null && !selected.equals(optionButtons.get(correctIndex))) {
                selected.getStyleClass().add("incorrect-option");
            }
        }

        // Animation
        if (correct) {
            playCorrectAnimation();
        } else {
            playIncorrectAnimation();
        }
    }

    private void playCorrectAnimation() {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), questionLabel);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.1);
        scale.setToY(1.1);
        scale.setCycleCount(2);
        scale.setAutoReverse(true);

        FadeTransition fade = new FadeTransition(Duration.millis(100), quizRoot);
        fade.setFromValue(1.0);
        fade.setToValue(0.8);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);

        ParallelTransition parallel = new ParallelTransition(scale, fade);
        parallel.play();
    }

    private void playIncorrectAnimation() {
        TranslateTransition shake = new TranslateTransition(Duration.millis(50), quizRoot);
        shake.setFromX(0);
        shake.setToX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }

    private void startTimer() {
        timeLeft = 30;
        updateTimerDisplay();

        if (timer != null) {
            timer.stop();
        }

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            updateTimerDisplay();
            if (timeLeft <= 0) {
                timer.stop();
                // Auto-submit when time runs out
                if (submitButton.isVisible()) {
                    submitButton.fire();
                }
            }
        }));
        timer.setCycleCount(30);
        timer.play();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void updateTimerDisplay() {
        timerLabel.setText("Time: " + timeLeft + "s");
        if (timeLeft <= 10) {
            timerLabel.getStyleClass().add("timer-warning");
        } else {
            timerLabel.getStyleClass().remove("timer-warning");
        }
    }

    public void updateResults(int score, int total, UserProgress progress, boolean hasBonusRound) {
        finalScoreLabel.setText("Final Score: " + score + "/" + total);
        accuracyLabel.setText(String.format("Accuracy: %.1f%%", progress.getAccuracy()));
        recommendationLabel.setText(" " + progress.getRecommendation());

        StringBuilder achievements = new StringBuilder();
        achievements.append("Total Points: ").append(progress.getTotalPoints()).append("\n");
        achievements.append("Questions Answered: ").append(progress.getTotalQuestionsAnswered()).append("\n");
        achievements.append("Correct Answers: ").append(progress.getCorrectAnswers()).append("\n\n");

        if (!progress.getBadges().isEmpty()) {
            achievements.append("Badges Earned:\n");
            for (String badge : progress.getBadges()) {
                achievements.append("• ").append(badge).append("\n");
            }
        }

        if (!progress.getAchievements().isEmpty()) {
            achievements.append("\n New Achievements:\n");
            for (String achievement : progress.getAchievements()) {
                achievements.append("• ").append(achievement).append("\n");
            }
        }

        achievementsArea.setText(achievements.toString());
        bonusButton.setVisible(hasBonusRound);
    }

    public void setCategories(List<String> categories) {
        categoryComboBox.getItems().setAll(categories);
        categoryComboBox.setValue(categories.getFirst());
    }

    // Getters for controller to access UI components
    public ComboBox<String> getCategoryComboBox() { return categoryComboBox; }
    public Button getStartButton() { return startButton; }
    public Button getStatsButton() { return statsButton; }
    public Button getSubmitButton() { return submitButton; }
    public Button getNextButton() { return nextButton; }
    public Button getRestartButton() { return restartButton; }
    public Button getBonusButton() { return bonusButton; }
    public Button getMenuButton() { return menuButton; }
    public ToggleGroup getOptionsGroup() { return optionsGroup; }
    public List<RadioButton> getOptionButtons() { return optionButtons; }
}